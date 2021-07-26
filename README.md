# Code Challenge - Mortal Kombat

## Prerequisites

- Docker running locally
- Docker Compose
- Maven (Optional)

## How to run

1. Go to the command line
2. Clone this repository: `git clone https://github.com/phgocarvalho/java-mortal-kombat.git`
3. Go to the project root directory
4. Run the application and all required services using docker compose

    - For external version: `docker-compose up`
    - For embedded version: `docker compose up`

OBS: It already has the updated built .jar, but it's possible to rebuild it using maven: `mvn clean package`

**See the Dockerfile in the project root directory for more information about the packaging.**

## How to use

### Application API

The Application container is exposed from the Docker to the Docker host in the port `8080`

- HTTP request which returns all saved players from the database:
    - `GET http://localhost:8080/players`
- HTTP request which process the players from a payload, for instance
    - `POST http://localhost:8080/players`
        ```json
        {
           "players":[
              {
                 "name":"Sub zero",
                 "type":"expert"
              },
              {
                 "name":"Scorpion",
                 "type":"novice"
              },
              {
                 "name":"Reptile",
                 "type":"meh"
              }
           ]
        }
        ```

### Zookeeper API

The Zookeeper container is exposed from the Docker to the Docker host in the port `2181`

### Kafka API

The Kafka is exposed from the Docker to the Docker host in the port `19092`

- CLI Consumer which listen to the next 10 players which were sent to the novice-players topic:
    - `docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:19092 --topic novice-players --max-mess ages 10`

## How to run the tests

### Unit Test Classes

- Integration tests
    - com.codechallenge.mortalkombat.controller
    - com.codechallenge.mortalkombat.messaging
- Business tests
    - com.codechallenge.mortalkombat.service
- Persistence tests
    - com.codechallenge.mortalkombat.repository

## Stack Tech

- Zookeeper
- Kafka
- Spring Boot
- H2 Database

**OBS: See the docker-compose.yml file for more information about the running.**
