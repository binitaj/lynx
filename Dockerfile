FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY ./target/lynx-0.1.jar /opt/app
CMD ["ls", "/opt/app"]
CMD ["java", "-jar", "/opt/app/lynx-0.1.jar"]