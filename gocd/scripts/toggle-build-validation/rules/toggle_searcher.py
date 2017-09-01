import subprocess
import os

class ToggleSearcher(object):

    __false_positives = ["OFF5TH", "OMS", "PAYPAL", "FEDEX", "PICK_UP_IN_STORE", "FIND IN STORE", "SHIP_FROM_STORE", "HTTPS"]

    def is_used(self, path, toggle):
        grep_and_count = 'grep -r --no-messages -w "%s" --include \*.scala --include \*.java --include \*.js --include \*.jsp --include \*.perl --include \*.pm --include \*.pkb %s | wc -l' % (toggle, path)
        output = subprocess.Popen(grep_and_count, stdout=subprocess.PIPE, shell=True).communicate()[0].strip()

        return int(output) > 0

    def is_false_positive(self, toggle):
        return toggle in self.__false_positives
