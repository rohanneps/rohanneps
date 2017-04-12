from core.base.baseTask import BaseTask
import pandas as pd
import os
import logging

class Comparator(BaseTask):

	def __init__(self,magento_import_file,output_file,comparision_report_file,logger,output_dir):
		self.logger = logger
		self.output_dir = output_dir
		self.magento_import_file = pd.read_csv(magento_import_file)
		self.output_file = pd.read_csv(os.path.join(self.output_dir,output_file))

		self.logger = logger
		self.output_dir = output_dir

		self.report_csv = pd.DataFrame(columns = self.magento_import_file.columns.tolist())
		self.comparision_report_file = comparision_report_file


	def start_task(self):
		self.logger.info('Starting comparision task')
		self.magento_import_file.apply(self.compare_data,axis=1)
		

	def stop_task(self):
		self.logger.info('Comparision task completed')
		report_file_name = os.path.join(self.output_dir,self.comparision_report_file)
		self.report_csv.to_csv(report_file_name,index=False)
		self.logger.debug('Report generated to file {}',format(report_file_name))

	# Data comparison for each row
	def compare_data(self,row):
		primary_id_column = str(row.index.tolist()[0])
		primary_id = row[primary_id_column]
		# Report is stored in list for each row
		row_report = [primary_id]

		self.logger.setLevel(logging.DEBUG)
		self.logger.debug('Comparing for {}'.format(primary_id))
		self.logger.setLevel(logging.INFO)

		try:
			scrapped_data_row = self.output_file[self.output_file[self.output_file.columns.tolist()[0]] == primary_id].iloc[0]
			if len(scrapped_data_row)>0:
				for column in self.magento_import_file.columns.tolist():
					if column != primary_id_column:
						column_value = row[column].strip()
						try:
							scrapped_column_data = scrapped_data_row[column].strip()
							if column_value == scrapped_column_data:
								row_report.append('Data is same')
							else:
								row_report.append('Data is not same')
						except KeyError:
							row_report.append('Column missing in provided fields to scrape')
			else:
				row_report.append('Row missing scrapped data')
		except IndexError:
			missing_report = ['Product not present in provided url' for x in range(1,len(self.magento_import_file.columns.tolist()))]
			row_report = row_report+(missing_report)
		# print row_report
		self.report_csv = self.report_csv.append(pd.Series(row_report,index = self.magento_import_file.columns.tolist()),ignore_index=True)
		

		



