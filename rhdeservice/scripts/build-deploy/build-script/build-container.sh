#!/usr/bin/env bash

startTime=${SECONDS}

NAME=${1}
ENVIRONMENT_TYPE=${2}
RESTART_ENABLED=${3}
APPLICATION_NAME=${4}
INSTANCE_HOSTNAME=${5}
SERVER_PORT=${6}
VERSION=${7}
SERVICE_URL_DEFAULT_ZONE=${8}
EUREKA_INSTANCE=${9}
CURRENT_LOG_FILE_NAME=${10}
ARCHIVED_LOG_FILE_NAME_PATTERN=${11}
SERVICES=${12}
CONTAINER_TYPE=${13}
DATASOURCE_URL=${14}
DATASOURCE_USERNAME=${15}
DATASOURCE_PASSWORD=${16}
EUREKA_INSTANCE_APPNAME=${17}
PROJECT_ROOT=${18}
SERVER_LOCATION=${19}
EMAIL_LOG=${20}
LOG_FILE=${21}
CONTAINER_STAGED_LOCATION=${22}


if [ "${CONTAINER_TYPE}" == "basic" ]
then
    CONF_DIR="${PROJECT_ROOT}"/${SERVICES}
    PROPERTIES_DIR="${CONF_DIR}"/src/main/resources
    APPLICATION_PROPERTIES_FILE="${PROPERTIES_DIR}"/application.properties
    BOOTSTRAP_PROPERTIES_FILE="${PROPERTIES_DIR}"/bootstrap.properties
    LOGBACK_FILE=${PROPERTIES_DIR}/logback.xml
    CONTAINER="${CONF_DIR}"

else
    CONF_DIR="${PROJECT_ROOT}"/${SERVICES}-service/java
    PROPERTIES_DIR="${CONF_DIR}"/${NAME}/${NAME}-container/src/main/resources
    APPLICATION_PROPERTIES_FILE="${PROPERTIES_DIR}"/application.properties
    BOOTSTRAP_PROPERTIES_FILE="${PROPERTIES_DIR}"/bootstrap.properties
    LOGBACK_FILE=${PROPERTIES_DIR}/logback.xml
    CONTAINER="${CONF_DIR}"/${NAME}/${NAME}-container
fi

function update_configuration (){

        cp -rf ${CONTAINER}/build.gradle ${CONTAINER}/build.gradle.bak
        cp -rf ${APPLICATION_PROPERTIES_FILE} ${PROPERTIES_DIR}/application.properties.bak
        cp -rf ${LOGBACK_FILE} ${PROPERTIES_DIR}/logback.xml.bak

        sed -i "/version/c\version = \'${VERSION}\'" ${CONTAINER}/build.gradle
        sed -i "/baseName/c\baseName = \'${APPLICATION_NAME}\'" ${CONTAINER}/build.gradle

        result="gradle.build"

        if [ "${NAME}" == "eureka" ]
        then
            python3 ./configuration/update-eureka-application-properties.py "${APPLICATION_PROPERTIES_FILE}" "${RESTART_ENABLED}" "${VERSION}" "${SERVER_PORT}" "${CURRENT_LOG_FILE_NAME}" "${ENVIRONMENT_TYPE}" "${INSTANCE_HOSTNAME}" "${SERVICE_URL_DEFAULT_ZONE}" "${NAME}"
            result="${result}, application.properties"
        else
          if [ "${NAME}" == "config" ]
          then
            cp -rf ${PROPERTIES_DIR}/global-config/application.properties ${PROPERTIES_DIR}/global-config/application.properties.bak
            python3 ./configuration/update-global-config-properties.py "${PROPERTIES_DIR}/global-config/application.properties" "${DATASOURCE_URL}" "${DATASOURCE_USERNAME}" "${DATASOURCE_PASSWORD}"
          fi
            python3 ./configuration/update-application-properties.py "${APPLICATION_PROPERTIES_FILE}" "${RESTART_ENABLED}" "${VERSION}" "${SERVER_PORT}" "${CURRENT_LOG_FILE_NAME}" "${ENVIRONMENT_TYPE}" "${INSTANCE_HOSTNAME}" "${EUREKA_INSTANCE_APPNAME}"
            cp -rf ${BOOTSTRAP_PROPERTIES_FILE} ${PROPERTIES_DIR}/bootstrap.properties.bak
            python3 ./configuration/update-bootstrap-properties.py "${BOOTSTRAP_PROPERTIES_FILE}" "${EUREKA_INSTANCE_APPNAME}" "${EUREKA_INSTANCE}" "${SERVICE_URL_DEFAULT_ZONE}"
            result="${result}, application.properties, bootstrap.properties"
        fi

        python3 ./configuration/update-logback.py "${LOGBACK_FILE}" "${SERVER_LOCATION}" "${APPLICATION_NAME}" "${CURRENT_LOG_FILE_NAME}" "${ARCHIVED_LOG_FILE_NAME_PATTERN}" "${EMAIL_LOG}"
        result="${result}, logback.xml"

        printf "2. %-15s : configuration files(${result})\n" "Update" | tee -a "${LOG_FILE}"

}

