import os
import csv
import string
for folder, subs, files in os.walk('/media/laveena/Academics/Natural language processing/NLP Corpus/dev_test_tokenized'):
        with open(os.path.join(folder, 'English.csv'), 'w+') as dest1,  open(os.path.join(folder, 'Hindi.csv'), 'w+') as dest2:
            wr1 = csv.writer(dest1)
            wr2 = csv.writer(dest2)
            for filename in files:
                if filename.endswith('en'):
                    with open(os.path.join(folder, filename), 'r') as src:
                        text = src.readlines()
                        words = []
                        for str in text:
                            str = str.translate(None, string.punctuation)
                            words = str.split()
                            words.append("EOF")
                            wr1.writerow(words)
                else:
                    with open(os.path.join(folder, filename), 'r') as src:
                        text = src.readlines()
                        words = []
                        for str in text:
                            str = str.translate(None, string.punctuation)
                            words = str.split()
                            words.append("EOF")
                            wr2.writerow(words)
                src.close()
        dest2.close()
        dest1.close()



















