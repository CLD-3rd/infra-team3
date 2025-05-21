# infra-team3

All features have been tested and are working correctly.

HOW TO START

Clone the repository: git clone ~~~
Build the project (skip tests): mvn package -DskipTests
Build the Docker image: docker buildx build -t backend-server .
Start containers: docker-compose up
Open in browser: server-address:8080/countries
CAUTIONS:

Ubuntu username must be: globetrek.
Ensure the following directory exists: /home/globetrek/data/mysql_globetrek
This is required by docker-compose.yml for volume mapping.
