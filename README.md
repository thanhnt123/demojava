[sql.txt](https://github.com/user-attachments/files/19021893/sql.txt)# Demo java with microservice, docker, CI/CD
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

<img width="1062" alt="Screen Shot 2025-02-28 at 9 06 31 AM" src="https://github.com/user-attachments/assets/1cd02482-4e1c-4836-bd38-1735b64cd36c" />

<img width="1417" alt="Screen Shot 2025-02-28 at 9 07 14 AM" src="https://github.com/user-attachments/assets/7b68beea-6e1f-4da4-b3b0-d60dc6696db6" />

<img width="1403" alt="Screen Shot 2025-02-28 at 9 07 35 AM" src="https://github.com/user-attachments/assets/abc58c92-3aba-4b7d-b5df-85cf96296d7f" />

<img width="1396" alt="Screen Shot 2025-02-28 at 9 07 43 AM" src="https://github.com/user-attachments/assets/226cecfc-448d-4743-9cb0-af9eb89ce1ca" />

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

URL server cloud aws

```bash
http://54.169.162.172:9000
```

URL localhost

```bash
http://localhost:9000
```

#### Method GET

```
  http://54.169.162.172:9000/master/config
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `token` | `string` | **Required**. Your token key |

#### Method POST

```
  http://54.169.162.172:9000/account/new
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

## Gen code thrift entity

Install thrift exp: libthrift:0.9.x

```bash
https://thrift.apache.org/tutorial/java.html
```

Go to the project lib thrift directory

```bash
  cd lib.thrift/resource
```

```bash
  thrift --gen java account.thrift
```

Copy file gen to the project

## Mysql database

[Uploading sqCREATE TABLE `tb_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `displayname` varchar(200) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `verifiedAt` timestamp(6) NULL DEFAULT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_cil.txt…]()


## Acknowledgements

- [Apache thrift](https://thrift.apache.org/)
- [Gearmand](https://gearman.org/)
- [Redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/)
- [Elasticsearch](https://www.elastic.co/elasticsearch)
- [Docker](https://www.docker.com/)
- [Github Actions](https://github.com/features/actions)
- [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Spring](https://spring.io/projects/spring-boot)

