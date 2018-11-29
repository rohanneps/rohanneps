from keras.models import load_model
import numpy as np
import pandas as pd
from sklearn.preprocessing import MinMaxScaler

if __name__ =='__main__':
	df = pd.read_csv('BostonHousing.csv', sep=',')
	features = df.iloc[:,:13].values
	output = df.iloc[:,13].values
	y_actual = output[0]
	output=	output.reshape(506,1)	
	
	x_test = np.expand_dims(features[0],axis=0)

	scalarX, scalarY = MinMaxScaler(), MinMaxScaler()
	scalarX.fit(features)
	scalarY.fit(output)
	X = scalarX.transform(features)
	y = scalarY.transform(output)
	x_test = scalarX.transform(x_test)


	model = load_model('regression_model.best.hdf5')

	y_predicted = model.predict(x_test)
	y_inverse_scaled = scalarY.inverse_transform(y_predicted)[0][0]


	print('Actual Value:{} . Predicted Value: {}'.format(y_actual, y_inverse_scaled))