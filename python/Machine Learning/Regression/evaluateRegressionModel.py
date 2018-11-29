import numpy as np
import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasRegressor
from keras.layers.normalization import BatchNormalization
from keras.optimizers import Adam
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline

seed = 7
np.random.seed(seed)

def getModel():
	model = Sequential()
	model.add(Dense(20, input_dim=13,kernel_initializer='normal', activation='relu'))		# initialize random weights of keras layers
	model.add(Dense(8, kernel_initializer='normal', activation='relu'))
	model.add(BatchNormalization())
	model.add(Dense(8, kernel_initializer='normal',activation='relu'))
	model.add(BatchNormalization())
	model.add(Dense(8, kernel_initializer='normal',activation='relu'))
	model.add(Dense(1, kernel_initializer='normal'))						#No activation function because we are interested in predicting numerical values directly without transform
	opt = Adam(lr = 0.001, decay=.001/150)
	model.compile(loss='mse',optimizer = opt, metrics=['mse'])
	return model


if __name__=='__main__':
	df = pd.read_csv('BostonHousing.csv', sep=',')
	
	# split into input (X) and output (Y) variables
	X = df.iloc[:,0:13].values
	Y = df.iloc[:,13].values
	
	# evaluate model with standardized dataset
	# estimator = KerasRegressor(build_fn=getModel, epochs=100, batch_size=5, verbose=0)

	estimators = []
	# scaling attributes 
	estimators.append(('standardize', StandardScaler()))
	estimators.append(('mlp', KerasRegressor(build_fn=getModel, epochs=50, batch_size=5, verbose=0)))
	pipeline = Pipeline(estimators)

	kfold = KFold(n_splits=10, random_state=seed)
	# results = cross_val_score(estimator, X, Y, cv=kfold)
	results = cross_val_score(pipeline, X, Y, cv=kfold)
	print("Results: %.2f (%.2f) MSE" % (results.mean(), results.std()))