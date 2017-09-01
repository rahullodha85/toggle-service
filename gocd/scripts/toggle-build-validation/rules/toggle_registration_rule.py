import os
from multiprocessing.pool import ThreadPool as Pool

class ToggleRegistrationRule (object):

    def __init__(self, my_toggles, reference_toggles, toggle_searcher):
        self.__reference_toggles = reference_toggles
        self.__my_toggles = my_toggles
        self.__toggle_searcher = toggle_searcher
        self.__evaluation = "--- Registration Rule: Toggles used in code should be in toggles.json ---\n"
        self.__is_passed = False

    def __is_defined(self, toggle):
        toggle_names = list(map(lambda x: x['toggle_name'], self.__my_toggles))
        if self.__toggle_searcher.is_used(path=os.getcwd(), toggle=toggle['toggle_name']):
            if toggle['toggle_name'] not in toggle_names:
                
                if self.__toggle_searcher.is_false_positive(toggle['toggle_name']):
                    self.__evaluation += "--- [WARN]  Toggle {} found in codebase but not registered in toggles.json\n".format(toggle['toggle_name'])
                    self.__evaluation += "*** [WARN]  {} can be a False Positive, double check code usage before adding to toggles.json\n".format(toggle['toggle_name'])
                else:
                    self.__evaluation += "--- [ERROR] Toggle {} found in codebase but not registered in toggles.json\n".format(toggle['toggle_name'])
                    return False
        return True

    def evaluate(self):
        pool = Pool(10)
        self.__is_passed = all(pool.map(self.__is_defined, self.__reference_toggles))
        print self.__evaluation

    def is_passed(self):
        return self.__is_passed
