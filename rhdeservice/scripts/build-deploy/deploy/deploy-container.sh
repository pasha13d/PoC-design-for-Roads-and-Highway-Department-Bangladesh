#!/usr/bin/env bash

INSTANCE_NAME=${1}
SERVICE_NAME=${2}
REMOTE_USER=${3}
REMOTE_MACHINE_IP=${4}
PORT_NUMBER=${5}
LOG_FILE=${6}
DEPLOYMENT_DIR=${7}
REMOTE_DEPLOYMENT=${8}
VERSION=${9}

printf "\033[93m\033[1m%-18s \033[0m\n" "----------------------------------------- ${SERVICE_NAME} -----------------------------------------" | tee -a "${LOG_FILE}"

if [ ${REMOTE_DEPLOYMENT} == "VM" ]
then
    ssh -p ${PORT_NUMBER} ${REMOTE_USER}@${REMOTE_MACHINE_IP} mkdir -p ${DEPLOYMENT_DIR}
    printf "1. %-30s : ${DEPLOYMENT_DIR}\n" "Directory Created" | tee -a "${LOG_FILE}"

    scp -P ${PORT_NUMBER} container/${SERVICE_NAME}.zip ${REMOTE_USER}@${REMOTE_MACHINE_IP}:${DEPLOYMENT_DIR} >> /dev/null
    printf "2. %-30s : ${SERVICE_NAME}.zip\n" "Dist Transfered" | tee -a "${LOG_FILE}"

    scp -P ${PORT_NUMBER} deploy/run-container.sh ${REMOTE_USER}@${REMOTE_MACHINE_IP}:${DEPLOYMENT_DIR} >> /dev/null
    printf "3. %-30s : run-container.sh\n" "Script Transfered" | tee -a "${LOG_FILE}"

    printf "4. %-30s : ${SERVICE_NAME} using run-container.sh\n" "Deploying" | tee -a "${LOG_FILE}"
    ssh -p ${PORT_NUMBER} ${REMOTE_USER}@${REMOTE_MACHINE_IP} ${DEPLOYMENT_DIR}/run-container.sh ${INSTANCE_NAME} ${SERVICE_NAME} ${DEPLOYMENT_DIR} ${REMOTE_DEPLOYMENT}

else
    mkdir -p ${DEPLOYMENT_DIR}
    printf "1. %-30s : ${DEPLOYMENT_DIR}\n" "Directory Created" | tee -a "${LOG_FILE}"

    cp -rf container/${SERVICE_NAME}-${VERSION}.zip ${DEPLOYMENT_DIR} >> /dev/null
    printf "2. %-30s : ${SERVICE_NAME}-${VERSION}.zip\n" "Dist Transfered" | tee -a "${LOG_FILE}"

    cp -rf deploy/run-container.sh ${DEPLOYMENT_DIR} >> /dev/null
    printf "3. %-30s : run-container.sh\n" "Script Transfered" | tee -a "${LOG_FILE}"

    printf "4. %-30s : ${SERVICE_NAME} using run-container.sh\n" "Deploying" | tee -a "${LOG_FILE}"
    cd ${DEPLOYMENT_DIR}
    ./run-container.sh ${INSTANCE_NAME} ${SERVICE_NAME} ${DEPLOYMENT_DIR} ${REMOTE_DEPLOYMENT} ${REMOTE_MACHINE_IP} ${VERSION}

fi
