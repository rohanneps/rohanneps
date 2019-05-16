from django.db import models
from django.conf import settings
from django.db.models.signals import pre_save
from helpers import helper, ftp_helper
import logging
import traceback

comp_logger = logging.getLogger(__name__)

# Create your models here.
class Project(models.Model):
	class Meta:
		verbose_name_plural = 'Projects'

	client_name = models.CharField(max_length=250, blank=False,null=False)
	# status_list = (
	# 	('Pending', 'Pending'),
	# 	('InProgress', 'InProgress'),
	# 	('Completed', 'Completed'),
	# 	('Failed', 'Failed'),
	# 	('Terminated','Terminated'),
	# 	('H_InProgress', 'H_InProgress'),
	# 	('H_Completed', 'H_Completed'),
	# )
	start_date = models.DateTimeField(auto_now=False, auto_now_add=True)
	last_updated_date = models.DateTimeField(auto_now=True, auto_now_add=False)
	file_name = models.CharField(max_length=250, blank=False,null=False)
	status = models.CharField(max_length=20,choices=settings.PROJECT_STATUS_LIST,blank=False, null=False)
	failed_log = models.CharField(max_length=500, blank=True,null=True)
	total_records = models.IntegerField(default=0)
	total_unique_products = models.IntegerField(default=0)

	def __str__(self):
		return str(self.client_name)



class ProjectStatus(models.Model):
	class Meta:
		verbose_name_plural = 'ProjectStatus'

	project = models.ForeignKey(Project, on_delete = models.CASCADE)
	start_datetime =models.DateTimeField(auto_now=False, auto_now_add=True)
	last_updated_date = models.DateTimeField(auto_now=True, auto_now_add=False)
	project_phase = models.CharField(max_length=20,choices=list(settings.PROJECT_PROCESS_STATUS_DICT.items()),blank=False, null=False)
	project_completion_percentage = models.CharField(max_length=20,blank=False, null=False, default='0.0%')
	asynctask_id = models.CharField(max_length=50,blank=False, null=False, default='-')
	def __str__(self):
		return str(self.project.id)


def get_project_last_id():
	try:
		last_obj_id = Project.objects.last().id
	except:
		last_obj_id = 0
	return last_obj_id


def project_presave_criterias(sender, instance, *args, **kwarys):
	if not instance.id:		
		# check to see this is created instance or saved instace

		# For created instance
		# copy ftp file
		current_id = get_project_last_id()+1

		try:
			ftp_helper.copy_ftp_file(project_id=current_id,client_name=instance.client_name, file_name=instance.file_name)
		except Exception as e:
			comp_logger.info(str(e))
			# Exception handling for ftp file transfer
			comp_logger.info('ftp file transfer error for client_name:{} and file:{}'.format(instance.client_name, instance.file_name))
			traceback_error = traceback.format_exc()
			comp_logger.info(traceback_error)
			comp_logger.info('Cannot save project for client_name:{} and file:{}'.format(instance.client_name, instance.file_name))
			raise Exception('Cannot Create Project. Ftp file transfer issue')

		# getting the total number of records and unique product count
		total_records,total_unique_products = helper.get_unique_products_and_total_count(current_id,instance.file_name)
		instance.total_records = total_records
		instance.total_unique_products = total_unique_products
		


pre_save.connect(project_presave_criterias, sender=Project)
