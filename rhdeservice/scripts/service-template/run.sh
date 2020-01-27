#!/usr/bin/env bash

#  Insert variables
. ./property.sh

SERVICES_PATH=${REPO_HOME}/${REPO_NAME}
echo "SERVICES_PATH : "$SERVICES_PATH
SRC_PATH=src\/main\/${CODING_LANGUAGE}
echo "SRC_PATH : "$SRC_PATH
PACKAGE_DOMAIN=com/secl/eservice
echo "PACKAGE_DOMAIN : "$PACKAGE_DOMAIN

BUILD_GRADLE_GROUP=$(echo $PACKAGE_DOMAIN | sed "s/\//\./g")
echo "BUILD_GRADLE_GROUP : "$BUILD_GRADLE_GROUP
PROJECT_PATH=$SERVICES_PATH/$CODING_LANGUAGE
echo "PROJECT_PATH : "$PROJECT_PATH
NEW_FEATURE_PATH=$COMPONENT-$FEATURE-$VERSION
echo "NEW_FEATURE_PATH : "$NEW_FEATURE_PATH

component_lc=$(echo $COMPONENT | sed 's/-//g' | tr [A-Z] [a-z])
#cho "component_lc : "$component_lc
feature_lc=$(echo $FEATURE | sed 's/-//g' | tr [A-Z] [a-z])
#echo "feature_lc : "$feature_lc

CREATION_PATH=$SERVICES_PATH/$CODING_LANGUAGE/$COMPONENT/$NEW_FEATURE_PATH/$SRC_PATH/$SERVICE/$PACKAGE_DOMAIN/$component_lc/$feature_lc
echo "CREATION_PATH : "$CREATION_PATH
SERVICE_PACKAGE_NAME=${BUILD_GRADLE_GROUP}.${component_lc}.${feature_lc}
echo "SERVICE_PACKAGE_NAME : "$SERVICE_PACKAGE_NAME

mkdir -p $CREATION_PATH/dao
mkdir -p $CREATION_PATH/request
mkdir -p $CREATION_PATH/resource
mkdir -p $CREATION_PATH/response
mkdir -p $CREATION_PATH/service
mkdir -p $CREATION_PATH/util

#  change service name to camel case
function change_to_camel_case(){
    NAME=""
    for element in `echo $1 | sed 's/-/ /g'`
    do
        NAME="$NAME""$(tr '[:lower:]' '[:upper:]' <<< ${element:0:1})${element:1}"
    done
    echo $NAME
}

function change_to_camel_case_first_lower(){
    NAME=""
    IDX=0
    for element in `echo $1 | sed 's/-/ /g'`
    do
        if [ "$IDX" -eq "0" ]
        then
            NAME="$element"
            IDX=$((IDX + 1))
        else
            NAME="$NAME""$(tr '[:lower:]' '[:upper:]' <<< ${element:0:1})${element:1}"
        fi
    done
    echo $NAME
}

SERVICE_CC=$(change_to_camel_case $SERVICE)
COMPONENT_CC=$(change_to_camel_case $COMPONENT)
FEATURE_CC=$(change_to_camel_case $FEATURE)
VERSION_CC=$(change_to_camel_case $VERSION)
SERVICE_PATH_CC=$COMPONENT$FEATURE_CC$VERSION_CC$SERVICE_CC
COMPONENT_UC=$(echo $COMPONENT | tr [a-z-] [A-Z_])
FEATURE_UC=$(echo $FEATURE | tr [a-z-] [A-Z_])
VERSION_UC=$(echo $VERSION | tr [a-z-] [A-Z_])

SERVICE_UC=$(echo $SERVICE | tr [a-z-] [A-Z_])
PRODUCT_SHORT_NAME_UC=$(echo $PRODUCT_SHORT_NAME | tr [a-z-] [A-Z_])
SERVICE_RESOURCE_PATH_UC=$COMPONENT_UC\_$FEATURE_UC\_$VERSION_UC
SERVICE_NAME_PATH_UC=$COMPONENT_UC\_$FEATURE_UC\_$VERSION_UC\_$SERVICE_UC
# echo $SERVICE_PATH_UC
# echo "SERVICE_CC : "$SERVICE_CC
# echo "COMPONENT_CC : "$COMPONENT_CC
# echo "FEATURE_CC : "$FEATURE_CC

