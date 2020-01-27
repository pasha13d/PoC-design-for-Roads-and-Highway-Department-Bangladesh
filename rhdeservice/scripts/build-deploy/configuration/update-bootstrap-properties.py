#!/usr/bin/env python3

import sys
import collections
import myjprops
import json

bootstrap_properties_file = sys.argv[1]
app_name = str(sys.argv[2])
eureka_instance = sys.argv[3]
serviceUrl = sys.argv[4]

with open(bootstrap_properties_file) as fp:
    properties = myjprops.load_properties(fp, collections.OrderedDict)

properties['eureka.instance.appname'] = app_name
properties['eureka.instance.hostname'] = eureka_instance
properties['eureka.client.serviceUrl.defaultZone'] = serviceUrl

with open(bootstrap_properties_file, 'w') as fp:
    myjprops.store_properties(fp, properties, timestamp = True)
