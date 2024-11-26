# Data Streaming Application with Kafka, Apache Beam, and Spring Boot

This project is a data streaming application that consumes messages from a Kafka topic, computes the age from a Date of Birth field,
and publishes the message to one of two Kafka topics based on whether the age is odd or even.

## Objective

The application uses Apache Beam with Spring Boot and Kafka to consume messages from a single Kafka topic (`SOURCE_TOPIC`). It calculates 
the age based on the Date of Birth, and publishes the message to either `EVEN_AGE_TOPIC` or `ODD_AGE_TOPIC` based on whether the calculated age is even or odd.

## Technology Stack

- Java 21
- Spring Boot
- Apache Kafka
- Apache Beam

## Application Requirements

1. **Kafka Server**: Ensure that Kafka is running locally.
2. **Spring Boot API**: Provides two endpoints:
   - `http://localhost:8080/trigger` - Triggers the initial Kafka message on `SOURCE_TOPIC`.
   - `http://localhost:8080/consume` - Consumes and processes messages from `SOURCE_TOPIC`.

## Processing Logic

1. **Age Calculation**: The application calculates the age based on the Date of Birth provided in the Kafka message.
2. **Topic Segmentation**:
   - If the calculated age is an **even number**, the message is sent to `EVEN_AGE_TOPIC`.
   - If the calculated age is an **odd number**, the message is sent to `ODD_AGE_TOPIC`.

## Running the Application

### Prerequisites

1. **Start Kafka Server**: Ensure that Kafka is installed and running on `localhost:9092`.
   
### How to Run

1. **Run Kafka Bootstrap Server**:
   ```bash
   bin/kafka-server-start.sh config/server.properties

## To verify messages in EVEN_AGE_TOPIC, use the following command:
          kafka-console-consumer --bootstrap-server localhost:9092 --topic EVEN_AGE_TOPIC --from-beginning
## To verify messages in ODD_AGE_TOPIC, use the following command:
           kafka-console-consumer --bootstrap-server localhost:9092 --topic ODD_AGE_TOPIC --from-beginning

**************************************************************************************************************END**********************************************************************************************************