# Creating files in dao package
#  ServiceNameDao.java


if ! [ -r ${CREATION_PATH}/dao/${SERVICE_CC}Dao.java ]
then
    cat ./new-service-template/dao/ServiceNameDao.java |
    sed -e "s/<ServiceNameDaoPackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" -e "s/<SERVICE_PATH_CC>/${SERVICE_PATH_CC}/g"> ${CREATION_PATH}/dao/${SERVICE_CC}Dao.java
    echo ${CREATION_PATH}/dao/${SERVICE_CC}Dao.java is created.
else
    echo "Nothing to do since  /dao/${SERVICE_CC}Dao.java  already exists!"
fi

# Creating files in request package
#  ServiceNameRequest.java
if ! [ -r ${CREATION_PATH}/request/${SERVICE_CC}Request.java ]
then
    cat ./new-service-template/request/ServiceNameRequest.java |
    sed -e "s/<ServiceNameRequestPackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/request/${SERVICE_CC}Request.java
    echo ${CREATION_PATH}/request/${SERVICE_CC}Request.java is created.
else
    echo "Nothing to do since  /request/${SERVICE_CC}Request.java  already exists!"
fi

#  ServiceNameRequest.java
if ! [ -r ${CREATION_PATH}/request/${SERVICE_CC}RequestBody.java ]
then
    cat ./new-service-template/request/ServiceNameRequestBody.java |
    sed -e "s/<ServiceNameRequestPackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/request/${SERVICE_CC}RequestBody.java
    echo ${CREATION_PATH}/request/${SERVICE_CC}RequestBody.java is created.
else
    echo "Nothing to do since  /request/${SERVICE_CC}RequestBody.java  already exists!"
fi

# Creating files in resource package
#  ServiceNameResource.java
if ! [ -r ${CREATION_PATH}/resource/${SERVICE_CC}Resource.java ]
then
    cat ./new-service-template/resource/ServiceNameResource.java |
    sed -e "s/<ServiceNameResourcePackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" -e "s/<SERVICE_PATH_CC>/${SERVICE_PATH_CC}/g" -e "s/<SERVICE_RESOURCE_PATH_UC>/${SERVICE_RESOURCE_PATH_UC}/g" -e "s/<SERVICE_NAME_PATH_UC>/${SERVICE_NAME_PATH_UC}/g" > ${CREATION_PATH}/resource/${SERVICE_CC}Resource.java
    echo ${CREATION_PATH}/resource/${SERVICE_CC}Resource.java is created.
else
    echo "Nothing to do since  /resource/${SERVICE_CC}Resource.java  already exists!"
fi

# Creating files in response package
#  ServiceName.java
if ! [ -r ${CREATION_PATH}/response/${SERVICE_CC}.java ]
then
    cat ./new-service-template/response/ServiceName.java |
    sed -e "s/<ServiceNameResponsePackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/response/${SERVICE_CC}.java
    echo ${CREATION_PATH}/response/${SERVICE_CC}.java is created.
else
    echo "Nothing to do since  /response/${SERVICE_CC}.java  already exists!"
fi

#  ServiceNameResponse.java
if ! [ -r ${CREATION_PATH}/response/${SERVICE_CC}Response.java ]
then
    cat ./new-service-template/response/ServiceNameResponse.java |
    sed -e "s/<ServiceNameResponsePackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/response/${SERVICE_CC}Response.java
    echo ${CREATION_PATH}/response/${SERVICE_CC}Response.java is created.
else
    echo "Nothing to do since  /response/${SERVICE_CC}Response.java  already exists!"
fi

#  ServiceNameResponseBody.java
if ! [ -r ${CREATION_PATH}/response/${SERVICE_CC}ResponseBody.java ]
then
    cat ./new-service-template/response/ServiceNameResponseBody.java |
    sed -e "s/<ServiceNameResponsePackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/response/${SERVICE_CC}ResponseBody.java
    echo ${CREATION_PATH}/response/${SERVICE_CC}ResponseBody.java is created.
else
    echo "Nothing to do since  /response/${SERVICE_CC}ResponseBody.java  already exists!"
