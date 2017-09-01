from multiprocessing.pool import ThreadPool as Pool

class ToggleDefinitionValidityRule(object):
    __TOGGLE_KEYS = ['toggle_name', 'description', 'toggle_type']

    def __init__(self, my_toggles):
        self.__my_toggles = my_toggles
        self.__is_passed = False
        self.__evaluation = "--- Definition Validity Rule: Toggles defined in toggles.json should have proper JSON definitions ---\n"

    def __is_valid(self, toggle):
        my_toggle_keys = list(toggle.keys())
        for key in self.__TOGGLE_KEYS:
            if key not in my_toggle_keys:
                self.__evaluation += "--- [ERROR] Toggle {} is missing key {}\n".format(toggle, key)
                return False
        return True

    def evaluate(self):
        pool = Pool(10)
        self.__is_passed = all(pool.map(self.__is_valid, self.__my_toggles))
        print self.__evaluation

    def is_passed(self):
        return self.__is_passed
