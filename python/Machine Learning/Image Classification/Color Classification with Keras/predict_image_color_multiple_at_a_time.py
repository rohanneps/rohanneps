import numpy as np
from keras.preprocessing.image import  img_to_array, load_img
from keras.models import load_model
import cv2
import os
from imutils import paths
from sklearn.preprocessing import LabelBinarizer

import time




rootPath = os.getcwd()

def predict():
	class_list = []
	image_list=[]
	imagePaths = sorted(list(paths.list_images(os.path.join(rootPath,'training_dataset'))))
	
	# Trainned Labels
	for img in imagePaths:
		label = img.split('/')[-2]
		class_list.append(label)
	labels = np.array(class_list)
	lb = LabelBinarizer()
	labels = lb.fit_transform(labels)
	
	
	# Images to predict
	imagePaths = sorted(list(paths.list_images(os.path.join(rootPath,'predict'))))
	# Loading trained model
	model=load_model(os.path.join(rootPath,'color_model.best.hdf5'))

	for image_path in imagePaths:
		image = cv2.imread(image_path)		
		# pre-process the image for classification
		image = cv2.resize(image, (28, 28))				# Resizing images to trainned images shape
		image = image.astype("float") / 255.0			# Normalization
		image = img_to_array(image)
		image = np.expand_dims(image, axis=0)
		image_list.append(image)	
	image_list = np.array(image_list, dtype="float")
	
	image_list = (y for y in image_list)				# converting list to generator
	predict = model.predict_generator(image_list, steps=4)
	# predict = model.predict(image_list)
	for x,pred in enumerate(predict):
		print('----------------------------------------------')
		print(imagePaths[x])
		idx = np.argmax(pred)
		label = lb.classes_[idx]
		print("predicted label: {}".format(label))
		print("confidence score: {}%".format(pred[idx]*100))	


if __name__=='__main__':
	start = time.time()
	predict()
	print(time.time()-start)