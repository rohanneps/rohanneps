from core.utils.config import Config
from core.scrapper.scrapper import Scrapper
from core.comparator.comparator import Comparator

import os


class TaskLoader(object):

	def __init__(self,logger,output_dir):
		self.logger = logger
		self.output_dir = output_dir

		self.config = Config('config.ini')
		self.magento_import_file = self.config.get_config_value('input_files','magento_import_file')
		self.url_to_scrape_file = self.config.get_config_value('input_files','url_to_scrape_file')
		self.field_to_xpath_file = self.config.get_config_value('input_files','field_to_xpath_file')

		#get output files
		self.output_file = self.config.get_config_value('output_files','scrapped_output_csv')
		self.comparision_report_file = self.config.get_config_value('output_files','comparison_report')
		self.scrapper = Scrapper(self.url_to_scrape_file,self.field_to_xpath_file,self.output_file,self.logger,self.output_dir)


	def start_task(self):
		#Start Scrapping Task
		self.logger.info('*****************************')
		self.scrapper.start_task()
		self.scrapper.stop_task()
		self.logger.info('*****************************')


		#Comparison task
		if (os.path.exists(os.path.join(self.output_dir,self.output_file))):
			self.comparator = Comparator(self.magento_import_file,self.output_file,self.comparision_report_file,self.logger,self.output_dir)
			self.logger.info( '*****************************')
			self.comparator.start_task()
			self.comparator.stop_task()
			self.logger.info('*****************************')
		else:
			self.logger.info('Output csv missing')
			

		self.logger.info('*****************************')
		self.logger.info('Closing Comparator.')
		self.logger.info('*****************************')