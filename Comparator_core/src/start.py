from core.loader.load import TaskLoader
import logging
import os


if __name__=='__main__':
	
	log_directory = 'Log'

	if not os.path.exists(log_directory):
		os.makedirs(log_directory)

	output_dir = 'Output'
	if not os.path.exists(output_dir):
		os.makedirs(output_dir)

	logging.basicConfig(filemode = 'a')
	logger = logging.getLogger(__name__)
	logger.setLevel(logging.INFO)

	# File handler
	handler = logging.FileHandler(os.path.join(log_directory,'Comparator_Log.log'))
	handler.setLevel(logging.INFO)

	# Logging Format
	formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
	handler.setFormatter(formatter)

	logger.addHandler(handler)

	comp_type = 'bulk'
	max_error_threshold = 0

	if comp_type =='immediate':
		task_loader = TaskLoader(logger,output_dir,comp_type,max_error_threshold)
	else:
		task_loader = TaskLoader(logger,output_dir,comp_type)

	task_loader.start_task()

	
	
	


