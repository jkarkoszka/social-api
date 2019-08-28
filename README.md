# Social-API [![Build Status](https://travis-ci.com/jkarkoszka/social-api.svg?branch=master)](https://travis-ci.com/jkarkoszka/social-api) [![codecov](https://codecov.io/gh/jkarkoszka/social-api/branch/master/graph/badge.svg)](https://codecov.io/gh/jkarkoszka/social-api)

Simple Twitter-Like social API.

## Requirements
- JDK 12

## Build 

    ./mvnw clean package
## Test

    ./mvnw clean verify

## Run

   ./mvnw spring-boot:run

 # API documentation

 Swagger: http://localhost:8080/swagger-ui.html#/

## Scenarios

### Posting

A user should be able to post a 140 character message.

### Wall

A user should be able to see a list of the messages they've posted, in reverse chronological order.

### Following

A user should be able to follow another user. Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

### Timeline

A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.
