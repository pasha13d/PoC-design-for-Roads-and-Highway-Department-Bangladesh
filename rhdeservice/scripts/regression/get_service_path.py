#!/usr/bin/env python3
import sys
import json
import pygsheets

sheet_name = 'GRP__Component-Feature-Service'
# container = sys.argv[2]
# module = sys.argv[1]
credential_file = 'credential.json'


# def init():
#     auth = pygsheets.authorize(service_file=credential_file)
#     return auth

def get_all_services(auth, module, container):
    global sheet_name
    _SERVICE_PATH = []
    g_sheet = auth.open(sheet_name)
    ws = g_sheet.worksheet('title', module)
    df = ws.get_as_df(has_header=True, index_colum=None, start='A4', end='Q{}'.format(ws.rows))
    product_dict = {}
    grouped_df = df.groupby('component')
    for key, item in grouped_df:
        if key == container:
            grouped_df = grouped_df.get_group(key)
    grouped_df = grouped_df.groupby(['feature', 'version'])
    dict = []
    for key, item in grouped_df:
        component_dict = {}
        val = []
        for values in grouped_df.get_group(key).values.tolist():
            if values[15] == 'done':
                val.append(values[5])
        if len(val) > 0:
            component_dict[key[0] + '-' + key[1]] = val
            dict.append(component_dict)
    return dict
