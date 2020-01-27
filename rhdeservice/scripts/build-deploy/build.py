#!/usr/bin/env python3

import os
from builtins import zip

import yaml
import json
import time
import shlex
import shutil
import gspread
import logging
import myjprops
import subprocess
import collections
from datetime import datetime
from oauth2client.service_account import ServiceAccountCredentials
import pathlib


class Build(object):

    _CREDENTIAL = './config/credential.json'
    _GOOGLE_SHEET_NAME = 'GRP__Configuration__Parameter'
    _SHEET_DEPLOYMENT_TAB_NAME = 'configuration'

    start_time = int(round(time.time()))

    def __init__(self):
        self.setup_logger()
        self.set_up()

    def setup_logger(self):
        logging_format = '%(asctime)s %(filename)s:%(lineno)d %(levelname)s - %(message)s'
        logging.basicConfig(level=logging.INFO, format=logging_format)
        self.logger = logging.getLogger()
        if os.path.exists(os.getcwd() + '/log') is False:
            os.mkdir(os.getcwd() + '/log')
        self.log_file_path = os.getcwd() + '/log/build_{}.log'.format(datetime.now().strftime('%Y%m%d_%H%M%S'))
        print('Logging output in - {}'.format(self.log_file_path))
        handler = logging.FileHandler(self.log_file_path)
        formatter = logging.Formatter(logging_format)
        handler.setFormatter(formatter)
        self.logger.addHandler(handler)

    def set_up(self):
        self.logger.info('Google sheet conneting...')
        scope = ['https://spreadsheets.google.com/feeds', 'https://www.googleapis.com/auth/drive']
        credentials = ServiceAccountCredentials.from_json_keyfile_name(self._CREDENTIAL, scope)
        g = gspread.authorize(credentials)
        self.g_sheet = g.open(self._GOOGLE_SHEET_NAME)
        self.read_configuration()

    def read_configuration(self):
        range = self.g_sheet.worksheet(self._SHEET_DEPLOYMENT_TAB_NAME).get_all_values()
        self.parameters = { row[2] : row[3] for row in range[2:] }
        obj_deployment_package_path = self.parameters['deployment_package_path']
        pathlib.Path(obj_deployment_package_path).mkdir(parents=True, exist_ok=True)
        self.deployment_package_path = obj_deployment_package_path + '/grp_{}'.format(datetime.now().strftime('%Y%m%d_%H%M%S'))
        self.jdbc_connection = 'jdbc:postgresql://' + self.parameters['postgres_host_ip'] + ':' + self.parameters['postgres_host_port'] + '/' + self.parameters['database_name']
        os.mkdir(self.deployment_package_path)
        with open(self.deployment_package_path + '/configuration.json', 'w') as jsonFile:
            print(json.dumps(self.parameters, indent=4), file=jsonFile)
        self.logger.info('Read configuration tab')

    def read_container(self):
        range = self.g_sheet.worksheet(self.parameters['container_sheet']).get_all_values()
        keys = [k.replace('.', '_') for k in range[1]]
        self.container_configuration = [dict(zip(keys, row)) for row in range[2:]]
        self.container_configuration.sort(key=lambda x: int(x['#']), reverse=False)
        with open(self.deployment_package_path + '/container.json', 'w') as jsonFile:
            print(json.dumps(self.container_configuration, indent=4), file=jsonFile)
        self.logger.info('Read {} tab'.format(self.parameters['container_sheet']))

    def build_container(self):

        self.deployment_container_dist_dir = self.deployment_package_path + '/container'
        os.mkdir(self.deployment_container_dist_dir)

        for instance in self.container_configuration:
            if instance['type'] != 'service' or instance['enable'] != 'Yes':
                continue
            self.build_service(instance)

    def build_service(self, instance):
        service_path = instance['path']
        bash_command = './build-script/build-container.sh {name} {environment} {restart_enabled} {application_name} '\
            '{instance_hostname} {server_port} {version} {serviceUrl_defaultZone} {eureka_instance} {log_file} {archived_log} ' \
           '{repository_name}/{path}/{name} {container_type} {datasource_url} {datasource_username} {datasource_password} {eureka_instance_appname}' \
           .format(**instance)
        bash_command = bash_command + ' {repository_root_path} {server_location} {email_log} {log_file_path} {deployment_container_dist_dir}' \
           .format(log_file_path=self.log_file_path, deployment_container_dist_dir=self.deployment_container_dist_dir, **self.parameters)
        subprocess.call(shlex.split(bash_command))


    def copytree(self, src, dst, symlinks=False, ignore=None):
        for item in os.listdir(src):
            s = os.path.join(src, item)
            d = os.path.join(dst, item)
            if os.path.isdir(s):
                shutil.copytree(s, d, symlinks, ignore)
            else:
                shutil.copy2(s, d)

    def copy_scripts(self):
        deploy_script_dir = self.deployment_package_path + '/deploy'
        os.mkdir(deploy_script_dir)
        self.copytree('deploy', deploy_script_dir)
        shutil.copy2('./deploy.py', self.deployment_package_path)

        shutil.make_archive(self.deployment_package_path, 'zip', self.deployment_package_path)
        shutil.rmtree(self.deployment_package_path, ignore_errors=False, onerror=None)


if __name__ == '__main__':

    build = Build()

    if build.parameters['build_container'] == 'Yes':
        build.read_container()

    if build.parameters['build_container'] == 'Yes':
        build.build_container()

    build.copy_scripts()

    diffTime = int(round(time.time())) - build.start_time
    start = "\033[93m\033[1m"
    end = "\033[0m"
    print(start + '\nTotal execution time: {:02d}:{:02d}:{:02d}'.format(round(diffTime / 3600), round(diffTime % 3600 / 60), round(diffTime % 60)) + end)
