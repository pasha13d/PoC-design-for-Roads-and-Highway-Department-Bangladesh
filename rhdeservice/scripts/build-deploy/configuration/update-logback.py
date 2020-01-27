#!/usr/bin/env python3

from xml.dom import minidom
import xml.etree.ElementTree as ET
import xml.etree.ElementTree as etree
import xml.etree.ElementTree
import sys

logback_xml = sys.argv[1]
server_location = sys.argv[2]
service_name = sys.argv[3]
file_name = str(sys.argv[4])
file_name_pattern = str(sys.argv[5])
email_log = str(sys.argv[6])

tree = ET.parse(logback_xml)
root = tree.getroot()

root[3].find('file').text = file_name
root[3].find('rollingPolicy').find('fileNamePattern').text = file_name_pattern
#root[4].find('subject').text = '['+server_location+']['+service_name+'] - %logger{36} - %m'

#root[5].clear()
#root[5].set('level', 'INFO')
#ET.SubElement(root[5], 'appender-ref', {'ref': 'console'})
#ET.SubElement(root[5], 'appender-ref', {'ref': 'file'})

#if email_log == 'Yes':
#    ET.SubElement(root[5], 'appender-ref', {'ref': 'email'})

tree.write(logback_xml)
