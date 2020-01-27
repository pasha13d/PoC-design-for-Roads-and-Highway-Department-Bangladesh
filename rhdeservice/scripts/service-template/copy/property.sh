#!/usr/bin/env bash

#  declaring variables
REPO_HOME=/home/shajir/spectrum_repo/poc/rhdeservice/services/eservice
CODING_LANGUAGE=java


FROM_PRODUCT_SHORT_NAME=requisition  # same as component
FROM_REPO_NAME=e-service       # don't change it
FROM_COMPONENT=requisition                  # component
FROM_FEATURE=road               # feature
FROM_VERSION=v1                         # version
FROM_SERVICE=status-check                  # service

TO_PRODUCT_SHORT_NAME=requisition
TO_REPO_NAME=e-service
TO_COMPONENT=requisition
TO_FEATURE=road
TO_VERSION=v1
TO_SERVICE=assign-person
