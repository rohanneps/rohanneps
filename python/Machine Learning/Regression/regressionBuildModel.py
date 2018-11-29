import pandas as pd
from keras.models import Sequential
from keras.layers import Dense, Activation
from keras.layers.normalization import BatchNormalization
from keras.optimizers import Adam
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from keras.callbacks import TensorBoard,ModelCheckpoint

np.random.seed(3)


def getModel():
	model = Sequential()
	model.add(Dense(20, input_dim=13, activation='relu'))		#input indicates number of features
	model.add(Dense(8, activation='relu'))
	model.add(BatchNormalization())
	model.add(Dense(1, activation='linear'))
	opt = Adam(lr = 0.001, decay=.001/150)
	model.compile(loss='mse',optimizer = opt, metrics=['mse'])
	return model


if __name__=='__main__':
	df = pd.read_csv('BostonHousing.csv', sep=',')
	features = df.iloc[:,:13].values
	output = df.iloc[:,13].values							# output values are in a list

	output=	output.reshape(506,1)						# keeping values in a list itself
	scalarX, scalarY = MinMaxScaler(), MinMaxScaler()
	scalarX.fit(features)
	scalarY.fit(output)
	X = scalarX.transform(features)
	y = scalarY.transform(output)

	model = getModel()
	tensorboard = TensorBoard(log_dir="logs",write_graph=True)
	filepath="regression_model.best.hdf5"
	model_check_point = ModelCheckpoint(filepath, monitor='val_mean_squared_error', verbose=0, save_best_only=True, mode='auto', period=1)

	h = model.fit(X, y, epochs=150, batch_size=16, validation_split=0.2,shuffle=True, callbacks = [model_check_point,tensorboard],)
	print(min(h.history['val_mean_squared_error']))
	print(min(h.history['mean_squared_error']))
	# model.save('regression_model.save')