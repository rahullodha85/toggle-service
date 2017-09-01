import unittest
from rules import ToggleSearcher, ToggleRegistrationRule
from mock import MagicMock

class ToggleRegistrationRuleTest(unittest.TestCase):

    def test_rule_should_fail_when_there_are_toggles_used_in_codebase_but_not_in_registration_file(self):
        my_toggles = [
            {'toggle_name': "TOGGLE_C"},
            {'toggle_name': "TOGGLE_D"}
        ]
        reference_toggles = [
            {'toggle_name': 'TOGGLE_A'},
            {'toggle_name': 'TOGGLE_B'}
        ]
        toggle_searcher = ToggleSearcher()
        toggle_searcher.is_used = MagicMock(return_value=True)
        rule = ToggleRegistrationRule(my_toggles, reference_toggles, toggle_searcher)

        rule.evaluate()
        self.assertEquals(rule.is_passed(), False)

    def test_rule_should_pass_when_toggle_is_used_and_defined(self):
        my_toggles = [
            {'toggle_name': "TOGGLE_A"},
            {'toggle_name': "TOGGLE_B"}
        ]
        reference_toggles = [
            {'toggle_name': 'TOGGLE_A'},
            {'toggle_name': 'TOGGLE_B'},
            {'toggle_name': 'TOGGLE_C'},
            {'toggle_name': 'TOGGLE_D'}
        ]
        toggle_searcher = ToggleSearcher()
        mock = MagicMock()
        mock.side_effect = [True, True, False, False]
        toggle_searcher.is_used = mock
        rule = ToggleRegistrationRule(my_toggles, reference_toggles, toggle_searcher)

        rule.evaluate()
        self.assertEquals(rule.is_passed(), True)

if __name__ == '__main__':
    unittest.main()
