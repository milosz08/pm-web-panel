# PM Web Panel

An interactive and open-source management panel for PM2 instances updated in real time. It uses a
Java process runner, Spring Boot (server) and React environment, server-side events and websocket
protocol to send resources usage parameters and application logs in real time.

[TODO] Presentation gif

## Table of content

* [Clone and install](#clone-and-install)
* [Run with Docker (simplest, not for development)](#run-with-docker-simplest-not-for-development)
* [Setup (for development)](#setup-for-development)
* [Create executable JAR file (bare-metal)](#create-executable-jar-file-bare-metal)
* [Tech stack](#tech-stack)
* [Author](#author)
* [License](#license)

## Clone and install

To install the program on your computer use the command (or use the built-in GIT system in your IDE
environment):

```bash
$ git clone https://github.com/milosz08/pm-web-panel
```

## Run with Docker (simplest, not for development)

1. Go to root directory (where file `docker-compose.yml` is located) and type:

```bash
$ docker compose up -d
```

This command should create 1 docker container:

| Container name | Port(s) | Description                   |
|----------------|---------|-------------------------------|
| pm-web-panel   | 8080    | Application (client + server) |

> NOTE: Application outgoing port can be changed inside `.env` file.

## Setup (for development)

1. Setup client:

* Go to client directory (`$ cd app-frontend`) and install all dependencies via:

```bash
$ yarn install --frozen-lockfile
```

> NOTE: If you do not have yarn, install via: `npm i -g yarn`

* Run client via:

```bash
$ yarn run dev
```

2. Setup server:

* Go to server directory (`$ cd app-backend`) and type (for UNIX):

```bash
$ ./mvnw clean install
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

or for Windows:

```bash
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Check application state via endpoint: [/actuator/health](http://localhost:8690/actuator/health). If
response show this:

```json
{
  "status": "UP"
}
```

application is running and waiting for http requests.

3. Applications (client and server) should be available at ports:

| App name              | Port(s) |
|-----------------------|---------|
| pm-web-panel (server) | 8690    |
| pm-web-panel (client) | 8691    |

## Create executable JAR file (bare-metal)

To create executable JAR file (client + server), you must type (for UNIX):

```bash
$ ./mvnw clean package
```

or for Windows:

```bash
.\mvnw.cmd clean package
```

Output JAR file will be located inside `.bin` directory. With this file you can run app in
bare-metal environment without virtualization via:

```bash
$ java \
  -Xms1024m \
  -Xmx1024m \
  -Dspring.profiles.active=prod \
  -Dserver.port=8080 \
  -jar pm-web-panel.jar
```

## Tech stack

* React 19, Vite, Mui Components,
* Java 17, Spring Boot 3,
* Spring JDBC, SQLite,
* Docker containers.

## Author

Created by Miłosz Gilga. If you have any questions about this application, send
message: [miloszgilga@gmail.com](mailto:miloszgilga@gmail.com).

## License

This project is licensed under the AGPL-3.0 License - see the LICENSE file for details.
