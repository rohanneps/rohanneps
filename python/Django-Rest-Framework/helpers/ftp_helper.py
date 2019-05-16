from ftplib import FTP_TLS
from django.conf import settings
import logging
from . import helper
import os

comp_logger = logging.getLogger(__name__)


def copy_ftp_file(project_id, client_name, file_name):
	ftp =  FTP_TLS(settings.FTP_LOCATION)
	ftp.sendcmd("USER {}".format(settings.FTP_USER))
	ftp.sendcmd("PASS {}".format(settings.FTP_PASS))
	comp_logger.info('Initiating ftp file transfer for file {} for client {}'.format(file_name, client_name))
	ftp.cwd(client_name)

	# create project input dir
	project_dir = os.path.join(settings.PROJECT_DIR,str(project_id))
	helper.create_dir(project_dir)

	project_input_dir = os.path.join(project_dir, settings.PROJECT_INPUT_FOLDER)
	helper.create_dir(project_input_dir)

	local_filename = os.path.join(project_input_dir, file_name)
	lf = open(local_filename, "wb")
	ftp.retrbinary("RETR " + file_name, lf.write, 8*1024)
	lf.close()
	comp_logger.info('Completed Copying file {} for client {}'.format(file_name, client_name))