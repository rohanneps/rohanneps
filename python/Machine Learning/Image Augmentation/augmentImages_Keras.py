from keras.preprocessing.image import ImageDataGenerator
import os
from keras.preprocessing.image import img_to_array
import cv2
from imutils import paths
import numpy as np
import random
import argparse

if __name__ == '__main__':
	parser = argparse.ArgumentParser(description='Augment Images for deep learning Model training. Images are generated in the same folder as input.')
	parser.add_argument("--img_path", help="Image Directory path here", type=str) 
	parser.add_argument("--num_images", help="Number of Images to be augmented for each category", type=int) 
	args = parser.parse_args()
	
	img_path = args.img_path
	num_images = args.num_images

	# grab the image paths and randomly shuffle them
	imagePaths = sorted(list(paths.list_images(img_path)))
	random.seed(42)
	random.shuffle(imagePaths)

	datagen = ImageDataGenerator(rotation_range=40,
	        width_shift_range=0.2,
	        height_shift_range=0.2,
	        shear_range=0.2,
	        zoom_range=0.2,
	        horizontal_flip=True,
	        vertical_flip=True)

	for imagePath in imagePaths:
		image = cv2.imread(imagePath)
		image = cv2.resize(image, (512, 512))
		image = img_to_array(image)
		image = image.reshape((1,) + image.shape)

		i = 0
		datagen.fit(image)
		label = imagePath.split('/')[-2]
		dir_path = os.path.join('./image_datagen',label)
		if not os.path.exists(dir_path):
			os.makedirs(dir_path)
		for batch in datagen.flow(image,save_to_dir=dir_path, save_prefix='img', save_format='jpg',batch_size=32):
			i += 1
			if i > num_images:
				break

		# exit(1)