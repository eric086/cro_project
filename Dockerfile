FROM coding-public-docker.pkg.coding.net/public/docker/openjdk:8

COPY ./cro-api/target/cro-api-1.0-SNAPSHOT.jar /root/workspace/app.jar

CMD ["java", "-jar", "./app.jar"]