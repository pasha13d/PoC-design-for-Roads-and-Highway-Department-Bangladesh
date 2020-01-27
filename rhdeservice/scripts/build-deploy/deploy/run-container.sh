#!/usr/bin/env bash

INSTANCE_NAME=${1}
SERVICE_NAME=${2}
DEPLOYMENT_DIR=${3}
REMOTE_DEPLOYMENT=${4}
REMOTE_MACHINE_IP=${5}
VERSION=${6}
LOG_FILE=${DEPLOYMENT_DIR}/${SERVICE_NAME}.log

printf "5. %-30s : $(date +%Y-%m-%d_%H:%M:%S)\n" "Deployment Time" | tee -a "${LOG_FILE}"

if [ ${REMOTE_DEPLOYMENT} == "Docker" ]
then
    CONTAINER_ID=$(echo `docker ps -a | grep "${SERVICE_NAME}" | awk '{print $1}'`)
    cd "${DEPLOYMENT_DIR}" >> /dev/null

    if [ ! -z "${CONTAINER_ID}" ]
    then
        docker stop ${CONTAINER_ID} >> "${LOG_FILE}"
        docker rm -f ${CONTAINER_ID} >> "${LOG_FILE}"
        printf "6. %-30s : `echo ${SERVICE_NAME}, ID - ${CONTAINER_ID}` stopped & removed\n" "Container" | tee -a "${LOG_FILE}"
    else
        printf "6. %-30s : `echo Not found`\n" "Container" | tee -a "${LOG_FILE}"
    fi

    IMAGE_ID=$(echo `docker images | grep "${SERVICE_NAME}" | tr -s ' ' | cut -d ' ' -f 3`)
    if [ ! -z "${IMAGE_ID}" ]
    then
        docker rmi ${IMAGE_ID} -f >> "${LOG_FILE}"
        printf "7. %-30s : `echo ${SERVICE_NAME}, ID - ${IMAGE_ID}` removed\n" "Image" | tee -a "${LOG_FILE}"
    else
        printf "7. %-30s : `echo Not found`\n" "Image" | tee -a "${LOG_FILE}"
    fi

    unzip -o "${DEPLOYMENT_DIR}/${SERVICE_NAME}.zip" -d ${DEPLOYMENT_DIR} >> /dev/null
    printf "8. %-30s : `echo ${SERVICE_NAME}.zip`\n" "Unzip" | tee -a "${LOG_FILE}"
    printf "9. %-30s : `echo ${SERVICE_NAME}`\n" "Running" | tee -a "${LOG_FILE}"
    cd "${DEPLOYMENT_DIR}/${SERVICE_NAME}"/

    docker build --tag=${SERVICE_NAME}:1.0 . >> "${LOG_FILE}"
    docker run --name=${SERVICE_NAME} -d -v /var/log/brownfield:/var/log/brownfield -v /opt/brownfield/data:/opt/brownfield/data --net brownfield_net --ip ${REMOTE_MACHINE_IP} ${SERVICE_NAME}:1.0 >> "${LOG_FILE}"

else
    # S_NAME="$(tr '[:lower:]' '[:upper:]' <<< ${INSTANCE_NAME:0:1})${INSTANCE_NAME:1}"

    CONTAINER_ID=$(echo `ps aux | grep -i "${SERVICE_NAME}"-"${VERSION}".jar | awk '{print $2}'`)

    if [ ! -z "${CONTAINER_ID}" ]
    then
        kill -9 `echo ${CONTAINER_ID}` </dev/null >/dev/null 2>&1 &
        printf "6. %-30s : `echo ${CONTAINER_ID}`\n" "Kill Process" | tee -a "${LOG_FILE}"
    else
        printf "6. %-30s : `echo Not running`\n" "Status" | tee -a "${LOG_FILE}"
    fi

    unzip -o "${DEPLOYMENT_DIR}/${SERVICE_NAME}-${VERSION}.zip" -d ${DEPLOYMENT_DIR} >> /dev/null
    printf "7. %-30s : `echo ${SERVICE_NAME}-${VERSION}.zip`\n" "Unzip" | tee -a "${LOG_FILE}"

    printf "8. %-30s : `echo ${SERVICE_NAME}`\n" "Running" | tee -a "${LOG_FILE}"
    cd "${DEPLOYMENT_DIR}"/

    java -jar ${SERVICE_NAME}-${VERSION}.jar </dev/null >/dev/null 2>&1 &

fi
