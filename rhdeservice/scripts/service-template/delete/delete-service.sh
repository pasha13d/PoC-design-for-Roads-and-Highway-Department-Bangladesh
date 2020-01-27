#!/usr/bin/env bash

#  Insert variables
. ./property.sh

SERVICES_PATH=${REPO_HOME}/${REPO_NAME}
SRC_PATH=src\/main\/${CODING_LANGUAGE}
PACKAGE_DOMAIN=bd/grp

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

pushd $PROJECT_PATH
gradle clean cleanEclipse eclipse
popd

echo 'Done'
