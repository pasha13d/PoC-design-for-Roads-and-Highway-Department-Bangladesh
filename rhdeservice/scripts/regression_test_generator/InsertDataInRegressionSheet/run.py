#!/usr/bin/env python3

import json
import time
import sys
import pygsheets


class CopyData(object):
    _CREDENTIAL = './conf/credential.json'
    _SHEET_NAME = 'GRP__RegressionTesting'
    _CFS_SHEET_NAME = 'GRP__Component-Feature-Service'

    def __init__(self, product, endpoint):
        self._ENDPOINT = endpoint
        pcfs = endpoint.split('/')
        if len(pcfs) != 4:
            sys.exit("Invalid service : %s\nPlease enter a valid service." % endpoint)
        self._PRODUCT = product
        self._COMPONENT = pcfs[0]
        self._FEATURE = pcfs[1]
        self._VERSION = pcfs[2]
        self._SERVICE = pcfs[3]

    def auth(self):
        auth = pygsheets.authorize(service_file=self._CREDENTIAL)
        return auth

    def load_regression_sheet(self, auth):
        sheet = auth.open(self._SHEET_NAME).worksheet('title', self._PRODUCT)
        rows = sheet.get_as_df(has_header=False, index_colum=None, start='A1', end='A{}'.format(sheet.rows)).values.tolist()
        print(rows)
        append_row = len(rows)
        if append_row > 2:
            id = int(rows[append_row-1][0]) + 1
        else:
            id = 1
        print(id)

        c = 0
        rc = 0
        cells = []
        l = len(self.req) + len(self.res)
        service = self._ENDPOINT
        for v in self.req:
            c += 1
            rc += 1
            d = [str(id), self._PRODUCT, service, "request", str(rc), v['name'], v['type'], "wrongtype", "empty", "null", "-",
                 "-", "-", "-"]
            # print(d)
            cells.append(d)

        rc = 0
        for v in self.res:
            c += 1
            rc += 1
            d = [str(id), self._PRODUCT, service, "response", str(rc), v['name'], v['type'], "wrongtype", "empty", "null", "-",
                 "-", "-", "-"]
            # print(d)
            cells.append(d)

        # print(len(cells))
        sheet.add_rows(len(cells))
        for c in cells:
            sheet.insert_rows(append_row, 1, c)
            append_row += 1

    def format_body(self, obj):
        data = []
        for k, v in obj.items():
            type = None
            if isinstance(v, str):
                type = "str"
            elif isinstance(v, int):
                type = "int"
            elif isinstance(v, float):
                type = "double"
            elif isinstance(v, bool):
                type = "boolean"
            elif isinstance(v, dict):
                type = "{}"
            elif isinstance(v, list):
                type = "[]"

            data.append({"name": k, "type": type})
        data.sort(key=lambda d: d['name'])
        return data

    def get_service_request_response(self, auth):
        rows = auth.open(self._CFS_SHEET_NAME).worksheet('title', self._PRODUCT).get_all_values()
        component_col_no = rows[3].index("component")
        feature_col_no = rows[3].index("feature")
        version_col_no = rows[3].index("version")
        service_col_no = rows[3].index("service")
        data = list(filter(lambda x: self._COMPONENT == x[component_col_no] and self._FEATURE == x[feature_col_no]
                           and self._VERSION == x[version_col_no] and self._SERVICE == x[service_col_no], rows))
        if len(data) == 0:
            print("Service not defined in CFS sheet : %s" % self._ENDPOINT)
            sys.exit(-1)
        elif len(data) > 1:
            print("Multiple service found with the same url in CFS sheet : %s" % self._ENDPOINT)
            sys.exit(-1)

        data = data[0]
        try:
            request_json = json.loads(data[rows[3].index("test-request-json")])
            response_json = json.loads(data[rows[3].index("test-response-json")])
            # print("Request json : \n%s" % json.dumps(request_json, indent=4))
            # print("Response json : \n%s" % json.dumps(response_json, indent=4))

            self.req = list(self.format_body(request_json['body']))
            self.res = list(self.format_body(response_json['body']))
            req_str = json.dumps(self.req, indent=4)
            res_str = json.dumps(self.res, indent=4)
            print(req_str)
            print(res_str)

            with open("data/request.json", "w") as f:
                f.write(req_str)
            with open("data/response.json", "w") as f:
                f.write(res_str)
        except Exception as e:
            print("Error when loading request and response json : %s", str(e))
            sys.exit(-1)

    def run(self):
        start_time = int(round(time.time() * 1000))
        auth = self.auth()
        self.get_service_request_response(auth)
        self.load_regression_sheet(auth)
        print('Execution Time : %d ms' % (int(round(time.time() * 1000)) - start_time))


if __name__ == '__main__':
    if len(sys.argv) == 3:
        CopyData(sys.argv[1], sys.argv[2]).run()
    else:
        print("Usage: " + sys.argv[0] + " PRODUCT COMPONENT/FEATURE/SERVICE/VERSION")
    # CopyData('ast', 'com-acq/temp-item/v1/get-list').run()
