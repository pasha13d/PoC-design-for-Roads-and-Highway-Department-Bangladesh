#!/usr/bin/env python3

import sys
import collections
import myjprops
import json

application_properties_file = sys.argv[1]
datasource_url= sys.argv[2]
datasource_username = sys.argv[3]
datasource_password= sys.argv[4]

with open(application_properties_file) as fp:
    properties = myjprops.load_properties(fp, collections.OrderedDict)

properties['spring.datasource.url'] = datasource_url
properties['spring.datasource.username'] = datasource_username
properties['spring.datasource.password'] = datasource_password


with open(application_properties_file, 'w') as fp:
    myjprops.store_properties(fp, properties, timestamp = True)
