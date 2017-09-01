import unittest
from rules import ToggleDefinitionValidityRule


class ToggleDefinitionValidityRuleTest(unittest.TestCase):
    def test_rule_fails_when_all_toggles_are_improperly_defined(self):
        my_toggles = [{'some_key': 'some_value'}]

        rule = ToggleDefinitionValidityRule(my_toggles)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), False)

    def test_rule_fails_when_some_toggles_are_not_fully_defined(self):
        my_toggles = [
            {'toggle_name': 'TOGGLE_A', 'toggle_type': 'Temporary', 'description': ''},
            {'toggle_name': 'TOGGLE_B', 'description': ''}
        ]

        rule = ToggleDefinitionValidityRule(my_toggles)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), False)

    def test_rule_passes_when_all_toggles_are_fully_defined(self):
        my_toggles = [
            {'toggle_name': 'TOGGLE_A', 'toggle_type': 'Temporary', 'description': ''},
            {'toggle_name': 'TOGGLE_B', 'toggle_type': 'Temporary', 'description': ''}
        ]

        rule = ToggleDefinitionValidityRule(my_toggles)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), True)


if __name__ == '__main__':
    unittest.main()
