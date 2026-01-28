FROM alpine:latest
#Installing required utilities
ENV ZULU_KEY_SHA256=6c6393d4755818a15cf055a5216cffa599f038cd508433faed2226925956509a
RUN apk update && apk add bash curl && wget --quiet https://cdn.azul.com/public_keys/alpine-signing@azul.com-5d5dc44c.rsa.pub -P /etc/apk/keys/ && \
    echo "${ZULU_KEY_SHA256}  /etc/apk/keys/alpine-signing@azul.com-5d5dc44c.rsa.pub" | sha256sum -c - && \
    apk --repository https://repos.azul.com/zulu/alpine --no-cache add zulu21-jdk~=21.0.4 tzdata && \
    wget https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64 && \
    mv jq-linux64 /usr/bin/jq && chmod +x /usr/bin/jq

ENV JAVA_HOME=/usr/lib/jvm/zulu21
ARG ENVIRONMENT
ENV envDir=$ENVIRONMENT
ARG SERVICE_NAME
ENV ServiceName=$SERVICE_NAME
# create application folder
RUN mkdir -p /usr/share/service
# copying the jar in application folder
ADD inventory-application/target/inventory-application-*.jar  /usr/share/service/
# copying the config and properties file
ADD inventory-application/src/main/resources/config/application.properties /usr/share/service/
# create the volume to persist the logs
RUN mkdir /var/log/inventory-management
# copy GC.out
ADD setup/gc.out /var/log/inventory-management/
ADD setup/newrelic.* /usr/share/service/
#copying elastic apm jar
ADD setup/elastic-apm-agent-1.51.0.jar /usr/share/service/
# copying runner script
ADD setup/run.sh /usr/share/service/
EXPOSE 8060
WORKDIR /usr/share/service/
# Running the server
CMD bash /usr/share/service/run.sh $envDir $ServiceName
