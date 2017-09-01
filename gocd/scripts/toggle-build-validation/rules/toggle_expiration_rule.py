import datetime
import pytz
from dateutil.parser import parse
from multiprocessing.pool import ThreadPool as Pool


class ToggleExpirationRule(object):
    AGE_THRESHOLD = 90

    def __init__(self, my_toggles, reference_toggles):
        self.__reference_toggles = reference_toggles
        self.__my_toggles = my_toggles
        self.__evaluation = "--- Expiration Rule: Temporary/Project toggles' age should not exceed {} days ---\n".format(
            self.AGE_THRESHOLD)
        self.__is_passed = False

    def __should_be_short_lived(self, toggle):
        return (toggle['toggle_type'] == 'Temporary' or toggle['toggle_type'] == 'Project')

    def __has_not_expired(self, toggle):
        name_list = list(map(lambda x: x['toggle_name'], self.__my_toggles))
        if toggle['toggle_name'] in name_list and self.__should_be_short_lived(toggle):
            if (datetime.datetime.now(pytz.utc) - parse(toggle['modified_timestamp'], fuzzy=True)).days > self.AGE_THRESHOLD:
                self.__evaluation += "--- [WARN]  Toggle {} is past its expiration date and should be cleaned up\n".format(toggle['toggle_name'])
                return False
        return True

    def evaluate(self):
        pool = Pool(10)
        self.__is_passed = all(pool.map(self.__has_not_expired, self.__reference_toggles))
        print self.__evaluation

    def is_passed(self):
        return self.__is_passed
