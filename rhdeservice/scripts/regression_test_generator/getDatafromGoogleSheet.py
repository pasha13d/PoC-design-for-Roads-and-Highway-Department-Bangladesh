#!/usr/bin/env python3

from builtins import map, len
import pygsheets
import json
import time
import sys
import os
import re
import random
from functional import seq
from datetime import datetime
from custom_logger import CustomLogger


class RegressionTestScript(object):
    _CREDENTIAL = './conf/credential.json'
    _REGRESSION_SHEET_NAME = 'GRP__RegressionTesting'
    _CFS_SHEET_NAME = 'GRP__Component-Feature-Service'
    _SINGLE_REGRESSION = "\n"
    _TEST_COUNT = 0
    _FAILED_COUNT = 0

    def __init__(self, product, service, zuul_url, zuul_route):
        self._PRODUCT = product
        self._SERVICE = service
        self._BASE_PATH = os.path.expanduser('~') + "/Documents/GRP"
        now = datetime.now()
        log_dir = "/var/log/grp/regression-test-automation/%s/%s/%s" % \
                  (self._PRODUCT, self._SERVICE, '{0:%Y-%m-%d__%H.%M.%S}'.format(now))
        self.log = CustomLogger(log_dir).get_logger()
        self.log.info("Script starts executes at : {0:%Y-%m-%d %H:%M:%S}\n".format(now))
        pcfs = service.split('/')
        if len(pcfs) != 4:
            sys.exit("Invalid service : %s\nPlease enter a valid service." % service)
        self._COMPONENT = pcfs[0]
        self._FEATURE = pcfs[1]
        self._VERSION = pcfs[2]
        self._SERVICE_NAME = pcfs[3]
        self._ZUUL_URL = zuul_url
        self._ZUUL_ROUTE = zuul_route

    def set_up(self):
        self.log.info("Setup environment variables.")
        self.g = pygsheets.authorize(service_file=self._CREDENTIAL)

        self.log.debug("\n_PRODUCT = %s\n_SERVICE = %s\n_CREDENTIAL = %s\n_REGRESSION_SHEET_NAME = %s\n"
                       "_CFS_SHEET_NAME = %s\ng = %s\n" %
                       (self._PRODUCT, self._SERVICE, self._CREDENTIAL, self._REGRESSION_SHEET_NAME,
                        self._CFS_SHEET_NAME, str(self.g)))

    def load_regression_sheet(self):
        self.log.debug("Start loading data from regression sheet.")
        worksheet = self.g.open(self._REGRESSION_SHEET_NAME)
        worksheet = worksheet.worksheet('title', self._PRODUCT)
        worksheet = worksheet.get_all_values()
        self.log.debug("Worksheet data : " + str(worksheet))

        data = list(filter(lambda x: self._SERVICE.strip() == x[2].strip(), worksheet))
        if len(data) == 0:
            self.log.error("Service not found. Product : %s, Service : %s" % (self._PRODUCT, self._SERVICE))
            sys.exit("Service not found")

        self.regression_data = data
        print(data)
        self.log.debug("Regression test data : " + str(self.regression_data))
        self.log.info("Loading complete.\n")

    def create_base_directory(self):
        self.log.debug("Start creating base directory for test script.")
        product_dir = self._BASE_PATH + "/" + self._PRODUCT + "-service" + "/tests/regression"
        if not os.path.exists(product_dir):
            self.log.error("directory not found : %s", product_dir)
            sys.exit(-1)
        self.product_dir = product_dir
        service = self._SERVICE.split("/")
        self.directory = self._BASE_PATH + "/" + self._PRODUCT + "-service/tests/regression/" + service[0] + \
                         "-service/python/" + service[0] + "-" + service[1] + "-" + service[2] + "/" + service[3] + "/"
        self.log.info("Test script directory : %s" % self.directory)
        try:
            if not os.path.exists(self.directory):
                os.makedirs(self.directory)
            if not os.path.exists(self.directory + "data/"):
                os.makedirs(self.directory + "data/")
            if not os.path.exists(self.directory + "script/"):
                os.makedirs(self.directory + "script/")
        except Exception as e:
            self.log.error("Error when creating directory %s : %s", self.directory, str(e))
            sys.exit(-1)
        self.log.debug("Directory created : \n1 : %s\n2 : %sdata\n3 : %sscript\n" % (
            self.directory, self.directory, self.directory))

    def get_service_request_response(self):
        self.log.info("Start loading request and response json.")
        rows = self.g.open(self._CFS_SHEET_NAME).worksheet('title', self._PRODUCT).get_all_values()
        component_col_no = rows[3].index("component")
        feature_col_no = rows[3].index("feature")
        version_col_no = rows[3].index("version")
        service_col_no = rows[3].index("service")
        data = list(filter(lambda x: self._COMPONENT == x[component_col_no] and self._FEATURE == x[feature_col_no]
                        and self._VERSION == x[version_col_no] and self._SERVICE_NAME == x[service_col_no], rows))
        if len(data) == 0:
            print("Service not defined in CFS sheet : %s" % self._SERVICE)
            sys.exit(-1)

        data = data[0]
        try:
            request_json = json.loads(data[rows[3].index("test-request-json")])
            response_json = json.loads(data[rows[3].index("test-response-json")])
            request_json['header']['requestId'] = None
            request_json['header']['requestTime'] = None
            request_json['header']['requestType'] = None
            request_json['header']['requestSource'] = "python-regression"
            request_json['header']['requestSourceService'] = "regression-script"
            self.log.debug("Request json : \n%s", json.dumps(request_json, indent=4))
            self.log.debug("Response json : \n%s", json.dumps(response_json, indent=4))
            with open(self.directory + "data/request.json", "w") as f:
                f.write(json.dumps(request_json, sort_keys=False, indent=4))
            with open(self.directory + "data/response.json", "w") as f:
                f.write(json.dumps(response_json, sort_keys=False, indent=4))
        except Exception as e:
            self.log.error("Error when loading request and response json : %s", str(e))
            sys.exit(-1)
        self.log.info("Loading complete.\n")

    def generate_basetest(self):
        with open("./templates/basetest.txt", "r") as r:
            with open(self.directory + "script/basetest.py", "w") as w:
                w.write(r.read())
        self.log.info("File created : basetest.py ")

    def generate_run_regression(self):
        with open("./templates/run_regression.txt", "r") as r:
            with open(self.directory + "run_regression.sh", "w") as w:
                w.write(r.read().replace("<ZUUL_URL>", self._ZUUL_URL).replace("<ZUUL_ROUTE>", self._ZUUL_ROUTE)
                        .replace("<SERVICE>", self._SERVICE).replace("<PRODUCT>", self._PRODUCT).replace("<COMPONENT>", self._COMPONENT))
        self.log.info("File created : run_regression.sh ")

    def generate_run_regression_single(self):
        with open("./templates/run_regression_single.txt", "r") as r:
            with open(self.directory + "run_regression_single.sh", "w") as w:
                w.write(r.read().replace("<ZUUL_URL>", self._ZUUL_URL).replace("<ZUUL_ROUTE>", self._ZUUL_ROUTE)
                        .replace("<SERVICE>", self._SERVICE))
        self.log.info("File created : run_regression_single.sh\n")

    def create_environment(self):
        self.log.debug("Start creating environment.")
        # with open("./conf/port.json", "r") as r:
        #     ports = json.loads(r.read())
        #     self.port = ports[self._PRODUCT]
        #     if not self.port:
        #         self.log.error("Port not found for service : %s", self._SERVICE)
        #         sys.exit(-1)
        # self.log.info("Service Port : " + self.port)
        self.create_base_directory()
        self.get_service_request_response()
        self.generate_basetest()
        self.generate_run_regression()
        self.generate_run_regression_single()
        self.log.debug("Environment created.\n")

    def register_script(self, file, attribute, type):
        self._SINGLE_REGRESSION += "#./%s ${URL} ${SERVICE} ${TOKEN}\n" % file

    def create_request_null_test_script(self, attribute):
        self._TEST_COUNT += 1
        file = "service_test_request_null_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/request_null.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute))
        self.register_script(file, attribute, "null")
        self.log.info("File created : %s", file)

    def create_request_empty_test_script(self, attribute):
        self._TEST_COUNT += 1
        file = "service_test_request_empty_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/request_empty.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute))
        self.register_script(file, attribute, "empty")
        self.log.info("File created : %s", file)

    def create_request_wrongtype_test_script(self, attribute, type):
        if type == 'str':
            type = "\"\""
        elif type == 'double':
            type = "1.00"
        elif type == '[]' or type == 'array':
            type = "[]"
        elif type == '{}' or type == 'object':
            type = "{}"
        elif type == 'boolean':
            type = "True"
        elif type == 'int':
            type = "1"
        else:
            print("Type not found :", type)

        self._TEST_COUNT += 1
        file = "service_test_request_wrongtype_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/request_wrongtype.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute).replace("<type>", type))
        self.register_script(file, attribute + " data type", "wrong")
        self.log.info("File created : %s", file)

    def request_wronglength_formater(self, attribute, length):
        len_dic = {"condition": "<length_condition>", "min": "1", "max": "20"}
        if not length:
            self.log.error("Pattern not define of wronglength for request attribute : %s", attribute)
            self._FAILED_COUNT += 1
            return len_dic
        length = length.strip()

        if re.match(r'^[0-9]+$', length):
            min_max = [int(length) - 8, int(length) + 8]
            len_dic["condition"] = "len(suffix) != " + length
        elif re.match(r'^:[0-9]+$', length):
            length = length.replace(":", "")
            min_max = [1, int(length) + 16]
            len_dic["condition"] = "len(suffix) > " + length
        elif re.match(r'^[0-9]+:$', length):
            length = length.replace(":", "")
            min_max = [int(length) - 8, int(length) + 8]
            len_dic["condition"] = "len(suffix) < " + length
        elif re.match(r'^[0-9]+:[0-9]+$', length):
            v_range = length.split(":")
            min_max = [int(v_range[0]) - 8, int(v_range[1]) + 8]
            len_dic["condition"] = "len(suffix) < " + v_range[0] + " or len(suffix) > " + v_range[1]
        elif re.match(r'^[0-9,]+$', length):
            v_nums = list(map(lambda n: int(n), length.split(",")))
            min_max = [min(v_nums) - 8, max(v_nums) + 8]
            len_dic["condition"] = "len(suffix) not in " + str(v_nums)
        else:
            self.log.error("Pattern not match of wronglength for request attribute : %s", attribute)
            self._FAILED_COUNT += 1
            return len_dic

        min_max[0] = min_max[0] if min_max[0] > 1 else 1
        len_dic["min"] = str(min_max[0])
        len_dic["max"] = str(min_max[1])
        return len_dic

    def create_request_wronglength_test_script(self, attribute, length):
        len_dic = self.request_wronglength_formater(attribute, length)
        self._TEST_COUNT += 1
        file = "service_test_request_wronglength_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/request_wronglength.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute).replace("<min>", len_dic["min"])
                        .replace("<max>", len_dic["max"]).replace("<length_condition>", len_dic["condition"]))

        self.register_script(file, attribute + " length", "wrong")
        self.log.info("File created : %s", file)

    def create_request_malformed_test_script(self, attribute, pattern):
        length_value = "range(1, 30)"
        length = pattern.get("wronglength")
        if length:
            length = length.strip()
            if re.match(r'^[0-9]+$', length):
                length_value = "[%s]" % length
            elif re.match(r'^:[0-9]+$', length):
                length_value = "range(1, %d)" % (int(length.replace(":", "")) + 1)
            elif re.match(r'^[0-9]+:$', length):
                l = int(length.replace(":", ""))
                length_value = "range(%d, %d)" % (l, l + 16)
            elif re.match(r'^[0-9]+:[0-9]+$', length):
                l = length.split(":")
                length_value = "range(%s, %d)" % (l[0], int(l[1]) + 1)
            elif re.match(r'^[0-9,]+$', length):
                length_value = "[ %s ]" % length

        self._TEST_COUNT += 1
        file = "service_test_request_malformed_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/request_malformed.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                if pattern.get("malformed") is None:
                    self._FAILED_COUNT += 1
                    pattern["malformed"] = "<malformed_pattern>"
                    self.log.error("Pattern not define of malformed for request attribute : %s", attribute)
                w.write(r.read().replace("<attribute>", attribute)
                        .replace("<malformed_pattern>", pattern.get("malformed"))
                        .replace("<length_value>", length_value))
        self.register_script(file, attribute, "malformed")
        self.log.info("File created : %s", file)

    def create_request_invalid_test_script(self, attribute, pattern, data_type):
        self._TEST_COUNT += 1
        file = "service_test_request_invalid_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        if isinstance(pattern, type([])):
            with open("./templates/request_invalid_str.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    if pattern is None:
                        self._FAILED_COUNT += 1
                        pattern = "<values>"
                    w.write(r.read().replace("<attribute>", attribute).replace("<values>", str(pattern)))
        elif pattern is not None and re.match(r'^[0-9:]+$', pattern):
            value = 0
            if re.match(r'^[0-9]+$', pattern):
                value = float(pattern) - (random.random() * 10 + 1)
            elif re.match(r'^:[0-9]+$', pattern):
                value = float(pattern.replace(":", "")) + (random.random() * 10 + 1)
            elif re.match(r'^[0-9]+:$', pattern):
                value = float(pattern.replace(":", "")) - (random.random() * 10 + 1)
            elif re.match(r'^[0-9]+:[0-9]+$', pattern):
                v = pattern.split(":")
                value = float(v[0]) - (random.random() * 10 + 1)

            if data_type == 'int':
                value = str(round(value))
            elif data_type == 'double':
                value = str(round(value, 2))
            else:
                value = '"' + str(round(value)) + '"'

            with open("./templates/request_invalid_number.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    w.write(r.read().replace("<attribute>", attribute).replace("<value>", value))
        else:
            self._FAILED_COUNT += 1
            self.log.error("Pattern not define of invalid for request attribute : %s", attribute)
            with open("./templates/request_invalid.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    w.write(r.read().replace("<attribute>", attribute))
        self.register_script(file, attribute, "invalid")
        self.log.info("File created : %s", file)

    def create_request_unknown_test_script(self):
        self._TEST_COUNT += 1
        file = "service_test_request_unknown_attribute.py"
        with open("./templates/request_unknown.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read())
        self.register_script(file, "attribute", "unknown")
        self.log.info("File created : %s", file)
        self._SINGLE_REGRESSION += "\n"

    def create_response_null_test_script(self, attribute):
        self._TEST_COUNT += 1
        file = "service_test_response_null_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/response_null.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute))
        self.register_script(file, attribute, "null")
        self.log.info("File created : %s", file)

    def create_response_empty_test_script(self, attribute, is_mandatory):
        self._TEST_COUNT += 1
        file = "service_test_response_empty_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        null_conditon = ""
        if not is_mandatory:
            null_conditon = "self._ATTR not in self.response_data['body'] or "
        with open("./templates/response_empty.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute).replace("<null_checker>", null_conditon))
        self.register_script(file, attribute, "empty")
        self.log.info("File created : %s", file)

    def create_response_mismatched_test_script(self, attribute):
        self._TEST_COUNT += 1
        file = "service_test_response_mismatched_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        with open("./templates/response_mismatched.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute))
        self.register_script(file, attribute, "mismatched")
        self.log.info("File created : %s", file)

    def create_response_wrongtype_test_script(self, attribute, type, is_mandatory):
        if type == 'str':
            type = "[type(\"\")]"
        elif type == 'double':
            type = "[type(1.00), type(1)]"
        elif type == '[]' or type == 'array':
            type = "[type([])]"
        elif type == '{}' or type == 'object':
            type = "[type({})]"
        elif type == 'boolean':
            type = "[type(True)]"
        elif type == 'int':
            type = "[type(1)]"
        else:
            print("Type not found :", type)
            type = "<type>"

        self._TEST_COUNT += 1
        file = "service_test_response_wrongtype_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        null_conditon = ""
        if not is_mandatory:
            null_conditon = "self._ATTR not in self.response_data['body'] or "
        with open("./templates/response_wrongtype.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute).replace("<type>", type)
                        .replace("<null_checker>", null_conditon))
        self.register_script(file, attribute + " data type", "wrong")
        self.log.info("File created : %s", file)

    def response_wronglength_formater(self, attribute, length):
        value = "len(self.response_data['body'][self._ATTR])"
        if not length:
            self.log.error("Pattern not define of wronglength for response attribute : %s", attribute)
            return "<length_condition>"
        length = length.strip()

        if re.match(r'^[0-9]+$', length):
            return value + " == " + length
        elif re.match(r'^:[0-9]+$', length):
            length = length.replace(":", "")
            return value + " <= " + length
        elif re.match(r'^[0-9]+:$', length):
            length = length.replace(":", "")
            return value + " >= " + length
        elif re.match(r'^[0-9]+:[0-9]+$', length):
            v_range = length.split(":")
            return value + " >= " + v_range[0] + " and " + value + " <= " + v_range[1]
        elif re.match(r'^[0-9,]+$', length):
            v_nums = list(map(lambda n: int(n), length.split(",")))
            return value + " in " + str(v_nums)
        else:
            self.log.error("Pattern not match of wronglength for response attribute : %s", attribute)
            return "<length_condition>"

    def create_response_wronglength_test_script(self, attribute, length, is_mandatory):
        length_condition = self.response_wronglength_formater(attribute, length)
        self._TEST_COUNT += 1
        file = "service_test_response_wronglength_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        if not is_mandatory:
            length_condition = "self._ATTR not in self.response_data['body'] or %s" % length_condition
        with open("./templates/response_wronglength.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<attribute>", attribute)
                        .replace("<length_condition>", length_condition))
        self.register_script(file, attribute + " length", "wrong")
        self.log.info("File created : %s", file)

    def create_response_malformed_test_script(self, attribute, pattern, is_mandatory):
        self._TEST_COUNT += 1
        file = "service_test_response_malformed_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        null_conditon = ""
        with open("./templates/response_malformed.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                if pattern is None:
                    self._FAILED_COUNT += 1
                    pattern = "<malformed_pattern>"
                    self.log.error("Pattern not define of malformed for response attribute : %s", attribute)
                if not is_mandatory:
                    null_conditon = "self._ATTR not in self.response_data['body'] or "
                w.write(r.read().replace("<attribute>", attribute).replace("<malformed_pattern>", pattern)
                        .replace("<null_checker>", null_conditon))
        self.register_script(file, attribute, "malformed")
        self.log.info("File created : %s", file)

    def create_response_invalid_test_script(self, attribute, pattern, is_mandatory):
        self._TEST_COUNT += 1
        file = "service_test_response_invalid_%s.py" % re.sub('([A-Z]+)', r'_\1', attribute).lower()
        null_conditon = ""
        if not is_mandatory:
            null_conditon = "self._ATTR not in self.response_data['body'] or "
        if isinstance(pattern, type([])):
            with open("./templates/response_invalid_str.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    w.write(r.read().replace("<attribute>", attribute).replace("<values>", str(pattern))
                            .replace("<null_condition>", null_conditon))
        elif pattern is not None and re.match(r'^[0-9:]+$', pattern):
            value = 0
            condition = "float(self.response_data[\"body\"][\"%s\"])" % attribute
            error_message = ""
            if re.match(r'^[0-9]+$', pattern):
                condition = condition + " != " + pattern
                error_message = pattern
            elif re.match(r'^:[0-9]+$', pattern):
                value = pattern.replace(":", "")
                condition = condition + " > " + value
                error_message = "greater than " + value
            elif re.match(r'^[0-9]+:$', pattern):
                value = pattern.replace(":", "")
                condition = condition + " < " + value
                error_message = "less than " + value
            elif re.match(r'^[0-9]+:[0-9]+$', pattern):
                value = pattern.split(":")
                condition = condition + " < " + value[0] + " or " + condition + " > " + value[1]
                error_message = "less than " + value[0] + " or greater than " + value[1]

            with open("./templates/response_invalid_number.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    w.write(r.read().replace("<attribute>", attribute)
                            .replace("<condition>", condition)
                            .replace("<error_message>", error_message)
                            .replace("<null_condition>", null_conditon))
        else:
            self.log.error("Pattern not define of invalid for response attribute : %s", attribute)
            self._FAILED_COUNT += 1
            with open("./templates/response_invalid.txt", "r") as r:
                with open(self.directory + "script/" + file, "w") as w:
                    w.write(r.read().replace("<attribute>", attribute))

        self.register_script(file, attribute, "invalid")
        self.log.info("File created : %s", file)

    def create_response_unknown_test_script(self, mandatory_attrs, optional_attrs):
        self._TEST_COUNT += 1
        file = "service_test_response_unknown_attribute.py"
        with open("./templates/response_unknown.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<mandatory_attribute_list>", mandatory_attrs)
                        .replace("<optional_attribute_list>", optional_attrs))
        self.register_script(file, "attribute", "unknown")
        self.log.info("File created : %s", file)

    def create_response_missing_test_script(self, mandatory_attrs):
        self._TEST_COUNT += 1
        file = "service_test_response_missing_attribute.py"
        with open("./templates/response_missing.txt", "r") as r:
            with open(self.directory + "script/" + file, "w") as w:
                w.write(r.read().replace("<mandatory_attribute_list>", mandatory_attrs))
        self.register_script(file, "attribute", "missing")
        self.log.info("File created : %s", file)

    def generate_request_test_script(self, data):
        self.log.debug("Attribute name : %s", data[5])
        attr_dic = {"attribute": data[5], "mandatory": False}
        pattern = {}
        if len(data) >= 17 and re.match(r"^\{.*\}$", data[16]):
            pattern = json.loads(data[16])
            self.log.debug("Pattern : \n%s", json.dumps(pattern))

        if "null" in data[9]:
            attr_dic['mandatory'] = True
            self.create_request_null_test_script(data[5])
        if "empty" in data[8]:
            self.create_request_empty_test_script(data[5])
        if "wrongtype" in data[7]:
            self.create_request_wrongtype_test_script(data[5], data[6])
        if "wronglength" in data[10]:
            self.create_request_wronglength_test_script(data[5], pattern.get('wronglength'))
        if "malformed" in data[11]:
            self.create_request_malformed_test_script(data[5], pattern)
        if "invalid" in data[13]:
            self.create_request_invalid_test_script(data[5], pattern.get('invalid'), data[6])

        self._SINGLE_REGRESSION += "\n"
        return attr_dic

    def generate_response_test_script(self, data):
        self.log.debug("Attribute name : %s", data[5])
        attr_dic = {"attribute": data[5], "mandatory": False}
        pattern = {}
        if len(data) >= 17 and re.match(r"^\{.*\}$", data[16]):
            pattern = json.loads(data[16])

        if "null" in data[9]:
            attr_dic['mandatory'] = True
            self.create_response_null_test_script(data[5])
        if "empty" in data[8]:
            self.create_response_empty_test_script(data[5], attr_dic['mandatory'])
        if "wrongtype" in data[7]:
            self.create_response_wrongtype_test_script(data[5], data[6], attr_dic['mandatory'])
        if "wronglength" in data[10]:
            self.create_response_wronglength_test_script(data[5], pattern.get('wronglength'), attr_dic['mandatory'])
        if "malformed" in data[11]:
            self.create_response_malformed_test_script(data[5], pattern.get('malformed'), attr_dic['mandatory'])
        if "mismatch" in data[12]:
            self.create_response_mismatched_test_script(data[5])
        if "invalid" in data[13]:
            self.create_response_invalid_test_script(data[5], pattern.get('invalid'), attr_dic['mandatory'])

        self._SINGLE_REGRESSION += "\n"
        return attr_dic

    def create_test_script(self):
        self.log.info("Start creating request test script")
        (seq(self.regression_data).filter(lambda x: "request" in x[3])
         .map(lambda d: self.generate_request_test_script(d)).to_list())
        self.create_request_unknown_test_script()

        self.log.info("\nStart creating response test script")

        response_attr_list = seq(self.regression_data).filter(lambda x: "response" in x[3]) \
            .map(lambda d: self.generate_response_test_script(d)).to_list()

        mandatory_res_attrs = str(seq(response_attr_list).filter(lambda d: d['mandatory'])
                                  .map(lambda d: d['attribute']).to_list())
        optional_res_attrs = str(seq(response_attr_list).filter(lambda d: not d['mandatory'])
                                 .map(lambda d: d['attribute']).to_list())

        self.create_response_missing_test_script(mandatory_res_attrs)
        self.create_response_unknown_test_script(mandatory_res_attrs, optional_res_attrs)

        with open(self.directory + "run_regression_single.sh", "a") as a:
            a.write(self._SINGLE_REGRESSION)

        self.log.debug("Update run_regression_single.sh file")

        self.log.info("Successfully created : (%d/%d)", self._TEST_COUNT - self._FAILED_COUNT, self._TEST_COUNT)
        self.log.info("Only Template create : (%d/%d)", self._FAILED_COUNT, self._TEST_COUNT)

    def change_script_permission(self):
        self.log.debug("Change file mode to 775")
        os.system('chmod 775 ' + self.directory + 'run_regression*')
        os.system('chmod 775 ' + self.directory + 'script/*')

    def run(self):
        start_time = int(round(time.time() * 1000))
        self.set_up()
        self.load_regression_sheet()
        self.create_environment()
        self.create_test_script()
        self.change_script_permission()
        self.log.info("")
        self.log.info("Script execution end at : {0:%Y-%m-%d %H:%M:%S}".format(datetime.now()))
        self.log.info('Execution Time : %d ms', int(round(time.time() * 1000)) - start_time)


if __name__ == '__main__':
    if len(sys.argv) == 5:
        RegressionTestScript(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4]).run()
    else:
        print("Usage: " + sys.argv[0] + " PRODUCT SERVICE ZUUL_URL ZUUL_ROUTE")
