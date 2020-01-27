#!/usr/bin/env bash

#  Insert variables
. ./property.sh


SERVICES_PATH=${REPO_HOME}/${REPO_NAME}
SRC_PATH=src\/main\/${CODING_LANGUAGE}
PACKAGE_DOMAIN=bd/grp

# echo "SERVICES_PATH : "$SERVICES_PATH
# echo "SRC_PATH : "$SRC_PATH
# echo "PACKAGE_DOMAIN : "$PACKAGE_DOMAIN

# F means FROM
# T means TO

F_BUILD_GRADLE_GROUP=$(echo ${PACKAGE_DOMAIN} | sed "s/\//\./").${FROM_PRODUCT_SHORT_NAME}
F_PROJECT_PATH=${SERVICES_PATH}/${CODING_LANGUAGE}
F_NEW_FEATURE_PATH=${FROM_COMPONENT}-${FROM_FEATURE}-${FROM_VERSION}

# echo "F_BUILD_GRADLE_GROUP : "$F_BUILD_GRADLE_GROUP
# echo "F_PROJECT_PATH : "$F_PROJECT_PATH
# echo "F_NEW_FEATURE_PATH : "$F_NEW_FEATURE_PATH

T_BUILD_GRADLE_GROUP=$(echo ${PACKAGE_DOMAIN} | sed "s/\//\./").${TO_PRODUCT_SHORT_NAME}
T_PROJECT_PATH=${SERVICES_PATH}/${CODING_LANGUAGE}
T_NEW_FEATURE_PATH=${TO_COMPONENT}-${TO_FEATURE}-${TO_VERSION}

# echo "T_BUILD_GRADLE_GROUP : "$T_BUILD_GRADLE_GROUP
# echo "T_PROJECT_PATH : "$T_PROJECT_PATH
# echo "T_NEW_FEATURE_PATH : "$T_NEW_FEATURE_PATH

f_component_lc=$(echo ${FROM_COMPONENT} | sed 's/-//g' | tr [A-Z] [a-z])
f_feature_lc=$(echo ${FROM_FEATURE} | sed 's/-//g' | tr [A-Z] [a-z])

# echo "f_component_lc : "$f_component_lc
# echo "f_feature_lc : "$f_feature_lc

t_component_lc=$(echo ${TO_COMPONENT} | sed 's/-//g' | tr [A-Z] [a-z])
t_feature_lc=$(echo ${TO_FEATURE} | sed 's/-//g' | tr [A-Z] [a-z])

# echo "t_component_lc : "$t_component_lc
# echo "t_feature_lc : "$t_feature_lc

F_CREATION_PATH=${F_PROJECT_PATH}/${FROM_COMPONENT}/${F_NEW_FEATURE_PATH}/${SRC_PATH}/${FROM_SERVICE}/${PACKAGE_DOMAIN}/${FROM_PRODUCT_SHORT_NAME}/${f_component_lc}/${f_feature_lc}
F_SERVICE_PACKAGE_NAME=${F_BUILD_GRADLE_GROUP}.${f_component_lc}.${f_feature_lc}

echo "F_CREATION_PATH : "$F_CREATION_PATH
echo "F_SERVICE_PACKAGE_NAME : "$F_SERVICE_PACKAGE_NAME

