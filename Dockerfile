FROM openjdk:11
VOLUME /tmp
COPY kalah-0.0.1-SNAPSHOT.jar kalah-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/kalah-0.0.1-SNAPSHOT.jar"]
