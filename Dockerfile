FROM coding-public-docker.pkg.coding.net/public/docker/openjdk:8

COPY ./cro-api/target/app.jar /root/workspace/app.jar

CMD ["java", "-jar", "./app.jar"]