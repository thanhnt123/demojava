# Demo java with microservice, docker, CI/CD
Java 17, System Design, Architecture, Microservices, TDD (Test-Driven Development) &amp; DDD (Domain-Driven Design), AWS, Saga Pattern, Kafka CDC, Backend-for-Frontend (BFF), CI/CD...


![System-design](https://github.com/user-attachments/assets/d1920ef0-e239-4e09-8367-94eed203d753)

## Environment secrets

To run this project, you will need to add the following environment variables to Actions secrets and variables

#### Setup environment variables connect to docker hub

`DOCKER_PASSWORD`

`DOCKER_USERNAME`

#### Setup environment variables connect to cloud aws

`AWS_HOST`

`AWS_PORT`

`AWS_USERNAME`

`SSH_KEY`

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

[Documentation](https://docs.docker.com/get-started/)install

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