T_CREATION_PATH=${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/${SRC_PATH}/${TO_SERVICE}/${PACKAGE_DOMAIN}/${TO_PRODUCT_SHORT_NAME}/${t_component_lc}/${t_feature_lc}
T_SERVICE_PACKAGE_NAME=${T_BUILD_GRADLE_GROUP}.${t_component_lc}.${t_feature_lc}

echo "T_CREATION_PATH : "$T_CREATION_PATH
echo "T_SERVICE_PACKAGE_NAME : "$T_SERVICE_PACKAGE_NAME

#  change service name to CamelCase
function change_to_camel_case(){
    NAME=""
    for element in `echo $1 | sed 's/-/ /g'`
    do
        NAME="$NAME""$(tr '[:lower:]' '[:upper:]' <<< ${element:0:1})${element:1}"
    done
    echo $NAME
}

# All in camel case. Exapmle : CamelCase
F_SERVICE_CC=$(change_to_camel_case ${FROM_SERVICE})
F_COMPONENT_CC=$(change_to_camel_case ${FROM_COMPONENT})
F_FEATURE_CC=$(change_to_camel_case ${FROM_FEATURE})

# echo "F_SERVICE_CC : "$F_SERVICE_CC
# echo "F_COMPONENT_CC : "$F_COMPONENT_CC
# echo "F_FEATURE_CC : "$F_FEATURE_CC

T_SERVICE_CC=$(change_to_camel_case ${TO_SERVICE})
T_COMPONENT_CC=$(change_to_camel_case ${TO_COMPONENT})
T_FEATURE_CC=$(change_to_camel_case ${TO_FEATURE})

# echo "T_SERVICE_CC : "$T_SERVICE_CC
# echo "T_COMPONENT_CC : "$T_COMPONENT_CC
# echo "T_FEATURE_CC : "$T_FEATURE_CC


# First latter lower case and nexts are camel case. Exapmle : camelCase
f_serviceCc="$(tr '[:upper:]' '[:lower:]' <<< ${F_SERVICE_CC:0:1})${F_SERVICE_CC:1}"
f_versionCc="$(tr '[:lower:]' '[:upper:]' <<< ${FROM_VERSION:0:1})${FROM_VERSION:1}"
f_featureCc="$(tr '[:upper:]' '[:lower:]' <<< ${F_FEATURE_CC:0:1})${F_FEATURE_CC:1}"
f_componentCc="$(tr '[:upper:]' '[:lower:]' <<< ${F_COMPONENT_CC:0:1})${F_COMPONENT_CC:1}"

# echo "f_serviceCc : "$f_serviceCc
# echo "f_versionCc : "$f_versionCc
# echo "f_featureCc : "$f_featureCc
# echo "f_componentCc : "$f_componentCc

t_serviceCc="$(tr '[:upper:]' '[:lower:]' <<< ${T_SERVICE_CC:0:1})${T_SERVICE_CC:1}"
t_versionCc="$(tr '[:lower:]' '[:upper:]' <<< ${TO_VERSION:0:1})${TO_VERSION:1}"
t_featureCc="$(tr '[:upper:]' '[:lower:]' <<< ${T_FEATURE_CC:0:1})${T_FEATURE_CC:1}"
t_componentCc="$(tr '[:upper:]' '[:lower:]' <<< ${T_COMPONENT_CC:0:1})${T_COMPONENT_CC:1}"

# echo "t_serviceCc : "$t_serviceCc
# echo "t_versionCc : "$t_versionCc
# echo "t_featureCc : "$t_featureCc
# echo "t_componentCc : "$t_componentCc

# All in Upper case separeted with '_'. Exapmle : CAMEL_CASE
f_component_uc=$(echo ${FROM_COMPONENT} | tr [a-z-] [A-Z_])
f_feature_uc=$(echo ${FROM_FEATURE} | tr [a-z-] [A-Z_])
f_version_uc=$(echo ${FROM_VERSION} | tr [a-z-] [A-Z_])
f_service_uc=$(echo ${FROM_SERVICE} | tr [a-z-] [A-Z_])

# echo "f_component_uc : "$f_component_uc
# echo "f_feature_uc : "$f_feature_uc
# echo "f_version_uc : "$f_version_uc
# echo "f_service_uc : "$f_service_uc

t_component_uc=$(echo ${TO_COMPONENT} | tr [a-z-] [A-Z_])
t_feature_uc=$(echo ${TO_FEATURE} | tr [a-z-] [A-Z_])
t_version_uc=$(echo ${TO_VERSION} | tr [a-z-] [A-Z_])
t_service_uc=$(echo ${TO_SERVICE} | tr [a-z-] [A-Z_])

# echo "t_component_uc : "$t_component_uc
# echo "t_feature_uc : "$t_feature_uc
# echo "t_version_uc : "$t_version_uc
# echo "t_service_uc : "$t_service_uc

F_SERVICE_PATH_VARIABLE_NAME1=""
F_SERVICE_PATH_VARIABLE_NAME2=""
T_SERVICE_PATH_VARIABLE_NAME=""


function create_service_directory(){
    mkdir -p ${T_CREATION_PATH}
    if [ "$?" != "0" ]; then
        echo "Could not create directory ${T_CREATION_PATH}. Exiting ..."
        exit -1
  	fi
  	echo "Directory created : ${T_CREATION_PATH}"
}

function copy_to_new_directory(){
    cp -rf ${F_CREATION_PATH}/* ${T_CREATION_PATH}
    if [ "$?" != "0" ]; then
        echo "Could not copy files from ${F_CREATION_PATH} to ${T_CREATION_PATH}. Exiting ..."
        exit -1
  	fi
  	echo "File copied."
}

# Update in request directory
function update_request_body(){
    if [ -r ${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java ]
    then
	    mv ${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java ${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java.bak
        cat ${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java.bak |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}RequestBody/${T_SERVICE_CC}RequestBody/" > ${T_CREATION_PATH}/request/${T_SERVICE_CC}RequestBody.java

        rm -f "${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java.bak"
        echo ${F_SERVICE_CC}RequestBody.java is renamed to ${T_SERVICE_CC}RequestBody.java
    else
        echo "File not found : ${T_CREATION_PATH}/request/${F_SERVICE_CC}RequestBody.java"
    fi
}

function update_request(){
    if [ -r ${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java ]
    then
	      mv ${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java ${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java.bak
        cat ${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java.bak |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" > ${T_CREATION_PATH}/request/${T_SERVICE_CC}Request.java

        rm -f "${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java.bak"
        echo ${F_SERVICE_CC}Request.java is renamed to ${T_SERVICE_CC}Request.java
    else
        echo "File not found : ${T_CREATION_PATH}/request/${F_SERVICE_CC}Request.java.bak"
    fi
}


# Update in response directory
function update_response_body(){
    if [ -r ${T_CREATION_PATH}/response/${F_SERVICE_CC}ResponseBody.java ]
    then
	    mv ${T_CREATION_PATH}/response/${F_SERVICE_CC}ResponseBody.java ${T_CREATION_PATH}/response/${F_SERVICE_CC}ResponseBody.java.bak
        cat ${T_CREATION_PATH}/response/${F_SERVICE_CC}ResponseBody.java.bak |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}ResponseBody/${T_SERVICE_CC}ResponseBody/" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/" > ${T_CREATION_PATH}/response/${T_SERVICE_CC}ResponseBody.java

        rm -f "${T_CREATION_PATH}/response/${F_SERVICE_CC}ResponseBody.java.bak"
        echo ${F_SERVICE_CC}ResponseBody.java is renamed to ${T_SERVICE_CC}ResponseBody.java
    else
        echo "File not found : ${F_SERVICE_CC}ResponseBody.java"
    fi
}

function update_response(){
    if [ -r ${T_CREATION_PATH}/response/${F_SERVICE_CC}Response.java ]
    then
	      mv ${T_CREATION_PATH}/response/${F_SERVICE_CC}Response.java ${T_CREATION_PATH}/response/${F_SERVICE_CC}Response.java.bak
        cat ${T_CREATION_PATH}/response/${F_SERVICE_CC}Response.java.bak |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" > ${T_CREATION_PATH}/response/${T_SERVICE_CC}Response.java

        rm -f "${T_CREATION_PATH}/response/${F_SERVICE_CC}Response.java.bak"
        echo ${F_SERVICE_CC}Response.java is renamed to ${T_SERVICE_CC}Response.java
    else
        echo "File not found : ${F_SERVICE_CC}Response.java"
    fi
}

function update_response_main(){
    if [ -r ${T_CREATION_PATH}/response/${F_SERVICE_CC}.java ]
    then
	      mv ${T_CREATION_PATH}/response/${F_SERVICE_CC}.java ${T_CREATION_PATH}/response/${F_SERVICE_CC}.java.bak
        cat ${T_CREATION_PATH}/response/${F_SERVICE_CC}.java.bak |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/g" > ${T_CREATION_PATH}/response/${T_SERVICE_CC}.java

        rm -f "${T_CREATION_PATH}/response/${F_SERVICE_CC}.java.bak"
        echo ${F_SERVICE_CC}.java is renamed to ${T_SERVICE_CC}.java
    else
        echo "File not found : ${F_SERVICE_CC}.java"
    fi
}


# Update in service directory
function update_service(){
    if [ -r ${T_CREATION_PATH}/service/${F_SERVICE_CC}Service.java ]
    then
	    mv ${T_CREATION_PATH}/service/${F_SERVICE_CC}Service.java ${T_CREATION_PATH}/service/${F_SERVICE_CC}Service.java.bak
        cat ${T_CREATION_PATH}/service/${F_SERVICE_CC}Service.java.bak |
        sed "s/${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}/${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/ ${F_SERVICE_CC}Service/ ${T_SERVICE_CC}Service/" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" |
        sed "s/${F_SERVICE_CC}Util/${T_SERVICE_CC}Util/g" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/g" |
        sed "s/${f_serviceCc}/${t_serviceCc}/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" > ${T_CREATION_PATH}/service/${T_SERVICE_CC}Service.java

        rm -f "${T_CREATION_PATH}/service/${F_SERVICE_CC}Service.java.bak"
        echo ${F_SERVICE_CC}Service.java is renamed to ${T_SERVICE_CC}Service.java
    else
        echo "File not found : ${F_SERVICE_CC}Service.java"
    fi
}

resource_path_var=
service_path_var=
function add_service_path_in_api_java(){
    API_JAVA_PATH=${T_PROJECT_PATH}/${TO_PRODUCT}/service-base/${SRC_PATH}/${PACKAGE_DOMAIN}/util/constant
    echo " API_JAVA_PATH: "$API_JAVA_PATH
    if [ -r ${API_JAVA_PATH} ]
    then
        resource_path_var="${t_component_uc}_${t_feature_uc}_${t_version_uc}_RESOURCE"
        resource_path_value="${TO_COMPONENT}\/${TO_FEATURE}\/${TO_VERSION}\/"

        if ! grep "${resource_path_var}" ${API_JAVA_PATH}/Api.java
        then
            cat ${API_JAVA_PATH}/Api.java |
            sed "s/Api {/Api \{\n\n\tpublic static final String ${resource_path_var} = \"${resource_path_value}\"\; \/\/AUTO-INSERT\n/" > ${API_JAVA_PATH}/temp.Api.java
            mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
            echo "public static final String ${resource_path_var} = \"${resource_path_value}\" is added in Api.java"
        else
            echo "Api.java is already updated! with ${resource_path_var} resource path"
        fi

        service_path_var="${t_component_uc}_${t_feature_uc}_${t_version_uc}_${t_service_uc}_PATH"

        if ! grep "${service_path_var}" ${API_JAVA_PATH}/Api.java
        then
            cat ${API_JAVA_PATH}/Api.java |
            sed "s/Api {/Api \{\n\n\tpublic static final String ${service_path_var} = \"${TO_SERVICE}\"\; \/\/AUTO-INSERT/" > ${API_JAVA_PATH}/temp.Api.java
            mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
            echo "public static final String ${service_path_var} = \"${TO_SERVICE}\" is added in Api.java"
        else
            echo "Api.java is already updated with ${service_path_var} service path!"
        fi
    else
        echo 'Api.java source file does not exist!'
        exit -1
    fi
}

# Update in resource directory
function update_resource(){
    SERVICE_PATH_VARIABLE_NAME1="${f_component_uc}\_${f_feature_uc}\_${f_service_uc}_${f_version_uc}_PATH"
    SERVICE_PATH_VARIABLE_NAME2="${f_component_uc}\_${f_feature_uc}\_${f_version_uc}_${f_service_uc}_PATH"
    f_resource_path_var="${f_component_uc}_${f_feature_uc}_${f_version_uc}_RESOURCE"

    if [ -r ${T_CREATION_PATH}/resource/${F_SERVICE_CC}Resource.java ]
    then
	      mv ${T_CREATION_PATH}/resource/${F_SERVICE_CC}Resource.java ${T_CREATION_PATH}/resource/${F_SERVICE_CC}Resource.java.bak
        cat ${T_CREATION_PATH}/resource/${F_SERVICE_CC}Resource.java.bak |
        sed "s/\"${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}Service\"/\"${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}Service\"/" |
        sed "s/\"${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}Resource\"/\"${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}Resource\"/" |
		    sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" |
        sed "s/${F_SERVICE_CC}Service/${T_SERVICE_CC}Service/g" |
        sed "s/class ${F_SERVICE_CC}Resource/class ${T_SERVICE_CC}Resource/" |
        sed "s/Api.${f_resource_path_var}/Api.${resource_path_var}/g" |
        sed "s/Api.${SERVICE_PATH_VARIABLE_NAME1}/Api.${service_path_var}/g" |
        sed "s/Api.${SERVICE_PATH_VARIABLE_NAME2}/Api.${service_path_var}/g" > "${T_CREATION_PATH}/resource/${T_SERVICE_CC}Resource.java"

        rm -f "${T_CREATION_PATH}/resource/${F_SERVICE_CC}Resource.java.bak"
        echo ${F_SERVICE_CC}Resource.java is renamed to ${T_SERVICE_CC}Resource.java
    else
        echo "File not found : ${F_SERVICE_CC}Resource.java"
    fi
}

# Update in dao directory
function update_dao(){
    if [ -r ${T_CREATION_PATH}/dao/${F_SERVICE_CC}Dao.java ]
    then
	      mv ${T_CREATION_PATH}/dao/${F_SERVICE_CC}Dao.java ${T_CREATION_PATH}/dao/${F_SERVICE_CC}Dao.java.bak
        cat ${T_CREATION_PATH}/dao/${F_SERVICE_CC}Dao.java.bak |
        sed "s/\"${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}Service\"/\"${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}Service\"/" |
        sed "s/\"${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}Resource\"/\"${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}Resource\"/" |
		    sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" |
        sed "s/${F_SERVICE_CC}Service/${T_SERVICE_CC}Service/g" |
        sed "s/class ${F_SERVICE_CC}Resource/class ${T_SERVICE_CC}Resource/" |
        sed "s/Api.${f_resource_path_var}/Api.${resource_path_var}/g" > "${T_CREATION_PATH}/dao/${T_SERVICE_CC}Dao.java"

        rm -f "${T_CREATION_PATH}/dao/${F_SERVICE_CC}Dao.java.bak"
        echo ${F_SERVICE_CC}Dao.java is renamed to ${T_SERVICE_CC}Dao.java
    else
        echo "File not found : ${F_SERVICE_CC}Dao.java"
    fi
}

# Update in util directory
function update_query(){
    if [ -r ${T_CREATION_PATH}/util/${F_SERVICE_CC}Query.java ]
    then
	    mv ${T_CREATION_PATH}/util/${F_SERVICE_CC}Query.java ${T_CREATION_PATH}/util/${F_SERVICE_CC}Query.java.bak
        cat ${T_CREATION_PATH}/util/${F_SERVICE_CC}Query.java.bak |
        sed "s/${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}/${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" |
        sed "s/${F_SERVICE_CC}Query/${T_SERVICE_CC}Query/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" > ${T_CREATION_PATH}/util/${T_SERVICE_CC}Query.java
        rm -f "${T_CREATION_PATH}/util/${F_SERVICE_CC}Query.java.bak"
        echo ${F_SERVICE_CC}Query.java is renamed to ${T_SERVICE_CC}Query.java
    fi
}

function update_row_mapper(){
    if [ -r ${T_CREATION_PATH}/util/${F_SERVICE_CC}RowMapper.java ]
    then
	      mv ${T_CREATION_PATH}/util/${F_SERVICE_CC}RowMapper.java ${T_CREATION_PATH}/util/${F_SERVICE_CC}RowMapper.java.bak
        cat ${T_CREATION_PATH}/util/${F_SERVICE_CC}RowMapper.java.bak |
        sed "s/${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}/${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_PACKAGE_NAME}/${T_SERVICE_PACKAGE_NAME}/g" |
        sed "s/${F_SERVICE_CC}/${T_SERVICE_CC}/g" |
        sed "s/${F_SERVICE_CC}Request/${T_SERVICE_CC}Request/g" |
        sed "s/${F_SERVICE_CC}Query/${T_SERVICE_CC}Query/g" |
        sed "s/${F_SERVICE_CC}Response/${T_SERVICE_CC}Response/g" > ${T_CREATION_PATH}/util/${T_SERVICE_CC}RowMapper.java
        rm -f "${T_CREATION_PATH}/util/${F_SERVICE_CC}RowMapper.java.bak"
        echo ${F_SERVICE_CC}RowMapper.java is renamed to ${T_SERVICE_CC}RowMapper.java
    fi
}

function update_packages(){
    f_qualifier="${f_componentCc}${F_FEATURE_CC}${f_version_uc}${F_SERVICE_CC}"
    t_qualifier="${t_componentCc}${T_FEATURE_CC}${t_version_uc}${T_SERVICE_CC}"
    find ${T_CREATION_PATH} -type f -exec sed -i "s/package ${F_SERVICE_PACKAGE_NAME}/package ${T_SERVICE_PACKAGE_NAME}/g" {} +
    find ${T_CREATION_PATH} -type f -exec sed -i "s/import ${F_SERVICE_PACKAGE_NAME}/import ${T_SERVICE_PACKAGE_NAME}/g" {} +
    find ${T_CREATION_PATH} -type f -exec sed -i "s/@Qualifier(\"${f_qualifier}/@Qualifier(\"${t_qualifier}/g" {} +
    find ${T_CREATION_PATH} -type f -exec sed -i "s/@Component(\"${f_qualifier}/@Component(\"${t_qualifier}/g" {} +
}


function configure_gradle_file(){
    # configure gradle file in product
    if ! grep "applyFrom('"${TO_COMPONENT}"')" ${T_PROJECT_PATH}/settings.gradle
    then
        printf "\napplyFrom('${TO_COMPONENT}')" >> ${T_PROJECT_PATH}/settings.gradle
        echo "applyFrom('${TO_COMPONENT}') is added in ${T_PROJECT_PATH}/settings.gradle"
    fi

    # configure gradle file in component
    if ! [ -r ${T_PROJECT_PATH}/${TO_COMPONENT}/build.gradle ]
    then
        cp ../gradle-file/component.build.gradle ${T_PROJECT_PATH}/${TO_COMPONENT}/build.gradle
    fi

    if ! [ -r ${T_PROJECT_PATH}/${TO_COMPONENT}/settings.gradle ]
    then
        touch ${T_PROJECT_PATH}/${TO_COMPONENT}/settings.gradle
    fi

    if ! grep "include '${T_NEW_FEATURE_PATH}'" ${T_PROJECT_PATH}/${TO_COMPONENT}/settings.gradle
    then
        printf "\ninclude '${T_NEW_FEATURE_PATH}'" >> ${T_PROJECT_PATH}/${TO_COMPONENT}/settings.gradle
        echo "include '${T_NEW_FEATURE_PATH}' is added in ${T_PROJECT_PATH}/${TO_COMPONENT}/settings.gradle"
    fi

    # configure gradle file in feature
    if ! [ -r ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle ]
    then
        cat ../gradle-file/feature.build.gradle |
        sed "s/<FEATURE_SOURCE_DIRECTORY>/src\/main\/java\/${TO_SERVICE}/" > ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle
        echo "${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle is created."
    else
        if ! grep "'src\/main\/java\/${TO_SERVICE}'" ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle
        then
            cat ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle |
            sed "s/'src\/main\/java\//'src\/main\/java\/${TO_SERVICE}', 'src\/main\/java\//" > ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/temp.build.gradle
            mv ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/temp.build.gradle ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle
            echo "'src\/main\/java\/${TO_SERVICE}' is added in ${T_PROJECT_PATH}/${TO_COMPONENT}/${T_NEW_FEATURE_PATH}/build.gradle"
        else
            echo "nothing to do since 'src\/main\/java\/${TO_SERVICE}' already exists in gradle file!"
        fi
    fi

    # configure gradle file in feature
    container_gradle_file=${T_PROJECT_PATH}/${TO_PRODUCT}/${TO_PRODUCT}-container/build.gradle
    if [ -r ${container_gradle_file} ]
    then
        if ! grep "compile project(':${T_NEW_FEATURE_PATH}')" ${container_gradle_file}
        then
            cat ${container_gradle_file} |
            sed "s/compile project(':service-base')/compile project(':service-base')\n\tcompile project(':${T_NEW_FEATURE_PATH}')/" > ${container_gradle_file}.temp
            mv ${container_gradle_file}.temp ${container_gradle_file}
            echo "compile project(':${T_NEW_FEATURE_PATH}') is added in ${container_gradle_file}"
        else
            echo "nothing to do since compile project(':${T_NEW_FEATURE_PATH}') already exists in gradle file!"
        fi
    else
        echo "Error : ${container_gradle_file} file does not exists !!!!"
    fi
    echo
    echo
}

function gradle_clean(){
    pushd ${T_PROJECT_PATH}
    gradle clean cleanEclipse eclipse
    popd
}

function run(){
    create_service_directory
    copy_to_new_directory
    update_request_body
    update_request
    update_response_body
    update_response
    update_response_main
    update_service
    add_service_path_in_api_java
    update_resource
    update_query
    update_row_mapper
    update_dao
    update_packages
    configure_gradle_file
}

run

SERVICES_PATH=${REPO_HOME}/${REPO_NAME}
SRC_PATH=src\/main\/${CODING_LANGUAGE}
PACKAGE_DOMAIN=bd/grp

PRODUCT=$FROM_PRODUCT
PRODUCT_SHORT_NAME=$FROM_PRODUCT_SHORT_NAME
COMPONENT=$FROM_COMPONENT
FEATURE=$FROM_FEATURE
VERSION=$FROM_VERSION
SERVICE=$FROM_SERVICE

BUILD_GRADLE_GROUP=$(echo $PACKAGE_DOMAIN | sed "s/\//\./").$PRODUCT_SHORT_NAME
PROJECT_PATH=$SERVICES_PATH/$CODING_LANGUAGE
DELETE_FEATURE_PATH=$COMPONENT-$FEATURE-$VERSION

component_lc=$(echo $COMPONENT | sed 's/-//g' | tr [A-Z] [a-z])
feature_lc=$(echo $FEATURE | sed 's/-//g' | tr [A-Z] [a-z])

DELETION_PATH=$SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT/$DELETE_FEATURE_PATH/$SRC_PATH/$SERVICE
SERVICE_PACKAGE_NAME=${BUILD_GRADLE_GROUP}.${component_lc}.${feature_lc}

COMPONENT_UC=$(echo $COMPONENT | tr [a-z-] [A-Z_])
FEATURE_UC=$(echo $FEATURE | tr [a-z-] [A-Z_])
VERSION_UC=$(echo $VERSION | tr [a-z-] [A-Z_])

# echo "SERVICES_PATH : "$SERVICES_PATH
# echo "SRC_PATH : "$SRC_PATH
# echo "PACKAGE_DOMAIN : "$PACKAGE_DOMAIN
#
# echo "BUILD_GRADLE_GROUP : "$BUILD_GRADLE_GROUP
# echo "PROJECT_PATH : "$PROJECT_PATH
# echo "DELETE_FEATURE_PATH : "$DELETE_FEATURE_PATH
#
# echo "component_lc : "$component_lc
# echo "feature_lc : "$feature_lc
#
# echo "DELETION_PATH : "$DELETION_PATH
# echo "SERVICE_PACKAGE_NAME : "$SERVICE_PACKAGE_NAME


# Deleting feature directory
prompt () {
  echo
  echo
  echo $1
  read -p "Are you sure? " -n 1 -r
  echo    # (optional) move to a new line
  if [[ ! $REPLY =~ ^[Yy]$ ]]
  then
      [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1 # handle exits from shell or function but don't exit interactive shell
  fi
}

# prompt "The program will begin"

if [ -r $DELETION_PATH ]
then
    # prompt "Everything will be deleted from ${DELETION_PATH}"
    rm -r ${DELETION_PATH}
    echo "Service $SERVICE is deleted from ${DELETION_PATH}"
else
    echo "ERROR : ${DELETION_PATH} does not exist!"
    exit -1
fi

SERVICE_CNT=$(ls -d $SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT/$DELETE_FEATURE_PATH/$SRC_PATH/*/ | wc -l)
# echo "SERVICE_CNT : "$SERVICE_CNT

API_JAVA_PATH=$PROJECT_PATH/$PRODUCT/service-base/$SRC_PATH/$PACKAGE_DOMAIN/util/constant

if [ -r $API_JAVA_PATH ]
then
    API_SERVICE_PATH=$(echo $SERVICE | tr [a-z-] [A-Z_])
    # echo $API_SERVICE_PATH

    api_java_service_var=$(grep -c "${COMPONENT_UC}\_${FEATURE_UC}_${VERSION_UC}\_${API_SERVICE_PATH}_PATH" $API_JAVA_PATH/Api.java)
    # api_java_service_val=$(grep -c $SERVICE $API_JAVA_PATH/Api.java)

    if [ $api_java_service_var = 0 ]
    then
        echo "Api service variable does not exist in Api.java"
    else
        # prompt "${COMPONENT_UC}\_${FEATURE_UC}_${VERSION_UC}\_${API_SERVICE_PATH}_PATH will be deleted from $API_JAVA_PATH/Api.java"
        line_no=$(grep -n "${COMPONENT_UC}\_${FEATURE_UC}_${VERSION_UC}\_${API_SERVICE_PATH}_PATH" $API_JAVA_PATH/Api.java | cut -d : -f 1)
        sed -e "${line_no}d" $API_JAVA_PATH/Api.java > ${API_JAVA_PATH}/temp.Api.java
        mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
        echo "${COMPONENT_UC}\_${FEATURE_UC}_${VERSION_UC}\_${API_SERVICE_PATH}_PATH deleted in Api.java"
    fi
else
    echo 'Api.java source file does not exist!'
    exit -1
fi


# configure gradle file in feature
FEATURE_GRADLE_DIRECTORY=$PROJECT_PATH/$COMPONENT/$DELETE_FEATURE_PATH
# echo "FEATURE_GRADLE_DIRECTORY : "$FEATURE_GRADLE_DIRECTORY

if [ -r $FEATURE_GRADLE_DIRECTORY/build.gradle ]
then
    # prompt "src\/main\/java\/${SERVICE} will be deleted from $FEATURE_GRADLE_DIRECTORY/build.gradle"
    cat $FEATURE_GRADLE_DIRECTORY/build.gradle |
    sed -e "s/\s*'src\/main\/java\/${SERVICE}'\s*,//" |
    sed -e "s/,\s*'src\/main\/java\/${SERVICE}'\s*//" |
    sed -e "s/\s*'src\/main\/java\/${SERVICE}'\s*//" > $FEATURE_GRADLE_DIRECTORY/tmp.build.gradle
    mv $FEATURE_GRADLE_DIRECTORY/tmp.build.gradle $FEATURE_GRADLE_DIRECTORY/build.gradle
    echo "src\/main\/java\/${SERVICE} deleted from feature gradle file"
else
    echo "Feature Build Gradle not found"
fi

SERVICE_PATH=$SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT/$DELETE_FEATURE_PATH
# echo "SERVICE_PATH : "$SERVICE_PATH

if [ "$SERVICE_CNT" -eq "0" ]
then
    if [ -r $SERVICE_PATH ]
    then
        # prompt "Everything will be removed from $SERVICE_PATH"
        echo "Removing contents of : "$SERVICE_PATH
        rm -r ${SERVICE_PATH}
    else
        echo "$SERVICE_PATH not found"
    fi
fi

# api_java_resource_value
if [ "$SERVICE_CNT" -eq "0" ]
then
    if [ -r $PROJECT_PATH/$COMPONENT/settings.gradle ]
    then
      # prompt "include '${DELETE_FEATURE_PATH} will be deleted from $PROJECT_PATH/$COMPONENT/settings.gradle"
      line_no=$(grep -n "include '${DELETE_FEATURE_PATH}'" $PROJECT_PATH/$COMPONENT/settings.gradle | cut -d : -f 1)
      sed -e "${line_no}d" $PROJECT_PATH/$COMPONENT/settings.gradle > $PROJECT_PATH/$COMPONENT/tmp.settings.gradle
      mv $PROJECT_PATH/$COMPONENT/tmp.settings.gradle $PROJECT_PATH/$COMPONENT/settings.gradle
      echo "Deleted include '${DELETE_FEATURE_PATH}' from $PROJECT_PATH/$COMPONENT/settings.gradle"
    else
        echo "$PROJECT_PATH/$COMPONENT/settings.gradle not found"
    fi
fi

if [ "$SERVICE_CNT" -eq "0" ]
then
    api_java_resource_var=$(grep -c "$COMPONENT_UC\_$FEATURE_UC\_$VERSION_UC\_RESOURCE" $API_JAVA_PATH/Api.java)
    api_java_resource_value=$(grep -c "$COMPONENT\/$FEATURE\/$VERSION" $API_JAVA_PATH/Api.java)

    if [ $api_java_resource_var = 0 ] && [ $api_java_resource_value = 0 ]
    then
        echo "Variable does not exist"
    else
        # prompt "${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE variable will be deleted from $API_JAVA_PATH/Api.java"
        line_no=$(grep -n "public static final String ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE = \"$COMPONENT\/$FEATURE\/$VERSION\/\"" $API_JAVA_PATH/Api.java | cut -d : -f 1)
        sed -e "${line_no}d" $API_JAVA_PATH/Api.java > ${API_JAVA_PATH}/temp.Api.java
        mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
        echo "${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE deleted in Api.java"
    fi
fi

container_gradle_file=$PROJECT_PATH/$PRODUCT/${PRODUCT}-container
if [ "$SERVICE_CNT" -eq "0" ]
then
    if [ -r ${container_gradle_file}/build.gradle ]
    then
        # prompt "compile project(':${DELETE_FEATURE_PATH}') line will be deleted from $container_gradle_file/build.gradle"
        line_no=$(grep -n "compile project\s*(':${DELETE_FEATURE_PATH}')" $container_gradle_file/build.gradle | cut -d : -f 1)
        echo $line_no
        sed -e "${line_no}d" $container_gradle_file/build.gradle  > $container_gradle_file/tmp.build.gradle
        mv $container_gradle_file/tmp.build.gradle $container_gradle_file/build.gradle
        echo "Deleted compile project(':${DELETE_FEATURE_PATH}') from $container_gradle_file/tmp.build.gradle $container_gradle_file/build.gradle"
    else
        echo "Error : ${container_gradle_file} file does not exists !!!!"
        exit -1
    fi
fi
#
COMPONENT_CNT=$(ls -d $SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT/*/ | wc -l)
# echo "COMPONENT_CNT : "$COMPONENT_CNT
#
COMPONENT_PATH=$SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT
# echo "COMPONENT_PATH"$COMPONENT_PATH

if [ "$COMPONENT_CNT" -eq "0" ]
then
    # prompt "Everything will be deleted from $COMPONENT_PATH"
    echo "Removing contents of : "$COMPONENT_PATH
    rm -r ${COMPONENT_PATH}
fi

# echo "PROJECT_PATH : "$PROJECT_PATH

if [ "$COMPONENT_CNT" -eq "0" ]
then
    # prompt "applyFrom('$COMPONENT') will be deleted from $PROJECT_PATH/settings.gradle"
    line_no=$(grep -n "applyFrom\s*('$COMPONENT')" $PROJECT_PATH/settings.gradle | cut -d : -f 1)
    echo $line_no
    sed -e "${line_no}d" $PROJECT_PATH/settings.gradle > $PROJECT_PATH/tmp.settings.gradle
    mv $PROJECT_PATH/tmp.settings.gradle $PROJECT_PATH/settings.gradle
    echo "Deleted applyFrom('$COMPONENT') from $PROJECT_PATH/tmp.settings.gradle $PROJECT_PATH/settings.gradle"
fi

gradle_clean

echo "Done"
