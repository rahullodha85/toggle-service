from multiprocessing.pool import ThreadPool as Pool

class ToggleCapitalizationRule(object):

    def __init__(self, my_toggles, exceptions = []):
        self.__exceptions = exceptions
        self.__my_toggles = my_toggles
        self.__is_passed = False
        self.__evaluation = "--- Capitalization Rule: Toggle names should be capitalized ---\n"

    def __is_capitalized(self, toggle):
        toggle_name = toggle['toggle_name']

        if toggle_name != toggle_name.upper() and not toggle_name in self.__exceptions:
            self.__evaluation = "--- [ERROR] Toggle {} is not capitalized\n".format(toggle_name)
            return False

        return True


    def evaluate(self):
        pool = Pool(10)
        self.__is_passed = all(pool.map(self.__is_capitalized, self.__my_toggles))
        print(self.__evaluation)


    def is_passed(self):
        return self.__is_passed
