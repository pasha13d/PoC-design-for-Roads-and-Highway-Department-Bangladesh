import datetime
import time
import os
import subprocess
import re
import sys
from get_service_path import get_all_services
from get_banner import get_banner
from regression_report import generate_report
from get_token import get_token
import webbrowser
import pygsheets
from send_email import send_email


class Tee(object):
    def __init__(self, *files):
        self.files = files

    def write(self, obj):
        for f in self.files:
            f.write(obj)
            f.flush()  # If you want the output to be visible immediately

    def flush(self):
        for f in self.files:
            f.flush()


PROJECT_ROOT = "/home/kaushik/Documents/GRP"

# container_list = ['ast.com-acq']

start_time = int(round(time.time() * 1000))
date_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

CURRENT_DATE = datetime.datetime.now().date().strftime('%Y-%m-%d')
CURRENT_TIME = datetime.datetime.now().time().strftime('%H.%M.%S')
DATE_TIME_DIRECTORY = str(CURRENT_DATE) + "__" + str(CURRENT_TIME)
LOG_DIR = "/var/log/grp/" + DATE_TIME_DIRECTORY + "/regression"
LOG_FILE = LOG_DIR + "/regression__" + str(CURRENT_DATE) + "__" + str(CURRENT_TIME) + ".log"
SUMMARY = "\n\nContainer".ljust(15) + "Total".ljust(10) + "Success".ljust(10) + "Failed".ljust(10) + "\n================================================"
COLOR_GREEN = '92m'
COLOR_WARNING = '93m'
COLOR_FAIL = '91m'
COLOR_ENDC = '0m'
global log
out = {}
homedir = os.getcwd()
global to, cc

credential_file = './credential.json'
auth = pygsheets.authorize(service_file=credential_file)


def read_config():
    print("Reading configuration from google sheet")
    global to, cc
    container_list = []
    sheet_name = 'GRP__Regression_configuration'
    g_sheet = auth.open(sheet_name)
    title = 'configuration'
    ws = g_sheet.worksheet('title', title)
    df = ws.get_as_df(has_header=True, index_colum=None, start='B2', end='I{}'.format(ws.rows))
    for row in df.values.tolist():
        if row[0] == 'Yes':
            container_list.append(row)
    to = container_list[0][6]
    cc = container_list[0][7]
    return container_list


def prepare_logging_environment():
    global log
    if not os.path.exists(LOG_DIR):
        print("Creating directory : " + LOG_DIR)
        try:
            os.makedirs(LOG_DIR)
            print("Directory created  : " + LOG_DIR)
        except:
            print("Cannot Create directory : " + LOG_DIR + ". Exiting...")
            exit(-1)
    else:
        print("Log Dir Already Exists")

    try:
        log = open(LOG_FILE, 'a+')  # open file in append mode
        log.write("You are looking at " + LOG_FILE + '\n')
        print("Logging output in " + LOG_FILE)
        sys.stdout = Tee(sys.stdout, log)
    except:
        print("Cannot create LogFile")
        exit(-1)


