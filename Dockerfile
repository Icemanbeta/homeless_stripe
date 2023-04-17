FROM maven:3.5.4-jdk-10-slim

WORKDIR /var/app/homeless

COPY . /var/app/homeless

RUN mvn package

WORKDIR /var/app/homeless

RUN cp ./target/homeless-stripe.jar ./homeless-stripe.jar

EXPOSE 8080

CMD [ "java", "-jar", "homeless-stripe.jar" ]