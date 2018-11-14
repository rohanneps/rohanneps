import json
import numpy as np
import keras
from keras.preprocessing.text import Tokenizer, text_to_word_sequence
from keras.models import model_from_json
from keras.models import load_model

tokenizer = Tokenizer(num_words=3000)
# text labels
labels = ['negative', 'positive']

# read in our saved dictionary of words and indexes
with open('dictionary.json', 'r') as dictionary_file:
    dictionary = json.load(dictionary_file)

# get index of word from dictionary
def convert_text_to_index_array(text):
    words = text_to_word_sequence(text)
    wordIndices = []
    for word in words:
        if word in dictionary:
            wordIndices.append(dictionary[word])
        else:
            print("'%s' not in training corpus; ignoring." %(word))
    return wordIndices

# load saved model
model=load_model('model.h5')

evalSentence = 'bad day'
# format your input for the neural net
testArr = convert_text_to_index_array(evalSentence)
input = tokenizer.sequences_to_matrix([testArr], mode='binary')
# make prediction
pred = model.predict(input)
print(pred)
print("%s sentiment; %f%% confidence" % (labels[np.argmax(pred)], pred[0][np.argmax(pred)] * 100))