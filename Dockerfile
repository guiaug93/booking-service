FROM openjdk:17-oracle

WORKDIR /app

COPY target/booking-service-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "booking-service-0.0.1-SNAPSHOT.jar"]