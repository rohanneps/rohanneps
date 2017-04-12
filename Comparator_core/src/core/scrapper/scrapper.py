from core.base.baseTask import BaseTask
import pandas as pd
from selenium import webdriver
import os
import logging

class Scrapper(BaseTask):
	
	def __init__(self,url_to_scrape_file,field_to_xpath_file,output_file,logger,output_dir):
		self.url_to_scrape_file_df = pd.read_csv(url_to_scrape_file)
		self.field_identifier_file_df = pd.read_csv(field_to_xpath_file)
		self.output_file = output_file
		self.file_xpath_col_list = self.field_identifier_file_df.columns.tolist()
		self.logger = logger
		self.output_dir = output_dir
		
		# get dictionary from field and xpath
		self.field_identifier_dict = self.field_identifier_file_df.set_index(self.file_xpath_col_list[0])[self.file_xpath_col_list[1]].to_dict()
		# self.driver = webdriver.Firefox()
		self.driver = webdriver.PhantomJS()
		self.scrapped_data_df = pd.DataFrame(columns = [self.url_to_scrape_file_df.columns.tolist()[0]]+list(self.field_identifier_dict.keys()))

	def start_task(self):
		self.logger.info('Starting Scrapping task')
		self.url_to_scrape_file_df.apply(self.scrape_url,axis =1)
		self.scrapped_data_df.to_csv(os.path.join(self.output_dir,self.output_file),index=False)

	def stop_task(self):
		self.logger.info('Scrapping task completed')
		self.driver.quit()

	# For each url provided
	def scrape_url(self,row):
		primary_id = row[row.index.tolist()[0]]
		url = row[row.index.tolist()[1]]
		self.driver.get(url)
		# Data is stored in list for each row
		row_data_list = [primary_id]
		for key in self.field_identifier_dict:
			element_extraction_identifier = self.field_identifier_dict[key]
			try:
				scrapped_data = self.find_element(element_extraction_identifier,'xpath')
			except:
				scrapped_data = none
			row_data_list.append(scrapped_data)
		# print row_data_list
		self.scrapped_data_df = self.scrapped_data_df.append(pd.Series(row_data_list,index = self.scrapped_data_df.columns.tolist()),ignore_index=True)


	def find_element(self,element_extraction_identifier, type):
		if type =='xpath':
			return str(self.driver.find_element_by_xpath(element_extraction_identifier).text)




