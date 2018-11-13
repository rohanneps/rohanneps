import numpy as np
import pandas as pd
import numpy as np

import json
import keras
import keras.preprocessing.text as kpt
from keras.preprocessing.text import Tokenizer
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation

max_words = 3000

def get_simple_model():
	model = Sequential()
	model.add(Dense(512, activation='relu', input_shape=(max_words,)))
	model.add(Dropout(0.2))
	model.add(Dense(256, activation='sigmoid'))
	model.add(Dropout(0.2))
	model.add(Dense(2, activation='softmax'))
	model.summary()
	return model



# INPUT FILE
df = pd.read_csv('sentiment_test.tsv',sep='\t',encoding='latin-1')
df = df[df['sentence'].notnull()]

# Segregate data and label
train_y = df['tag']
train_x = df['sentence']



# create a new Tokenizer
tokenizer = Tokenizer(num_words=max_words)
# feed data to tokenizer
tokenizer.fit_on_texts(train_x)

# Tokenizers come with a convenient list of words and IDs
dictionary = tokenizer.word_index
# Let's save this out so we can use it later
with open('dictionary.json', 'w') as dictionary_file:
	json.dump(dictionary, dictionary_file)


def convert_text_to_index_array(text):
	# one really important thing that `text_to_word_sequence` does is padding
	return [dictionary[word] for word in kpt.text_to_word_sequence(text)]

allWordIndices = []
# for each sentence, change each token to its ID in the Tokenizer's word_index
for text in train_x:
	wordIndices = convert_text_to_index_array(text)
	allWordIndices.append(wordIndices)

# now we have a list of all sentence converted to index arrays.
# cast as an array for future usage.
allWordIndices = np.asarray(allWordIndices)

# create one-hot matrices out of the indexed sentences
train_x = tokenizer.sequences_to_matrix(allWordIndices, mode='binary')
# treat the labels as categories
train_y = keras.utils.to_categorical(train_y, 2)

model = get_simple_model()

model.compile(loss='categorical_crossentropy',optimizer='adam',metrics=['acc'])
model.fit(train_x, train_y,batch_size=32,epochs=25,verbose=1,validation_split=0.1,shuffle=True)

# Save model to disk
model.save('model.h5')