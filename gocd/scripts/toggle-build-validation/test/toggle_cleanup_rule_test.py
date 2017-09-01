import unittest
from mock import MagicMock
from rules import ToggleSearcher, ToggleCleanupRule

class ToggleCleanupRuleTest(unittest.TestCase):

    def test_rule_passes_when_no_defined_toggles_exist(self):
        my_toggles = []
        toggle_searcher = ToggleSearcher()

        rule = ToggleCleanupRule(my_toggles, toggle_searcher)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), True)

    def test_rule_fails_when_no_defined_toggles_are_used(self):
        my_toggles = [{'toggle_name': "TOGGLE_A"}, {'toggle_name': "TOGGLE_B"}]
        toggle_searcher = ToggleSearcher()
        toggle_searcher.is_used = MagicMock(return_value=False)

        rule = ToggleCleanupRule(my_toggles, toggle_searcher)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), False)

    def test_rule_fails_when_only_some_defined_toggles_are_used(self):
        my_toggles = [{'toggle_name': "TOGGLE_A"}, {'toggle_name': "TOGGLE_B"}]
        toggle_searcher = ToggleSearcher()
        mock = MagicMock()
        mock.side_effect = [True, False]
        toggle_searcher.is_used = mock

        rule = ToggleCleanupRule(my_toggles, toggle_searcher)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), False)

    def test_rule_passes_when_all_defined_toggles_are_used(self):
        my_toggles = [{'toggle_name': "TOGGLE_A"}, {'toggle_name': "TOGGLE_B"}]
        toggle_searcher = ToggleSearcher()
        toggle_searcher.is_used = MagicMock(return_value=True)

        rule = ToggleCleanupRule(my_toggles, toggle_searcher)
        rule.evaluate()
        self.assertEqual(rule.is_passed(), True)


if __name__ == '__main__':
    unittest.main()
