from keras.models import Sequential
from keras.layers.normalization import BatchNormalization
from keras.layers.convolutional import Conv2D
from keras.layers.convolutional import MaxPooling2D
from keras.layers.core import Activation
from keras.layers.core import Flatten
from keras.layers.core import Dropout
from keras.layers.core import Dense
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator
from keras.optimizers import Adam
from sklearn.model_selection import train_test_split
from keras.preprocessing.image import img_to_array
from keras.utils import to_categorical
from imutils import paths
import numpy as np
import random
import cv2
from sklearn.preprocessing import LabelBinarizer
import os 
import shutil
from keras.callbacks import ReduceLROnPlateau,TensorBoard,ModelCheckpoint

# initialize the number of epochs to train for, initia learning rate,
# and batch size
EPOCHS = 10
INIT_LR = 1e-3
BS = 32


def build(width, height, depth, classes):
	# initialize the model along with the input shape to be
	# "channels last" and the channels dimension itself
	model = Sequential()
	inputShape = (height, width, depth)
	chanDim = -1

	# if we are using "channels first", update the input shape
	# and channels dimension
	if K.image_data_format() == "channels_first":
		inputShape = (depth, height, width)
		chanDim = 1
	# CONV => RELU => POOL
	model.add(Conv2D(32, (3, 3), padding="same",
		input_shape=inputShape))
	model.add(Activation("relu"))
	model.add(BatchNormalization(axis=chanDim))
	model.add(MaxPooling2D(pool_size=(3, 3)))
	model.add(Dropout(0.25))
	# (CONV => RELU) * 2 => POOL
	model.add(Conv2D(64, (3, 3), padding="same",
		input_shape=inputShape))
	model.add(Activation("relu"))
	model.add(BatchNormalization(axis=chanDim))
	model.add(Conv2D(64, (3, 3), padding="same",
		input_shape=inputShape))
	model.add(Activation("relu"))
	model.add(BatchNormalization(axis=chanDim))
	model.add(MaxPooling2D(pool_size=(2, 2)))
	model.add(Dropout(0.25))
	# (CONV => RELU) * 2 => POOL
	model.add(Conv2D(128, (3, 3), padding="same",
		input_shape=inputShape))
	model.add(Activation("relu"))
	model.add(BatchNormalization(axis=chanDim))
	model.add(Conv2D(128, (3, 3), padding="same",
		input_shape=inputShape))
	model.add(Activation("relu"))
	model.add(BatchNormalization(axis=chanDim))
	model.add(MaxPooling2D(pool_size=(2, 2)))
	model.add(Dropout(0.25))
	# first (and only) set of FC => RELU layers
	model.add(Flatten())
	model.add(Dense(1024))
	model.add(Activation("relu"))
	model.add(BatchNormalization())
	model.add(Dropout(0.5))

	# softmax classifier
	model.add(Dense(classes))
	model.add(Activation("softmax"))

	# return the constructed network architecture
	return model

rootPath = os.getcwd()


# grab the image paths and randomly shuffle them
# imagePaths = sorted(list(paths.list_images('/home/ubuntu/deeplearning/First50/images/')))
imagePaths = sorted(list(paths.list_images(os.path.join(rootPath,'training_dataset'))))
# initialize the data and labels

print("[INFO] loading images...")
data = []
labels = []
file_count = len(imagePaths)
print(file_count)
if file_count<100:
	print('Cannot get good Images Training with less than '+str(file_count)+' Images, Please Add')
	exit(0)

random.seed(50)
random.shuffle(imagePaths)

for imagePath in imagePaths:
	if ' ' in imagePath.split('/')[-1]:
		imageName = imagePath.split('/')[-1].replace('\ ','_')
		labelFolder = imagePath.split('/')[-2]
		destPath = os.path.join(rootPath,labelFolder,imageName)
		print('image has issue')
		print(imagePath)
		print(type(imagePath))
		print('{} moved to {}'.format(imagePath, destPath))
		shutil.move(imagePath, destPath)
		imagePath = destPath
	image = cv2.imread(imagePath)
	image = cv2.resize(image, (28, 28))
	image = img_to_array(image)
	data.append(image)	
	label = imagePath.split('/')[-2]
	#print(label)
	#label = 1 if label == "Matched" else 0
	labels.append(label)


data = np.array(data, dtype="float") / 255.0
labels = np.array(labels)

lb = LabelBinarizer()
labels = lb.fit_transform(labels)

# partition the data into training and testing splits using 75% of
# the data for training and the remaining 25% for testing
(trainX, testX, trainY, testY) = train_test_split(data,
	labels, test_size=0.25, random_state=42)


# construct the image generator for data augmentation
aug = ImageDataGenerator(rotation_range=30, width_shift_range=0.1,
	height_shift_range=0.1, shear_range=0.2, zoom_range=0.2,
	horizontal_flip=True, fill_mode="nearest")

# initialize the model
print("[INFO] compiling model...")
model = build(width=28, height=28, depth=3, classes=4)
opt = Adam(lr=INIT_LR, decay=INIT_LR / EPOCHS)
model.compile(loss="categorical_crossentropy", optimizer=opt,	metrics=["accuracy"])

# train the network
print("[INFO] training network...")

reduce_lr_on_plateau = ReduceLROnPlateau(monitor='val_loss', factor=0.2, patience=3, verbose=0, mode='auto', min_delta=0.0001, cooldown=0, min_lr=0.001)
tensorboard = TensorBoard(log_dir="logs",write_graph=True)
filepath="color_model.best.hdf5"
model_check_point = ModelCheckpoint(filepath, monitor='val_acc', verbose=0, save_best_only=True, mode='max', period=1)

H = model.fit_generator(aug.flow(trainX, trainY, batch_size=BS),
	validation_data=(testX, testY), steps_per_epoch=len(trainX) // BS, callbacks = [reduce_lr_on_plateau,model_check_point,tensorboard],
	epochs=EPOCHS, verbose=1)

# save the model to disk
print("[INFO] serializing network...")
model.save(os.path.join(rootPath,'model_color_final.h5'))

# Model summary
print(model.summary())



