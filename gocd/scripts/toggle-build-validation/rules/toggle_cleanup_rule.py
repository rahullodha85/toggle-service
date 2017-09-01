import os
from multiprocessing.pool import ThreadPool as Pool

class ToggleCleanupRule (object):

    def __init__(self, my_toggles, toggle_searcher):
        self.__my_toggles = my_toggles
        self.__toggle_searcher = toggle_searcher
        self.__evaluation = "--- Cleanup Rule: Toggles defined in toggles.json should reflect what the code uses ---\n"
        self.__is_passed = False

    def __is_used(self, toggle):
        toggle_name = toggle['toggle_name']
        if not self.__toggle_searcher.is_used(path=os.getcwd(), toggle=toggle_name):

            if self.__toggle_searcher.is_false_positive(toggle_name):
                self.__evaluation += "--- [WARN]  Toggle {} in registration file, but not used in code\n".format(toggle_name)
                self.__evaluation += "*** [WARN]  {} can be a False Positive: double check code usage before removing from toggles.json\n".format(toggle_name)
            else:
                self.__evaluation += "--- [ERROR] Toggle {} in registration file, but not used in code\n".format(toggle_name)
                return False

        return True

    def evaluate(self):
        pool = Pool(10)
        self.__is_passed = all(pool.map(self.__is_used, self.__my_toggles))
        print self.__evaluation

    def is_passed (self):
        return self.__is_passed