fi


# Creating files in service package
#  ServiceNameService.java
SERVICE_CC_LOWER=$(change_to_camel_case_first_lower $SERVICE)
if ! [ -r ${CREATION_PATH}/service/${SERVICE_CC}Service.java ]
then
    cat ./new-service-template/service/ServiceNameService.java |
    sed -e "s/<ServiceNameServicePackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" -e "s/<FeatureName>/${FEATURE}/g" -e "s/<SERVICE_PATH_CC>/${SERVICE_PATH_CC}/g" -e "s/<SERVICE_CC_LOWER>/${SERVICE_CC_LOWER}/g" > ${CREATION_PATH}/service/${SERVICE_CC}Service.java
    echo ${CREATION_PATH}/service/${SERVICE_CC}Service.java is created.
else
    echo "Nothing to do since  /service/${SERVICE_CC}Service.java  already exists!"
fi

# Creating files in util package
#  ServiceNameQuery.java
if ! [ -r ${CREATION_PATH}/util/${SERVICE_CC}Query.java ]
then
    cat ./new-service-template/util/ServiceNameQuery.java |
    sed -e "s/<ServiceNameUtilPackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" -e "s/<FEATURE_UC>/${FEATURE_UC}/g" -e "s/<PRODUCT_SHORT_NAME_UC>/${PRODUCT_SHORT_NAME_UC}/g" -e "s/<FEATURE_CC>/${FEATURE_CC}/g" > ${CREATION_PATH}/util/${SERVICE_CC}Query.java
    echo ${CREATION_PATH}/util/${SERVICE_CC}Query.java is created.
else
    echo "Nothing to do since  /util/${SERVICE_CC}Query.java  already exists!"
fi

#  ServiceNameRowMapper.java
if ! [ -r ${CREATION_PATH}/util/${SERVICE_CC}RowMapper.java ]
then
    cat ./new-service-template/util/ServiceNameRowMapper.java |
    sed -e "s/<ServiceNameUtilPackage>/${SERVICE_PACKAGE_NAME}/" -e "s/<ServiceName>/${SERVICE_CC}/g" > ${CREATION_PATH}/util/${SERVICE_CC}RowMapper.java
    echo ${CREATION_PATH}/util/${SERVICE_CC}RowMapper.java is created.
else
    echo "Nothing to do since  /util/${SERVICE_CC}RowMapper.java  already exists!"
fi


# add constant of resource path and service path in api.java
API_JAVA_PATH=$PROJECT_PATH/$PRODUCT/service-base/$SRC_PATH/$PACKAGE_DOMAIN/util/constant

echo "API_JAVA_PATH : "$API_JAVA_PATH

if [ -r $API_JAVA_PATH ]
then
    #Api.java updated with RESOURCE path
    #check if this particular RESOURCE path already exist

    api_java_resource_var=$(grep -c "$COMPONENT_UC\_$FEATURE_UC\_$VERSION_UC\_RESOURCE" $API_JAVA_PATH/Api.java)
    api_java_resource_value=$(grep -c "$COMPONENT\/$FEATURE\/$VERSION" $API_JAVA_PATH/Api.java)

    if [ $api_java_resource_var = 0 ] && [ $api_java_resource_value = 0 ]
    then
        #add java line
        cat ${API_JAVA_PATH}/Api.java |
        sed "s/Api {/Api \{\n\n\tpublic static final String ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE = \"$COMPONENT\/$FEATURE\/$VERSION\/\"\; \/\/AUTO-INSERT\n/" > ${API_JAVA_PATH}/temp.Api.java
        mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
        echo "public static final String ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE = \"$COMPONENT\/$FEATURE\/$VERSION\" is added in Api.java"
    else
        echo "Api.java is already updated! with ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_RESOURCE resource path"
    fi

    #Api.java updated with SERVICE path
    #check if this particular SERVICE path already exist
    API_SERVICE_PATH=$(echo $SERVICE | tr [a-z-] [A-Z_])

    api_java_service_var=$(grep -c "${COMPONENT_UC}\_${FEATURE_UC}_${VERSION_UC}\_${API_SERVICE_PATH}_PATH" $API_JAVA_PATH/Api.java)
    # api_java_service_val=$(grep -c $SERVICE $API_JAVA_PATH/Api.java)

    if [ $api_java_service_var = 0 ] # && [ $api_java_service_val = 0 ]
    then
        #add java line
        cat $API_JAVA_PATH/Api.java |
        sed "s/Api {/Api \{\n\n\tpublic static final String ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_CC}_${API_SERVICE_PATH}_PATH = \"${SERVICE}\"\; \/\/AUTO-INSERT/" > $API_JAVA_PATH/temp.Api.java
        mv ${API_JAVA_PATH}/temp.Api.java ${API_JAVA_PATH}/Api.java
        echo "public static final String ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_${API_SERVICE_PATH}_PATH = \"\/${SERVICE}\" is added in Api.java"
    else
        echo "Api.java is already updated with ${COMPONENT_UC}_${FEATURE_UC}_${VERSION_UC}_${API_SERVICE_PATH}_PATH service path!"
    fi
