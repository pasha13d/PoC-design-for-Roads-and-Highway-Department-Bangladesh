#!/usr/bin/env python3

import sys
import collections
import myjprops
import json

application_properties_file = sys.argv[1]
restart_enabled = str(sys.argv[2])
version = sys.argv[3]
server_port = sys.argv[4]
log_file = sys.argv[5]
environment_type = sys.argv[6]
instance_hostname = sys.argv[7]
service_url = sys.argv[8]
instance_name = sys.argv[9]

with open(application_properties_file) as fp:
    properties = myjprops.load_properties(fp, collections.OrderedDict)

properties['spring.application.name'] = instance_name
properties['info.app.name'] = instance_name
properties['eureka.instance.appname'] = instance_name
properties['spring.devtools.restart.enabled'] = restart_enabled
properties['info.app.version'] = version
properties['info.tags.environment'] = environment_type
properties['server.port'] = server_port
properties['server.address'] = instance_hostname
properties['logging.file'] = log_file
properties['eureka.environment'] = environment_type
properties['eureka.instance.hostname'] = instance_hostname
properties['eureka.client.serviceUrl.defaultZone'] = service_url

with open(application_properties_file, 'w') as fp:
    myjprops.store_properties(fp, properties, timestamp = True)
