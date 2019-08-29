# Social-API [![Build Status](https://travis-ci.com/jkarkoszka/social-api.svg?branch=master)](https://travis-ci.com/jkarkoszka/social-api) [![codecov](https://codecov.io/gh/jkarkoszka/social-api/branch/master/graph/badge.svg)](https://codecov.io/gh/jkarkoszka/social-api)  

Simple Twitter-Like social API.  

## Requirements  

- JDK 12  

or

- Docker  

## Build & Test & Run locally

    ./mvnw clean package spring-boot:run

## Build & Test & Run via Docker

    docker build -t social-api . && docker run -p 8080:8080 -it social-api

## API documentation & Sandbox

Swagger: http://localhost:8080/swagger-ui.html#/

## Additional comments

Why do I pass current user id as a header?
If we implemented authentication, it would be easy to change it from this mocked header to eg. JWT implementation.

Current user ID have to be in MongoDb ObjectId format.

There is a configured pipeline for this project on Travis CI.
Code coverage by tests is tracked on codecov.io.

## Scenarios

### Posting

A user should be able to post a 140 character message.

Covered by POST /posts endpoint.

### Wall

A user should be able to see a list of the messages they've posted, in reverse chronological order.

Covered by GET /posts/my endpoint.

### Following

A user should be able to follow another user. Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

Covered by POST /user/me/following endpoint.

### Timeline

A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.

Covered by GET /posts/followingUsers endpoint.
