# Demo java with microservice, docker, CI/CD
Java 17, System Design, Architecture, Microservices, TDD (Test-Driven Development) &amp; DDD (Domain-Driven Design), AWS, Saga Pattern, Kafka CDC, Backend-for-Frontend (BFF), CI/CD...

## Workflows CI/CD auto deploy using Github action & Dockerhub to cloud server AWS

![System-design](https://github.com/user-attachments/assets/d1920ef0-e239-4e09-8367-94eed203d753)

## Setup Environment secrets

To run this project, you will need to add the following environment variables to Actions secrets and variables

#### Setup environment variables connect to docker hub

//password dockerhun
`DOCKER_PASSWORD`

//username dockerhub
`DOCKER_USERNAME`

#### Setup environment variables connect to cloud aws

//host server aws

`AWS_HOST`

//port 22

`AWS_PORT`

//username

`AWS_USERNAME`

//SSH key get from server

`SSH_KEY`

## Documentation Dockerhub

[Documentation](https://hub.docker.com/)

## Run Locally

Clone the project

```bash
  git clone https://github.com/thanhnt123/demojava.git
```

Go to the project directory

```bash
  cd demojava
```

Install and start docker

[Documentation](https://docs.docker.com/get-started/)

Start docker compose start Kafka & Zookeepeer

```bash
  docker-compose up -d —build
```

Start microservice

```bash
  Run service api

  Run service account

  Run service notification

  Run service report
```

## Run Test

Method GET

```bash
  http://54.169.162.172:9000/master/config
```

Method POST

```bash
  http://54.169.162.172:9000/account/new
```

Boby json

```bash
  {
    "id":0,
    "name":"test",
    "email": "test1@gmail.com"
  }	
```

