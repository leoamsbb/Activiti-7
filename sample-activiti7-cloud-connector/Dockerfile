FROM openjdk:alpine
RUN apk --update add fontconfig ttf-dejavu
ENV PORT 8080
EXPOSE 8080
COPY target/sample-activiti7-cloud-connector-0.0.1-SNAPSHOT.jar /opt/sample-activiti7-cloud-connector-0.0.1-SNAPSHOT.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar sample-activiti7-cloud-connector-0.0.1-SNAPSHOT.jar