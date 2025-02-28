# Demo java with microservice, docker, CI/CD
Java 17, System Design, Architecture, Microservices, TDD (Test-Driven Development) &amp; DDD (Domain-Driven Design), AWS, Saga Pattern, Kafka CDC, Backend-for-Frontend (BFF), CI/CD...

## Workflows CI/CD auto deploy using Github action & Dockerhub to cloud server AWS

![System-design](https://github.com/user-attachments/assets/d1920ef0-e239-4e09-8367-94eed203d753)

## Setup Environment secrets

To run this project, you will need to add the following environment variables to Actions secrets and variables

#### Setup environment variables connect to docker hub

//password docker-hub

`DOCKER_PASSWORD`

//username docker-hub

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

Documentation Docker-hub

[Documentation](https://hub.docker.com/)

## Workflows CI/CD

#### Auto deploy service api with Github Actions

Create Branches from repo deploy-api 

```bash
  git checkout deploy-api
```

Push code to Branches or push update code 

```bash
  git push --set-upstream origin deploy-api
```

Github actions run


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

## API Reference

#### Method GET

```http
  GET http://54.169.162.172:9000/master/config
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `token` | `string` | **Required**. Your token key |

#### Method POST

```http
  POST http://54.169.162.172:9000/account/new
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. Body data |

#### json

```bash
  {
    "id":0,
    "name":"test",
    "email": "test1@gmail.com"
  }	
```

## Acknowledgements

- [Apache thrift](https://thrift.apache.org/)
- [Gearmand](https://gearman.org/)
- [Redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/)
- [Elasticsearch](https://www.elastic.co/elasticsearch)
- [Docker](https://www.docker.com/)
- [Github Actions](https://github.com/features/actions)

