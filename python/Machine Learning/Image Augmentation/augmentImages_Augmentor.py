import os
import Augmentor
import shutil
from PIL import Image
ROOT_DIR = 'images'
NUM_OF_IMAGES  = 3


if __name__ =='__main__':

	for roots, dirs, files in os.walk(ROOT_DIR):
		for __dir__ in dirs:
			
			# if __dir__=='Activyl':
			dir_full_path = os.path.join(ROOT_DIR,__dir__)

			for subroots,subdirs,subfiles in os.walk(dir_full_path):
				
				print(__dir__)
				print(len(subfiles))
				p = Augmentor.Pipeline(dir_full_path, output_directory='.')
				p.rotate90(probability=0.3)
				p.rotate270(probability=0.3)
				p.flip_left_right(probability=0.3)
				p.flip_top_bottom(probability=0.3)
				p.skew_tilt(probability=0.3)
				p.skew_top_bottom(probability=0.3)
				p.random_distortion(probability=0.5, grid_width=4, grid_height=4, magnitude=8)
				p.crop_random(probability=1, percentage_area=0.5)
				p.sample(NUM_OF_IMAGES)
					