# Replace properties files
function replace (){

    if [ "${NAME}" == "config" ]
    then
        mv ${PROPERTIES_DIR}/global-config/application.properties.bak ${PROPERTIES_DIR}/global-config/application.properties
    fi
    mv ${CONTAINER}/build.gradle.bak ${CONTAINER}/build.gradle

    mv ${PROPERTIES_DIR}/application.properties.bak ${APPLICATION_PROPERTIES_FILE}

    mv ${PROPERTIES_DIR}/bootstrap.properties.bak ${BOOTSTRAP_PROPERTIES_FILE} </dev/null >/dev/null 2>&1 &

    mv ${PROPERTIES_DIR}/logback.xml.bak ${LOGBACK_FILE}
}

# Stage each container to their location
function staging_container (){

    echo "Begin staging : ${1}" >> "${LOG_FILE}"

    CONTAINER_SOURCE_LOCATION="${CONTAINER}"/build/libs
    pushd ${CONTAINER_SOURCE_LOCATION} >> /dev/null

    zip -r ${APPLICATION_NAME}-${VERSION}.zip ${APPLICATION_NAME}-${VERSION}.jar >> "${LOG_FILE}"

    cp -r ${APPLICATION_NAME}-${VERSION}.zip "${CONTAINER_STAGED_LOCATION}"

    printf "4. %-15s : ${1}\n" "Staged" | tee -a "${LOG_FILE}"

    popd >> /dev/null
}

function build_container(){

    echo "Changing directory to: ${CONF_DIR}/" >> "${LOG_FILE}"
    pushd "${CONF_DIR}"/ >> /dev/null

    gradle clean build >> "${LOG_FILE}" 2>&1

    if [ "$?" != "0" ]
    then
        echo "" | tee -a "${LOG_FILE}"
        # echo "Build failed: ${1}" | tee -a "${LOG_FILE}"
        printf "\033[91mBuilding status of ${NAME} : FAILED\033[0m\n" | tee -a "${LOG_FILE}"
        clean_container 4 ${NAME}
        exit -1
    else
        printf "3. %-15s : ${1}\n" "Built " | tee -a "${LOG_FILE}"
    fi
    popd >> /dev/null
}

function clean_container (){

        echo "Changing directory to : ${CONF_DIR}/" >> "${LOG_FILE}"
        pushd "${CONF_DIR}"/ >> /dev/null

        echo "Begin container clean : $2" >> "${LOG_FILE}"
        gradle clean >> "${LOG_FILE}" 2>&1

        if [ "$?" != "0" ]
        then
            echo "Clean failed : ${2}" | tee -a "${LOG_FILE}"
            exit -1
        fi

        printf "${1}. %-15s : ${2}\n" "Clean" | tee -a "${LOG_FILE}"
        popd >> /dev/null

}

function run(){

    clean_container 1 ${NAME}
    update_configuration ${NAME}
    build_container ${NAME}
    staging_container ${NAME}
    replace ${NAME}
    clean_container 5 ${NAME}

}

run

endTime=${SECONDS}
diffTime=`expr ${endTime} - ${startTime}`

printf "\033[93m\033[1m%-18s : %02d:%02d:%02d \033[0m\n" "Execution time" $(($diffTime/3600)) $(($diffTime%3600/60)) $(($diffTime%60)) | tee -a "${LOG_FILE}"
