# Sporty Group Eventio

## Pre-requisites

1. Java 24+
2. (Optional) RabbitMQ 3+

### Lombok

Since Lombok is used, you might need to install the appropriate plugin within your IDE (https://projectlombok.org)

### Code Style

For those using Intellij IDEA there is already a formatter included in the Project. If it's not enabled by default you
can do that in File->Settings->Editor->Code Style-Scheme->Project.
Also make sure that "Enable EditorConfig support" is checked, this can be found in File->Settings->Editor->Code Style

## Running the project

### Tests

```gradlew test```

### Local

In-process implementations of the score service and message publisher can be used for easier local development.

```gradlew application:bootRun --args='--spring.profiles.active=score-in-process,messaging-in-process'```

### Production

Requires a running RabbitMQ instance and a Score service instance.

```gradlew application:bootRun --args='--spring.profiles.active=score-rest,messaging-rabbitmq'```

You might also need to configure the following (as well as any other properties like the RabbitMQ host and port) in your
application.properties:

```
score-rest.url=...
sport-event.score-messaging-key=... // defaults to SportEventScoreMessage
```

## Architecture

### application

* Application layer
* Main entry point
* Connects all the other modules

### sport-event

* Main logic for sport events
* Exposes REST endpoint POST /events/status with validation
* Does not depend on any concrete implementation of the message publisher or score service

### score

* External service to get score data
* Implementations: *REST* (profile: score-rest) with retry logic and *in-process* (profile: score-in-process)

### messaging

* Messaging library
* Implementations: *RabbitMQ* (profile: messaging-rabbitmq) with retry logic and *in-process* (profile:
  messaging-in-process)
* Can be extended easily with other implementations (RocketMQ, Kafka, etc.) without changing any other modules (as they
  depend on the API, not concrete implementations)

## Potential improvements

Add Swagger/OpenAPI documentation.

## AI Usage Disclosure

Jetbrains AI Assistant with Claude 4 was used for autocomplete, as a Google substitute and for some docs and some unit tests
generation.