def run_regression_script(container_list):
    global log
    product_list = []
    for cnt in container_list:
        total_tested = 0
        total_failed = 0
        split_val = cnt[1].split('.')
        DEPLOYMENT_URL = cnt[3]
        DEPLOYMENT_ROUTE = cnt[2]
        TOKEN = get_token(cnt[4], cnt[5])
        module = split_val[0]
        container = split_val[1]
        banner = get_banner(container)
        print(banner)
        features = get_all_services(auth, module, container)
        container_test_root = PROJECT_ROOT + '/' + module + '-service/tests/regression/' + container + '-service'
        product_dict = {'product': container}
        product_dict['path'] = container_test_root
        product_dict['path-exists'] = True if os.path.exists(container_test_root) else False
        # print(SERVICE_PATH)
        feature_list = []
        for f in features:
            feature = list(f.keys())
            services = list(f.values())
            feature_dict = {'feature': feature[0]}
            feature_dict['path'] = container_test_root + '/python/' + container + '-' + feature[0]
            feature_dict['path-exists'] = True if os.path.exists(container_test_root + '/python/' + container + '-' + feature[0]) else False
            service_list = []
            for s in services[0]:
                service_dict = {'service': s}
                script_dir = container_test_root + '/python/' + container + '-' + feature[0] + '/' + s
                service_dict['path'] = script_dir
                if os.path.exists(script_dir):
                    service_dict['path-exists'] = True
                    os.chdir(script_dir)
                    print("Found Directory : " + script_dir)
                    if os.path.exists('./run_regression.sh'):
                        service_dict['scripts-path-exists'] = True
                        cmd = './run_regression.sh'
                        result = subprocess.run([cmd, DEPLOYMENT_URL, DEPLOYMENT_ROUTE, TOKEN], stdout=subprocess.PIPE)
                        result = result.stdout.decode('utf-8')
                        print(result)

                        passed_regex = re.findall(r'TestPassed.*', result)
                        tests = []
                        id = 0
                        pass_count = 0
                        for line in passed_regex:
                            id += 1
                            m = re.compile(r'service_test_.*\.py').search(line).group()
                            tests.append([id, m, 'Passed'])
                            pass_count += 1
                        service_dict['tests-pass-count'] = pass_count

                        failed_regex = re.findall(r'TestFailed.*', result)
                        fail_count = 0
                        for line in failed_regex:
                            id += 1
                            m = re.compile(r'service_test_.*\.py').search(line).group()
                            tests.append([id, m, 'Failed'])
                            fail_count += 1
                        service_dict['tests-fail-count'] = fail_count
                        service_dict['tests-count'] = pass_count + fail_count
                        service_dict['tests'] = tests
                        if fail_count > 0:
                            service_dict['coverage-status'] = False
                        else:
                            service_dict['coverage-status'] = True

                        tested_regex = re.compile(r'\( [0-9]+ \) test executed').search(result).group()
                        tested = re.compile(r'[0-9]+').search(tested_regex).group()

                        failed_regex = re.compile(r'\( [0-9]+ \) test failed').search(result).group()
                        failed = re.compile(r'[0-9]+').search(failed_regex).group()

                        total_tested = total_tested + int(tested)
                        total_failed = total_failed + int(failed)
                    else:
                        service_dict['scripts-path-exists'] = False
                        service_dict['coverage-status'] = False
                        print(script_dir + '/run_regression.sh not found')
                else:
                    service_dict['path-exists'] = False
                    service_dict['coverage-status'] = False
                    print(script_dir + ' Not Found')
                service_list.append(service_dict)
            feature_dict['services'] = service_list
            feature_dict['services-count'] = len(service_list)
            # print(service_list)
            feature_dict['services-fail-count'] = sum(1 for x in feature_dict['services'] if x['coverage-status'] == False)
            feature_dict['services-pass-count'] = feature_dict['services-count'] - feature_dict['services-fail-count']
            feature_dict['coverage-status'] = False if feature_dict['services-fail-count'] > 0 else True
            feature_list.append(feature_dict)

        product_dict['features-count'] = len(feature_list)
        product_dict['features'] = feature_list

        product_dict['features-fail-count'] = sum(1 for x in product_dict['features'] if x['coverage-status'] == False)
        product_dict['features-pass-count'] = product_dict['features-count'] - product_dict['features-fail-count']
        product_dict['services-count'] = sum(x['services-count'] for x in product_dict['features'])
        product_dict['services-fail-count'] = sum(x['services-fail-count'] for x in product_dict['features'])
        product_dict['services-pass-count'] = product_dict['services-count'] - product_dict['services-fail-count']
        product_dict['coverage-status'] = False if product_dict['services-fail-count'] > 0 else True
        product_list.append(product_dict)

        success = total_tested - total_failed
        if total_tested == 0:
            print(SUMMARY)
            print(f"\033[{COLOR_FAIL}{container.ljust(15) + str(total_tested).ljust(10) + str(success).ljust(10) + str(total_failed).ljust(10)}\033[00m")
        elif total_failed != 0:
            print(SUMMARY)
            print(f"\033[{COLOR_FAIL}{container.ljust(15) + str(total_tested).ljust(10) + str(success).ljust(10) + str(total_failed).ljust(10)}\033[00m")
        else:
            print(SUMMARY)
            print(f"\033[{COLOR_GREEN}{container.ljust(15) + str(total_tested).ljust(10) + str(success).ljust(10) + str(total_failed).ljust(10)}\033[00m")
    return product_list


def run():
    out['generated-at'] = datetime.datetime.now().strftime('%A, %B %m, %Y %H:%M:%S')
    prepare_logging_environment()
    out['begin'] = datetime.datetime.now().strftime('%H:%M:%S')
    print("Begin Script run-regression.sh at " + datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    container_list = read_config()
    product_list = run_regression_script(container_list)
    out['products'] = product_list
    out['products-count'] = len(product_list)
    out['products-fail-count'] = sum(1 for x in out['products'] if x['coverage-status'] == False)
    out['products-pass-count'] = out['products-count'] - out['products-fail-count']
    out['services-count'] = sum(x['services-count'] for x in out['products'])
    out['services-fail-count'] = sum(x['services-fail-count'] for x in out['products'])
    out['services-pass-count'] = out['services-count'] - out['services-fail-count']
    os.chdir(homedir)
    # with open('result.json', 'w') as fp:
    #     json.dump(out, fp)


run()
end_time = int(round(time.time() * 1000))
end = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
out['end'] = datetime.datetime.now().strftime('%H:%M:%S')
print("\n\nEnd Script run-regression.sh at " + datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'))

print("\nScript took {} seconds".format((end_time - start_time) / 1000))
out['duration'] = (end_time - start_time) / 1000
generate_report(out)
print("Sending Email To: " + to + ', cc: ' + cc)
send_email(to, cc, './log/regression-report.html')
url = './log/regression-report.html'
webbrowser.open(url, new=2)
