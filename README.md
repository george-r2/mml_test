<p align="center">
  Marsh and McLennan Technology Assessment
</p>


## Features

* There are implemented two endpoints for consuming weather info. 
* The first one is to consume a list of cities current weather and air pollution
* The second one returns a 5 days/3 hours forecast for one city
* Jacoco report of coverage is generated after maven install on __/src/target/site/jacoco/index.html__


## Requirements
The application can be run locally, the requirements are listed below.


### Local
* [Java 11 SDK](https://www.oracle.com/mx/java/technologies/downloads/#java11)
* [Maven](https://maven.apache.org/download.cgi)
* Docker and docker-compose


### Run Local
First is required to star the keycloack image for auth
```bash
$ docker-compose up auth
```
After that startup the weather service
```bash
$ mvn spring-boot:run
```

Application will run by default on port `8080`

Configure the port by changing `server.port` in __application.properties__

An openweathermap app key is required to be set on `opw.app-key` in __application.properties__. That key can be obtained following instructions [here](https://openweathermap.org/faq) on __How to get an API key__


## Testing
The oauth service has already configured client credentials
```bash
curl -L -X POST 'http://localhost:8180/realms/weather/protocol/openid-connect/token' \
--data-urlencode 'client_id=weatherClient' \
--data-urlencode 'client_secret=Pr97pgLUXfY4F9Ep6sbLCUyy9bt8eJFA' \
--data-urlencode 'grant_type=client_credentials' 
````


For call the first request here is an example
```
CURL http://localhost:8080/weather/forecast?city=<cityName> -H "Authorization: Bearer <token>"
```
For call the second request here is a CURL example
```
CURL http://localhost:8080/weather/current?cities=<cityOne>,<cityTwo> -H "Authorization: Bearer <token>"
```


