#!/usr/bin/env bash

#  Insert variables
. ./property.sh

SERVICES_PATH=${REPO_HOME}/${REPO_NAME}
SRC_PATH=src\/main\/${CODING_LANGUAGE}
PACKAGE_DOMAIN=bd/grp

BUILD_GRADLE_GROUP=$(echo $PACKAGE_DOMAIN | sed "s/\//\./").$PRODUCT_SHORT_NAME
PROJECT_PATH=$SERVICES_PATH/$CODING_LANGUAGE

SERVICE_BASE_PATH=$SERVICES_PATH\/$CODING_LANGUAGE\/$PRODUCT\/service-base

# echo "SERVICES_PATH : "$SERVICES_PATH
# echo "SRC_PATH : "$SRC_PATH
# echo "PACKAGE_DOMAIN : "$PACKAGE_DOMAIN
#
# echo "BUILD_GRADLE_GROUP : "$BUILD_GRADLE_GROUP
# echo "PROJECT_PATH : "$PROJECT_PATH
#
# echo "SERVICE_BASE_PATH : "$SERVICE_BASE_PATH

if [ -r $SERVICE_BASE_PATH/build.gradle ]
then
    echo "$SERVICE_BASE_PATH/build.gradle file found"
    if [ $HYSTRIX = true ]
    then
        hystrix_var=$(grep -c "//\s*compile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\")" $SERVICE_BASE_PATH/build.gradle)
        if [ $hystrix_var = 1 ]
        then
            cat $SERVICE_BASE_PATH/build.gradle |
            sed -e "s/\s*\/\/\s*compile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\"/\tcompile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\"/" > $SERVICE_BASE_PATH/tmp.build.gradle
            mv $SERVICE_BASE_PATH/tmp.build.gradle $SERVICE_BASE_PATH/build.gradle
            echo "Line uncommented in $SERVICE_BASE_PATH/build.gradle"
        else
            echo "Nothing to uncomment"
        fi
    else
        hystrix_var=$(grep -c "\s*compile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\")" $SERVICE_BASE_PATH/build.gradle)
        if [ $hystrix_var = 1 ]
        then
            cat $SERVICE_BASE_PATH/build.gradle |
            sed -e "s/\s*compile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\"/\t\/\/compile(\"org.springframework.cloud:spring-cloud-starter-netflix-hystrix:\${project.ext.hystrixVersion}\"/" > $SERVICE_BASE_PATH/tmp.build.gradle
            mv $SERVICE_BASE_PATH/tmp.build.gradle $SERVICE_BASE_PATH/build.gradle
            echo "Line commented in $SERVICE_BASE_PATH/build.gradle"
        else
            echo "Nothing to comment"
        fi
    fi
else
    echo "$SERVICE_BASE_PATH/build.gradle file not found"
fi


pushd $PROJECT_PATH
gradle clean cleanEclipse eclipse
popd


if [ $HYSTRIX = true ]
then
    grep -rl "\s*//\s*@HystrixCommand(fallbackMethod\s*=\s*\"fallbackGetAllOnPost\")" $PROJECT_PATH | while read -r line ; do
        dirname=$(dirname $line)
        filename=$(basename $line)
        # echo $dirname
        # echo $filename
        # echo "Processing $line"
        cat $line |
        sed "s/$(head -n 1 $line)/$(head -n 1 $line)\n\nimport com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;/" |
        sed -e "s/\s*\/\/\s*@HystrixCommand(fallbackMethod\s*=\s*\"fallbackGetAllOnPost\")/\t@HystrixCommand(fallbackMethod = \"fallbackGetAllOnPost\")/" > $dirname/tmp.$filename
        mv $dirname/tmp.$filename $dirname/$filename
        echo
        echo
        echo "hystrix enable in $dirname/$filename"
    done
else
    grep -rl "\s*@HystrixCommand(fallbackMethod\s*=\s*\"fallbackGetAllOnPost\")" $PROJECT_PATH | while read -r line ; do
        dirname=$(dirname $line)
        filename=$(basename $line)
        # echo $dirname
        # echo $filename
        # echo "Processing $line"
        cat $line |
        sed "s/\s*import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;//" |
        sed -e "s/\s*@HystrixCommand(fallbackMethod\s*=\s*\"fallbackGetAllOnPost\")/\t\/\/ @HystrixCommand(fallbackMethod = \"fallbackGetAllOnPost\")/" > $dirname/tmp.$filename
        mv $dirname/tmp.$filename $dirname/$filename
        echo
        echo
        echo "hystrix disabled in $dirname/$filename"
done
fi

echo 'Done'