else
    echo 'Api.java source file does not exist!'
    exit -1
fi


## ------------------------------------configuring gradle--------------------------------------
# configure gradle file in product
if ! grep "applyFrom('"$COMPONENT"')" $PROJECT_PATH/settings.gradle
then
    printf "\napplyFrom('${COMPONENT}')" >> $PROJECT_PATH/settings.gradle
    echo "applyFrom('${COMPONENT}') is added in $PROJECT_PATH/settings.gradle"
fi

# configure gradle file in component
if ! [ -r ${PROJECT_PATH}/${COMPONENT}/build.gradle ]
then
    cp ./gradle-file/component.build.gradle ${PROJECT_PATH}/${COMPONENT}/build.gradle
fi

if ! [ -r $PROJECT_PATH/$COMPONENT/settings.gradle ]
then
    touch $PROJECT_PATH/$COMPONENT/settings.gradle
fi

if ! grep "include '${NEW_FEATURE_PATH}'" $PROJECT_PATH/$COMPONENT/settings.gradle
then
    printf "\ninclude '${NEW_FEATURE_PATH}'" >> $PROJECT_PATH/$COMPONENT/settings.gradle
    echo "include '${NEW_FEATURE_PATH}' is added in $PROJECT_PATH/$COMPONENT/settings.gradle"
fi

# configure gradle file in feature
FEATURE_GRADLE_DIRECTORY=$PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH

if ! [ -r $FEATURE_GRADLE_DIRECTORY/build.gradle ]
then
    cat ./gradle-file/feature.build.gradle |
    sed "s/<FEATURE_SOURCE_DIRECTORY>/src\/main\/java\/${SERVICE}/" > $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle
    echo "$PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle is created."
else
    if ! grep "src\/main\/java\/${SERVICE}" $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle
    then
        cat $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle |
        sed "s/'src\/main\/java\//'src\/main\/java\/${SERVICE}', 'src\/main\/java\//" > $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/temp.build.gradle
        mv $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/temp.build.gradle $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle
        echo "'src\/main\/java\/${SERVICE}' is added in $PROJECT_PATH/$COMPONENT/$NEW_FEATURE_PATH/build.gradle"
    else
        echo "nothing to do since src\/main\/java\/${SERVICE} already exists in gradle file!"
    fi
fi

# configure gradle file in feature
container_gradle_file=$PROJECT_PATH/$PRODUCT/${PRODUCT}-container/build.gradle
if [ -r ${container_gradle_file} ]
then
    if ! grep "compile project(':${NEW_FEATURE_PATH}')" ${container_gradle_file}
    then
        cat ${container_gradle_file} |
        sed "s/compile project(':service-base')/compile project(':service-base')\n\tcompile project(':${NEW_FEATURE_PATH}')/" > ${container_gradle_file}.temp
        mv ${container_gradle_file}.temp ${container_gradle_file}
        echo "compile project(':${NEW_FEATURE_PATH}') is added in ${container_gradle_file}"
    else
        echo "nothing to do since compile project(':${NEW_FEATURE_PATH}') already exists in gradle file!"
    fi
else
    echo "Error : ${container_gradle_file} file does not exists !!!!"
    exit -1
fi

echo
echo

pushd $PROJECT_PATH
gradle clean cleanEclipse eclipse
popd

echo 'Done'
