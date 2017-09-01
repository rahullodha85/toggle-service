import unittest
from rules import ToggleCapitalizationRule

class ToggleCapitalizationRuleTest(unittest.TestCase):

    def test_rule_should_fail_if_a_toggle_is_not_capitalized(self):
        my_toggles = [{'toggle_name': "mixedCASE_toggle"}, {'toggle_name': "UPPERCASE_TOGGLE"}]

        rule = ToggleCapitalizationRule(my_toggles)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), False)

    def test_rule_should_pass_if_all_toggles_are_capitalized(self):
        my_toggles = [{'toggle_name': "UPPERCASE_TOGGLE_1"}, {'toggle_name': "UPPERCASE_TOGGLE_2"}]

        rule = ToggleCapitalizationRule(my_toggles)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), True)

    def test_rule_should_pass_if_all_non_capitalized_toggles_are_known_exceptions(self):
        my_toggles = [{'toggle_name': "UPPERCASE_TOGGLE"}, {'toggle_name': "mixedCASE_toggle"}]
        exceptions = ["mixedCASE_toggle"]

        rule = ToggleCapitalizationRule(my_toggles, exceptions)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), True)

if __name__ == '__main__':
    unittest.main()
