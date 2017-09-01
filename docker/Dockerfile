FROM sd1pgo01lx.saksdirect.com/rhel7_2:4

# Add and unzip the Play app
COPY toggle-service-0.1.zip /opt/toggle-service-0.1.zip
COPY newrelic.* /opt/newrelic/
RUN unzip /opt/toggle-service-0.1.zip -d /opt

COPY containerbuddy /opt/containerbuddy

# Give exec permission to the start script (unzip doesn't preserve permissions)
RUN chmod 755 /opt/toggle-service-0.1/bin/toggle-service
RUN chmod 775 /opt/containerbuddy/*

# Play port to expose
EXPOSE 9860

CMD nohup /opt/toggle-service-0.1/bin/toggle-service -Dhttp.port=9860 -Dpidfile.path=/dev/null -Dconfig.file=${CONFIG_FILE} -Dlogger.file=conf/docker-logger.xml
