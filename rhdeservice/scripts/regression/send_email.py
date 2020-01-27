from __future__ import print_function

import mimetypes
import pickle
import os.path
from email.mime.audio import MIMEAudio
from email.mime.base import MIMEBase
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import base64
import httplib2
import os
import base64

import mimetypes
from email import encoders
from email.mime.audio import MIMEAudio
from email.mime.base import MIMEBase
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.application import MIMEApplication

from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request

SCOPES = ['https://www.googleapis.com/auth/gmail.send']


def create_Message_with_attachment(sender, to, cc, subject, message_text_plain, attached_file):

    message = MIMEMultipart()
    message['to'] = to
    message['CC'] = cc
    message['from'] = sender
    message['subject'] = subject

    message.attach(MIMEText(message_text_plain, 'plain'))

    my_mimetype, encoding = mimetypes.guess_type(attached_file)

    if my_mimetype is None or encoding is not None:
        my_mimetype = 'application/octet-stream'

    main_type, sub_type = my_mimetype.split('/', 1)
    if main_type == 'text':
        # print("text")
        temp = open(attached_file, 'r')  # 'rb' will send this error: 'bytes' object has no attribute 'encode'
        attachement = MIMEText(temp.read(), _subtype=sub_type)
        temp.close()

    elif main_type == 'image':
        # print("image")
        temp = open(attached_file, 'rb')
        attachement = MIMEImage(temp.read(), _subtype=sub_type)
        temp.close()

    elif main_type == 'audio':
        # print("audio")
        temp = open(attached_file, 'rb')
        attachement = MIMEAudio(temp.read(), _subtype=sub_type)
        temp.close()

    elif main_type == 'application' and sub_type == 'pdf':
        temp = open(attached_file, 'rb')
        attachement = MIMEApplication(temp.read(), _subtype=sub_type)
        temp.close()

    else:
        attachement = MIMEBase(main_type, sub_type)
        temp = open(attached_file, 'rb')
        attachement.set_payload(temp.read())
        temp.close()

    encoders.encode_base64(attachement)
    filename = os.path.basename(attached_file)
    attachement.add_header('Content-Disposition', 'attachment', filename=filename) # name preview in email
    message.attach(attachement)

    message_as_bytes = message.as_bytes() # the message should converted from string to bytes.
    message_as_base64 = base64.urlsafe_b64encode(message_as_bytes) #encode in base64 (printable letters coding)
    raw = message_as_base64.decode()  # need to JSON serializable (no idea what does it means)
    return {'raw': raw}


def send_message(service, user_id, message):
    message = (service.users().messages().send(userId=user_id, body=message)
               .execute())
    # print('Message Id: %s' % message['id'])
    return message


def main():
    creds = None
    if os.path.exists('token.pickle'):
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token)
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'credentials.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)

    service = build('gmail', 'v1', credentials=creds)
    return service


def send_email(to, cc, path):
    service = main()
    sender = 'grp.regression@gmail.com'
    to = to
    cc = cc
    subject = 'Regression Test Report'
    message = 'Please check the attached document'
    attachment_path = path
    # message = create_message('grp.regression@gmail.com', to, cc, 'Test', 'hello world')
    message = create_Message_with_attachment(sender, to, cc, subject, message, attachment_path)
    send_message(service, "me", message)


# send_email('ehteshamkaushik@gmail.com', 'ehtesham@cokreates.com', './log/regression-report.html')
