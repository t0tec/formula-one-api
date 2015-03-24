# Formula-one-api

## About
This project is an api (similar to [Ergast](http://ergast.com/mrd/db))
for getting formula one data in a restful representation.
I made this project to learn and use Spring, REST & JPA/Hibernate and to test on
different relational databases.

The following topics are covered:

- A relational database that holds data about Formula One.
- A REST service that exposes the data in the database.
- Rest documentation with swagger

## Quick start guide

### Import database data:

#### MySQL
In MySQL Workbench:
1. Click on Server tab
2. Click on Data import menu
3. Select import from Self-Contained file and navigate to the MySQL sql script
4. Press Start import

With command line:

`mysql -u username -p password f1db < /path/to/f1dbmysql20150323.sql`

#### Postgresql:
In pgAdmin3:
1. select the required target schema in object tree
2. Click on Plugins/PSQL Console
3. Write: `\i /path/to/yourfile.sql`
4. Press enter

With command line:

`psql -U postgres -d f1db -f /path/to/f1dbpostgresql20150323.sql`

##### SQL scripts:
* [f1dbmysql20150323.sql](https://github.com/t0tec/formula-one-api/blob/master/main/repositories/src/main/resources/f1dbmysql20150323.sql)
* [f1dbpostgresql20150323.sql](https://github.com/t0tec/formula-one-api/blob/master/main/repositories/src/main/resources/f1dbpostgresql20150323.sql)

## Configure application

Adjust [application.properties](https://github.com/t0tec/formula-one-api/blob/master/main/repositories/src/main/resources/application.properties) to let it work on your local setup.

examples:
* [mysql-setup](https://github.com/t0tec/formula-one-api/blob/master/main/repositories/src/main/resources/application-mysql.properties)
* [postgresql-setup](https://github.com/t0tec/formula-one-api/blob/master/main/repositories/src/main/resources/application-postgresql.properties)

## Running this application
To run this project:
Navigate to `main` with command line tool
* `mvn clean install`

Than navigate to `main/rest` with command line tool to run it

* `mvn clean jetty:run -P dev`

Then point your browser to the Swagger api documentation link available at: `http://localhost:8080/doc/index.html`

It will provide an user interface which visualizes an applications JSON api.

Note: still XML representation is also supported but not available in Swagger
because it only supports JSON.

## Technologies:
* [Maven](http://maven.apache.org/)
* [Spring IO](https://spring.io/platform)
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [REST services with Spring](https://spring.io/guides/gs/rest-service/)
* [Swagger-spring-mvc](https://github.com/springfox/springfox)

## TODO:
* Enable and configure travis-ci for running tests
* A lot more api endpoints that need to be added
* [Bug](https://github.com/springfox/springfox/issues/610) from Swagger-spring-mvc: positioning controllers/methods not working

## Attribution
* Thanks to [Ergast](http://ergast.com/mrd/db) for the usage and keeping the database up to date
* Thanks to [Spring](https://spring.io/guides) for the useful guides
* Thanks to [pkainulainen](https://github.com/pkainulainen) for the useful tutorials and examples
* Thanks to [lanyrd](https://github.com/lanyrd/mysql-postgresql-converter) for his mysql to postgresql script converter
