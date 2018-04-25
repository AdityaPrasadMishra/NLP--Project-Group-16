#Importing Libraries
import re
import io

######---------------------Part 1- Data Preprocessing---------------------#############
#Importing the Dataset
languageone =open('IITB.en-hi.en', encoding = 'utf-8', errors = 'ignore').read().split('\n')
languagetwo =open('IITB.en-hi.hi', encoding = 'utf-8', errors = 'ignore').read().split('\n')

def clean_text(text):
    text= text.lower()
    text = re.sub(r"i'm", "i am", text)
    text = re.sub(r"he's", "he is", text)
    text = re.sub(r"she's", "she is", text)
    text = re.sub(r"that's", "that is", text)
    text = re.sub(r"what's", "what is", text)
    text = re.sub(r"where's", "where is", text)
    text = re.sub(r"\'ll", " will", text)
    text = re.sub(r"\'ve", " have", text)
    text = re.sub(r"\'re", " are", text)
    text = re.sub(r"\'d", " would", text)
    text = re.sub(r"won't", "will not", text)
    text = re.sub(r"can't", "cannot", text)
    text = re.sub(r"[-()\"#/@;:<>{}+=~|._?\',!%*]", "", text)
    text = re.sub(' +',' ', text)
    text = text.strip()
    return text

#Count number of words in a sentence
def count_words(line):
    count = len(re.findall(r'\w+', line))
    #print(count)
    return count

# Cleaning the languageone
clean_languageone = []
clean_languagetwo = []
total_lines_processed = 0
for i in range(len(languageone)):
    temp = clean_text(languageone[i])
    num_words = count_words(temp)
    total_lines_processed += 1
    if(num_words >=3 and num_words <=15):
        #print(temp)
        clean_languageone.append(temp)
        clean_languagetwo.append(clean_text(languagetwo[i]))    

# Create new files and then write the new corpus content to them
english_file = io.open("English.txt", "w", encoding="utf-8")
hindi_file = io.open("Hindi.txt", "w", encoding="utf-8")

for item in clean_languageone:
    english_file.write("%s\n" % item)

for item in clean_languagetwo:
    hindi_file.write("%s\n" % item)

english_file.close()
hindi_file.close()

print("Total lines processed are ",total_lines_processed)
## PRE-PROCESSING DONE