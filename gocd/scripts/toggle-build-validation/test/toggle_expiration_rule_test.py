import unittest
from rules import ToggleExpirationRule
from datetime import datetime, timedelta
import pytz

class ToggleExpirationRuleTest(unittest.TestCase):

    def test_rule_should_fail_when_all_toggles_are_aged_more_than_90_days(self):
        my_toggles = [
            {'toggle_name': "TOGGLE_A"},
            {'toggle_name': "TOGGLE_B"}
        ]
        reference_toggles = [
            {'toggle_name': 'TOGGLE_A', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc) - timedelta(days = 91))},
            {'toggle_name': 'TOGGLE_B', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc) - timedelta(days = 91))},
            {'toggle_name': 'TOGGLE_C', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc) - timedelta(days = 91))}
        ]

        rule = ToggleExpirationRule(my_toggles, reference_toggles)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), False)

    def test_rule_should_fail_when_some_toggles_are_old(self):
        my_toggles = [
            {'toggle_name': "TOGGLE_A"},
            {'toggle_name': "TOGGLE_B"}
        ]
        reference_toggles = [
            {'toggle_name': 'TOGGLE_A', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc))},
            {'toggle_name': 'TOGGLE_B', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc) - timedelta(days = 91))},
            {'toggle_name': 'TOGGLE_C', 'toggle_type': 'Temporary', 'modified_timestamp': str(datetime.now(pytz.utc) - timedelta(days = 91))}
        ]

        rule = ToggleExpirationRule(my_toggles, reference_toggles)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), False)

    def test_rule_should_pass_when_all_toggles_are_young(self):
        my_toggles = [
            {'toggle_name': "TOGGLE_A"},
            {'toggle_name': "TOGGLE_B"}
        ]
        reference_toggles = [
            {'toggle_name': 'TOGGLE_A', 'toggle_type': 'Project', 'modified_timestamp': str(datetime.now(pytz.utc))},
            {'toggle_name': 'TOGGLE_B', 'toggle_type': 'Project', 'modified_timestamp': str(datetime.now(pytz.utc))}
        ]

        rule = ToggleExpirationRule(my_toggles, reference_toggles)
        rule.evaluate()
        self.assertEquals(rule.is_passed(), True)

if __name__ == '__main__':
    unittest.main()