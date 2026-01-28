#!/bin/bash

ENVIRONMENT=$1
SERVICE_NAME=$2
BASE_PATH=/usr/share/service

# Declare all the environment variables in an array
declare -a env_vars=(
  "ACTIVE_MQ_USERNAME" 
  "ACTIVE_MQ_PASSWORD" 
  "ACTIVE_MQ_BROKER_URL" 
  "DB_USER_ENV" 
  "DB_PASSWORD_ENV" 
  "DB_URL_ENV" 
  "AWS_CLI_PROFILE"
  "AWS_CLI_REGION"
  "PARKING_URL" 
  "PERMIT_URL"
  "GO_AUTH_URL"
  "REDIS_HOST" 
  "APM_SERVER_URL"
  "CONFIG_URL"
  "RABBITMQ_HOST"
  "RABBITMQ_USERNAME"
  "RABBITMQ_PASSWORD"
  "LEAK_DETECTION_THRESHOLD"
  "ALLOWED_ORIGINS"
)

# Extract and export environment variables using jq
for var in "${env_vars[@]}"; do
  # Extract the variable from ENVIRONMENT_VARIABLES and export it globally
  value=$(echo "$ENVIRONMENT_VARIABLES" | jq --raw-output ".${var} // empty")
  if [[ -n "$value" ]]; then
    export "$var"="$value"
  else
    echo "Warning: $var not set in ENVIRONMENT_VARIABLES"
  fi
done

# Check if ALLOWED_ORIGINS is null or empty
ALLOWED_ORIGINS=$(echo $ENVIRONMENT_VARIABLES | jq --raw-output '.ALLOWED_ORIGINS')
if [ -z "$ALLOWED_ORIGINS" ] || [ "$ALLOWED_ORIGINS" = "null" ]; then
    ALLOWED_ORIGINS="*"
    sed -i "s#ALLOWED_ORIGINS#${ALLOWED_ORIGINS}#" "${BASE_PATH}/application.properties"
else
    sed -i "s#ALLOWED_ORIGINS#${ALLOWED_ORIGINS}#" "${BASE_PATH}/application.properties"
fi

# Update application.properties file using sed
for var in "${env_vars[@]}"; do
  value=$(eval echo \$$var)
  # Only proceed if value is not empty
  if [[ -n "$value" ]]; then
    # Escape special characters (like &, ?, /, and #)
    value=$(echo "$value" | sed 's/[&/\?]/\\&/g' | sed 's/#/\\#/g')
    sed -i "s#$var#${value}#" "${BASE_PATH}/application.properties"
  else
    echo "Warning: Value for $var is empty. Skipping replacement."
  fi
done

case $ENVIRONMENT in
    gmp-dev)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp"
    ;;
    gmp-qa)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp"
    ;;
    apcoa-staging)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp"
    ;;
    gmp-staging)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=http://apm-gmpstaging.getmyparking.tech:8200 -Delastic.apm.application_packages=com.gmp"
    ;;
    apcoa-prod)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp -XX:MaxRAMPercentage=70.0 -XX:InitialRAMPercentage=70.0 -XX:+UseContainerSupport"
    ;;
    gmp-prod)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp -XX:MaxRAMPercentage=70.0 -XX:InitialRAMPercentage=70.0 -XX:+UseContainerSupport"
    ;;
    townepark-prod)
        APM="-javaagent:elastic-apm-agent-1.51.0.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENVIRONMENT -Delastic.apm.server_urls=${APM_SERVER_URL} -Delastic.apm.application_packages=com.gmp"
    ;;
    *)
        APM=''
    ;;
esac

echo "ENVIRONMENT :"$ENVIRONMENT
echo "APM :"$APM


java $APM -Dfile.encoding=UTF-8 -Xlog:gc*:/var/log/inventory-management/gc.out -Djava.net.preferIPv4Stack=true -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -jar $BASE_PATH/inventory-application-*.jar -Dspring.config.location=file://$BASE_PATH/application.properties
