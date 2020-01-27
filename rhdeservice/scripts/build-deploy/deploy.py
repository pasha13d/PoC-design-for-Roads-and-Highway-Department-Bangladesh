#!/usr/bin/env python3

import json
import logging
import os
import shlex
import subprocess
import time
from datetime import datetime
import requests


class Deploy(object):
    start_time = int(round(time.time()))

    def __init__(self):
        self.setup_logger()
        self.read_config()

    def setup_logger(self):
        logging_format = '%(asctime)s %(filename)s:%(lineno)d %(levelname)s - %(message)s'
        logging.basicConfig(level=logging.INFO, format=logging_format)
        self.logger = logging.getLogger()
        if os.path.exists(os.getcwd() + '/log') is False:
            os.mkdir(os.getcwd() + '/log')
        self.log_file_path = os.getcwd() + '/log/deployment_{}.log'.format(datetime.now().strftime('%Y%m%d_%H%M%S'))
        print('Logging output in - {}'.format(self.log_file_path))
        handler = logging.FileHandler(self.log_file_path)
        formatter = logging.Formatter(logging_format)
        handler.setFormatter(formatter)
        self.logger.addHandler(handler)

    def read_config(self):
        with open('configuration.json', 'r') as data_file:
            self.parameters = json.loads(data_file.read())
        if self.parameters['build_container'] == 'Yes':
            with open('container.json', 'r') as data_file:
                self.container_configuration = json.loads(data_file.read())
            self.container_configuration.sort(key=lambda x: int(x['#']), reverse=False)

    def deploy_container(self):

        self.remote_deployment_dir = self.parameters['deployment_path'] + "/deploy_" + datetime.now().strftime('%Y%m%d_%H%M%S')
        for instance in self.container_configuration:

            if instance['type'] != 'service' or instance['enable'] != 'Yes':
                continue

            bash_command = './deploy/deploy-container.sh {name} {application_name} {os_user} {instance_hostname} {ssh_port} ' \
               '{log_file_path} {remote_deployment_dir} {deployment_server} {version}' \
               .format(log_file_path=self.log_file_path, remote_deployment_dir=self.remote_deployment_dir,
               deployment_server=self.parameters['deployment_server'], **instance)
            subprocess.call(shlex.split(bash_command))

            is_running_container = False
            for i in range(20):
                try:
                    time.sleep(i)
                    url = 'http://' + instance['instance_hostname'] + ':' + instance['server_port']
                    if instance['name'] != 'zipkin':
                        url += '/actuator'
                    url += '/info'
                    self.response = requests.get(url)
                    if "200" in str(self.response):
                        is_running_container = True
                        self.logger.info("Connected : {}".format(instance['application_name']))
                        break

                except:
                    self.logger.warning("Trying to connect : {} - {} times".format(instance['application_name'], i))

            if is_running_container is False:
                self.logger.warning("No need to start other container due to run correct for : {}".format(instance['application_name']))
                break




if __name__ == '__main__':

    deploy = Deploy()

    if deploy.parameters['build_container'] == 'Yes':
        deploy.deploy_container()

    diffTime = int(round(time.time())) - deploy.start_time

    print('\033[93m\033[1mTotal execution time: {:02d}:{:02d}:{:02d}'.format(round(diffTime / 3600), round(diffTime % 3600 / 60), round(diffTime % 60)))
