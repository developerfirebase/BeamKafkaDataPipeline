# Data Streaming Application with Apache Beam, Kafka, Spring Boot and Docker

This is a simple light weight, highly adaptable and scalable  data processing application built with Apache Beam. It consumes messages from a inbound Kakfa topic (namely SOURCE_TOPIC) and calculates the age from the date of birth, and publishes the messages to two separate outbound Kakfa topics (namely EVEN_AGE_TOPIC and ODD_AGE_TOPIC)  based on whether the calculated age is even or odd.

## Technology Stack
- Java 17
- Spring Boot
- Apache Kafka
- Apache Beam
- Docker

## Prerequisites : Create topics for Kafka Docker conatiner inside  opt/kafka/bin directory using below commands

### 1. Create SOURCE TOPIC 
kafka-topics.bat --create --topic SOURCE_TOPIC --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

### 2. Create EVEN TOPIC
kafka-topics.bat --create --topic EVEN_AGE_TOPIC --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

### 3. Create ODD TOPIC
kafka-topics.bat --create --topic ODD_AGE_TOPIC --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

## Setup Instructions for Running the application
  1. Clone this repository.
  2. Navigate to the project directory and build the project using Maven: Build  using the command : mvn clean install
  3. Go to terminal and inside project directory start the docker by using docker compose up -d
  4. **Start Kafka Server**: Ensure that Kafka is installed and running on `localhost:9092`and required topics are created.
 ###  5. Hit these API using postmann 
      - `http://localhost:8080/send` - Sends the initial messages on `SOURCE_TOPIC`.
      - `http://localhost:8080/consume` - Consumes and processes messages from `SOURCE_TOPIC`.
                                
   
## To verify messages in EVEN_AGE_TOPIC, use the following command:
          kafka-console-consumer --bootstrap-server localhost:9092 --topic EVEN_AGE_TOPIC --from-beginning
## To verify messages in ODD_AGE_TOPIC, use the following command:
           kafka-console-consumer --bootstrap-server localhost:9092 --topic ODD_AGE_TOPIC --from-beginning
           
## Enhancements which can be achieved
Adjust the error handling, logging, and testing as needed to fit project requirements.
Add necessary properties file to connect to Kafka cluster and handle movement of data using required dependecies .
Single Broker Setup: This guide describes a simple single-broker setup suitable for development and testing. For production, a multi-broker setup is used.
Data Loss: This setup is primarily for development purposes. Data persistence and reliability settings should be configured according to production requirements.






**************************************************************************************************************END**********************************************************************************************************
