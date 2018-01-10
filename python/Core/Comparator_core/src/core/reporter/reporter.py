from core.scrapper.scrapper import Scrapper
from core.comparator.comparator import Comparator
import pandas as pd
from selenium import webdriver
import os
import csv

class Reporter(Scrapper,Comparator):

	def __init__(self,url_to_scrape_file,field_to_xpath_file,platform_import_file,output_file,comparision_report_file,logger,output_dir,comp_type,max_error_threshold):
		self.url_to_scrape_file_df = pd.read_csv(url_to_scrape_file)
		self.field_identifier_file_df = pd.read_csv(field_to_xpath_file)
		self.platform_import_file = pd.read_csv(platform_import_file)

		# Scrapper file name
		self.output_file = output_file
		self.file_xpath_col_list = self.field_identifier_file_df.columns.tolist()
		# get dictionary from field and xpath
		self.field_identifier_dict = self.field_identifier_file_df.set_index(self.file_xpath_col_list[0])[self.file_xpath_col_list[1]].to_dict()
		self.scrapped_data_df = pd.DataFrame(columns = [self.url_to_scrape_file_df.columns.tolist()[0]]+list(self.field_identifier_dict.keys()))
		self.logger = logger
		self.output_dir = output_dir
		self.comp_type = comp_type

		self.report_csv = pd.DataFrame(columns = self.platform_import_file.columns.tolist())
		self.comparision_report_file = comparision_report_file
		self.page_not_found_list = []
		self.total_error_count = 0
		self.max_error_threshold = max_error_threshold
		# self.driver = webdriver.Firefox()
		self.driver = webdriver.PhantomJS()

	def start_task(self):
		self.logger.info('Starting Immediate Reporter task')
		self.url_to_scrape_file_df.apply(self.start_immediate_reporter,axis =1)


	def stop_task(self):
		self.scrapped_data_df.to_csv(os.path.join(self.output_dir,self.output_file),index=False,quoting=csv.QUOTE_ALL)
		self.logger.info('Scrapping task completed')
		self.driver.quit()
		self.logger.info('Comparision task completed')
		report_file_name = os.path.join(self.output_dir,self.comparision_report_file)
		self.report_csv.to_csv(report_file_name,index=False,quoting=csv.QUOTE_ALL)
		self.logger.debug('Report generated to file {}',format(report_file_name))
		self.logger.info('Total errors in file = %d' %(self.total_error_count))


	def start_immediate_reporter(self,row):
		if(self.total_error_count <= self.max_error_threshold):
			get_scrapped_row = self.scrape_url(row)
			self.scrapped_data_df = self.scrapped_data_df.append(pd.Series(get_scrapped_row,index = self.scrapped_data_df.columns.tolist()),ignore_index=True)

			primary_id_column = str(row.index.tolist()[0])
			primary_id = row[primary_id_column]
			try:
				platform_row = self.platform_import_file[self.platform_import_file[self.platform_import_file.columns.tolist()[0]] == primary_id].iloc[0]
				get_comparison_row = self.compare_data(platform_row)
			except:
				missing_report = self.get_missing_row_details(get_scrapped_row)
				get_comparison_row = missing_report
				self.total_error_count +=1
			get_comparison_row_series = pd.Series(get_comparison_row,index = self.platform_import_file.columns.tolist())
			self.report_csv = self.report_csv.append(get_comparison_row_series,ignore_index=True)
			# get_comparison_row_series.apply(self.get_total_error_count)
		else:
			return


	def get_missing_row_details(self,row):
		row_report = []
		row_report.append(row[0])

		for row_column_id in range(1,len(row)):
			row_column_value = row[row_column_id]
			row_report.append(str(row_column_value)+', Product not present in provided url')
		return row_report


	# def get_total_error_count(self,compared_row):
	# 	for row_column_id in range(1,len(compared_row)):
	# 		row_column_value = compared_row[row_column_id]
	# 		print row_column_value
	# 		if row_column_value != 'Data is same':
	# 			self.total_error_count += 1
	# 			if 'Page Not Found' in row_column_value :
	# 				return 
	# 	