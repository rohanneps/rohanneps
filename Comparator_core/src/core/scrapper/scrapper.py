from core.base.baseTask import BaseTask
import pandas as pd
from selenium import webdriver
import os
import logging
import requests
import csv

class Scrapper(BaseTask):
	
	def __init__(self,url_to_scrape_file,field_to_xpath_file,output_file,logger,output_dir,comp_type):
		self.url_to_scrape_file_df = pd.read_csv(url_to_scrape_file)
		self.field_identifier_file_df = pd.read_csv(field_to_xpath_file)
		self.output_file = output_file
		self.file_xpath_col_list = self.field_identifier_file_df.columns.tolist()
		self.logger = logger
		self.output_dir = output_dir
		self.comp_type = comp_type
		
		# get dictionary from field and xpath
		self.field_identifier_dict = self.field_identifier_file_df.set_index(self.file_xpath_col_list[0])[self.file_xpath_col_list[1]].to_dict()
		# self.driver = webdriver.Firefox()
		self.driver = webdriver.PhantomJS()
		self.scrapped_data_df = pd.DataFrame(columns = [self.url_to_scrape_file_df.columns.tolist()[0]]+list(self.field_identifier_dict.keys()))
		self.page_not_found_list = []

	def start_task(self):
		self.logger.info('Starting Scrapping task')
		self.url_to_scrape_file_df.apply(self.scrape_url,axis =1)
		

	def stop_task(self):
		self.scrapped_data_df.to_csv(os.path.join(self.output_dir,self.output_file),index=False,quoting=csv.QUOTE_ALL)
		self.logger.info('Scrapping task completed')
		self.driver.quit()

	# For each url provided
	def scrape_url(self,row):
		primary_id = row[row.index.tolist()[0]]
		url = row[row.index.tolist()[1]]
		
		# Data is stored in list for each row
		row_data_list = [primary_id]

		#Checking to see if page doesn't exist
		request = requests.get(url)
		if request.status_code == 404:
			# print url
			row_data_not_found_list = ['Page Not Found' for x in range(1,len(self.scrapped_data_df.columns.tolist()))]
			row_data_list = row_data_list + row_data_not_found_list
			self.page_not_found_list.append(primary_id)
		else:
			self.driver.get(url)
			for key in self.field_identifier_dict:
				element_extraction_identifier = self.field_identifier_dict[key]
				try:
					scrapped_data = self.find_element(element_extraction_identifier,'xpath')
				except:
					self.logger.info(primary_id+'--- has error with xpath ---'+element_extraction_identifier)
					scrapped_data = None
				row_data_list.append(scrapped_data)
		# print row_data_list
		self.logger.info(row_data_list)
		if self.comp_type=='immediate':
			return row_data_list
		else:
			self.scrapped_data_df = self.scrapped_data_df.append(pd.Series(row_data_list,index = self.scrapped_data_df.columns.tolist()),ignore_index=True)


	def find_element(self,element_extraction_identifier, type):
		if type =='xpath':
			page_element = self.driver.find_element_by_xpath(element_extraction_identifier)
			if str(page_element.tag_name) == 'select':
				option_list = []
				for option in page_element.find_elements_by_tag_name("option"):
					option_list.append(option.text)
				xpath_scrapped_data = ';;'.join(option_list)

			elif str(page_element.tag_name) == 'img':
				image_src_name = page_element.get_attribute('src')
				image_name = str(image_src_name.rsplit('/')[-1])
				xpath_scrapped_data = str('/'+image_name)

			else:
				xpath_scrapped_data = str(page_element.text)
				#Checking for attribute options
				if "\n" in xpath_scrapped_data:
					option_list = []
					for option in xpath_scrapped_data.split():
						option_list.append(str(option))
					xpath_scrapped_data = ';;'.join(option_list)
					

			return xpath_scrapped_data




