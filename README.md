# Getting Started

## About the api 

* A Scheduled Service retrieves  all the 100 stocks that compose Nasdaq-100 Index.Currently the frequency of retrieval is set every
5 minutes because of resource limitations. The frequency is configurable in application.yml.
* This service uses an unofficial reverse-engineered api. (https://www.nasdaq.com/market-activity/quotes/nasdaq-ndx-index)
* User can register and open many accounts.
* User can topUp an account.
* User can buy an asset inside for an account given he has eligible funds.
* User can get his account, current assets and information about how much his assets are worth
based on the latest stock price.
* User can retrieve all tracked indexes and  its composing stocks. (Only Nasdaq-100 so far)

**Check openapi documentation for all exposed endpoints [OpenAPI documentation](documentation/)**
<br>
**Use postman collection to test the api : [Postman Collection](documentation/)**  

## Requirements

* Java 15
* Gradle 
* Postgres

#### Running the db and application separately

* make sure that you have Postgres running and the connection url is set correctly in application.yml
* postgres can also be run via /dev-scripts/docker/psql/stack.yml (check corresponding readme in that
directory and read up on docker and docker-compose. make sure that you don't have other services running
on your local machine's ports that are specified in this project's configuration. if conflicts arise,
then change the ports in the stack.yml file and the application.yml database source url or kill the
conflicting services beforehand to free the ports.)
* the suggested way of running the application is via intellij (project sdk set to jdk 15)
* the application can also be run via gradle cli. for this you have to make sure that gradle will use
the correct jdk to execute the commands. you can use your local gradle installation or the gradlew and
gradlew.bat executables in the project root. if gradle is using a different jdk than jdk 15, then google
your environment's instructions on how to specify a different jdk home for gradle for this project.
the main commands to use are (./gradlew clean build) and (./gradlew bootRun)

#### Running the db and application both via docker

Any time the external dependencies change, run from the project's root:
```
docker build --rm --network host --tag stocksapi:0.1 .
```
because the containers cannot access the internet by themselves, and running
the docker-compose via host networking defeats the purpose of encapsulation.
A symptom of having not up-to-date external dependencies is that the docker-compose
runs into issues via getting request timed out responses to repository requests.

Further docker-compose runs even with the build flag enabled will then only rebuild
the local code artifacts, and docker-compose will use a cached step for the external dependencies.

This option suits you if you have trouble setting your local environment up, but have access to docker.
A second applicability is if you are just developing the front-end. This option has slower application
restarts and every restart flushes the database. For this option, one can use the
/dev-scripts/docker/stack.yml compose file to run the application. to build a new version of the back-end,
run the compose file via (docker-compose -f stack.yml up --build). if you do not want to build a new
version of the back-end, then omit the --build flag.

As building new images will take up disk space over time, it is advisable to prune images not in use
periodically. To check the images stored on your machine, run (docker image ls -a) and to prune unused
images, execute the command (docker image prune -f) or (docker image prune -a -f). READ ABOUT WHAT THESE
COMMANDS DO before executing them to not lose any custom images that you have built before-hand.

* admin credentials in the application: admin@testing.com / testing2 (inserted via a changeset)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#howto-execute-liquibase-database-migrations-on-startup)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

