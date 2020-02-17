import json
import pandas as pd

INPUT_FILE = 'client_walmart_result.tsv'

JSON_LIST = []

ROW_ID = 0

def getRowJson(row):
	row_dict = {}
	global ROW_ID

	row_dict['row_id'] = ROW_ID

	row_dict['r_title'] = row['r_title']
	row_dict['s_title'] = row['s_itemname']
	row_dict['r_image_url'] = "https://"+row['r_image']
	row_dict['s_image_url'] = row['s_image_url']
	row_dict['r_price'] = row['r_price']
	row_dict['s_price'] = row['s_price']
	row_dict['s_prod_url'] = row['s_product_url']
	row_dict['r_prod_url'] = row['r_link']

	ROW_ID += 1

	JSON_LIST.append(row_dict)

if __name__ == '__main__':
	df = pd.read_csv(INPUT_FILE, sep='\t')
	df = df.iloc[:30]
	df = df.fillna(value='')
	df.apply(getRowJson, axis=1)

	with open('itemPair.json','w') as outputFile:
		json.dump(JSON_LIST, outputFile)