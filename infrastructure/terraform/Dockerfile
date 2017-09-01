FROM java:8-jre-alpine

RUN apk update && apk add --no-cache bash

# Add and unzip the Play app
COPY ./toggle-service-0.1.zip /opt/toggle-service-0.1.zip
RUN unzip /opt/toggle-service-0.1.zip -d /opt
COPY ./application.conf /opt/toggle-service-0.1/

# Give exec permission to the start script (unzip doesn't preserve permissions)
RUN chmod 755 /opt/toggle-service-0.1/bin/toggle-service

ARG NEWRELIC_AGENT_VERSION=3.40.0

RUN apk --no-cache add curl unzip && \
    mkdir -p /tmp /opt/newrelic/logs && \
    curl -o /tmp/newrelic-java.zip -fSL \
    https://oss.sonatype.org/content/repositories/releases/com/newrelic/agent/java/newrelic-java/$NEWRELIC_AGENT_VERSION/newrelic-java-$NEWRELIC_AGENT_VERSION.zip && \
    unzip /tmp/newrelic-java.zip newrelic/newrelic.jar -d /opt && \
    rm /tmp/newrelic-java.zip && \
    chmod a+r /opt/newrelic/newrelic.jar && \
    chmod a+rw /opt/newrelic/logs && \
    apk del curl unzip

	
COPY ./newrelic.yml /opt/newrelic/

# Play port to expose
EXPOSE 9860

ENV JAVA_OPTS -javaagent:/opt/newrelic/newrelic.jar

CMD /opt/toggle-service-0.1/bin/toggle-service -Dpidfile.path=/dev/null -Dhttp.port=9860 -Dconfig.file=/opt/toggle-service-0.1/application.conf -Dlogger.file=conf/docker-logger.xml
