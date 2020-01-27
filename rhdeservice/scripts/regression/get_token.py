import json
import requests
from requests.auth import HTTPBasicAuth


def get_token(username, password):

    data = {
        'grant_type': 'password',
        'username': username,
        'password': password
    }
    header = {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
    url = 'http://192.168.0.166:6031/security/oauth/token'
    response = requests.post(url, data=data, headers=header,
                                  auth=HTTPBasicAuth('grp-web-portal', '123456'), verify=True)
    try:
        print("Generating Token")
        response_data = json.loads(response.content.decode())
        return response_data['access_token']
    except:
        print("Cannot Generate Token")
