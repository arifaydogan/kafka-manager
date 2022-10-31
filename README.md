
# Kafka Manager

In this project, kafka producer and consumer management is done simply with Spring Boot.

## API Usage

#### Send a message to queue with Producer

```http
  POST /kafkamanager/sendMessage
```

| Parameter    | Type                  | Description                                                                |
|:------------|:----------------------|:---------------------------------------------------------------------------|
| `topicName` | `string`(TOPICS ENUM) | **required**. The name of the queue to which you want to send the message. |
| `key`       | `string`              | **required**. Unique key value that identifies the message                 |
| `message`   | `JSON`                | **required**. The message you want to send to the queue.                   |




## Environment Variables

To run this project you need to add the following environment variables to your .env file.

| Variable           | Default Value    | Description                              |
|:-------------------|:-----------------|:-----------------------------------------|
| `_PRODUCER_ACTIVE` | `true`           | The application activates the producer feature. |
| `_PRODUCER_ADRESS` | `locahost:9093`  | It is the server address and port of the producer. |
| `_CONSUMER_ACTIVE` | `true`           | The application activates the consumer feature. |
| `_CONSUMER_ADRESS` | `localhost:9093` | It is the server address and port of the consumer. |
| `_KAFKA_GROUP_ID ` | `testgroup`      | The name of the consumer group. |
| `_CONSUMER_TOPIC ` | `test`           | The name of the queue to be consume. |

## Kafka Message Topics(TOPICS_ENUM)

- OTHER - other
- PAYMENT - payment
- TRANSFER - transfer



## Topic References

The topic name sent as a request is collected on the relevant topic by kafka.

| TopicName(Request) | Topic(Kafka)                                                      |
|--------------------|-------------------------------------------------------------------|
| OTHER              | ![#0a192f](https://via.placeholder.com/10/0a192f?text=+) other    |
| PAYMENT            | ![#ff0000](https://via.placeholder.com/10/ff0000?text=+) payment  |
| TRANSFER           | ![#40E0D0](https://via.placeholder.com/10/40E0D0?text=+) transfer |

## Helper Links

#Swagger  -> http://localhost:9099/kafkamanager/swagger-ui.html

#Kafka-Ui -> http://localhost:8081

#CMAK     -> http://localhost:8085

## Build

#### Profile 'dev'
The project is selected as 'dev' in the maven profile by default. You can run it as usual via the IDE. To create local target jar

```bash 
  mvn clean install
  java -jar kafka-server-1.0.0.jar
```

#### Profile 'prod'
While the project is being loaded into the test and prod environment, the following maven command is run on the localhost:8081(nexus-repository) server to create a docker image for the nexus environment at localhost:8081 and to create a versioned jar for the maven-repository environment.
```bash 
  mvn clean deploy -P prod
```

For the application scaled on Docker swarm, the number of replicas can be changed via docker-compose files. Environment variables can also be defined here. To run the docker swarm service on localhost, the following commands are run on the machine with docker installed.
```bash 
  cd /root/kafka-server
  git checkout master
  git pull && mvn -T 20 clean deploy -P prod -DskipTests;
```
