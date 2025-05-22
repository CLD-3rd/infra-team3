# infra-team3

All features have been tested and are working correctly.

---

## HOW TO START

1. Clone the repository: `git clone ~~~`
2. Build the project (skip tests): `mvn package -DskipTests`
3. Build the Docker image: `docker buildx build -t backend-server .`
4. Start containers: `docker-compose up`
5. Open in browser: `server-address:8080/countries`

## CAUTIONS:

- Ubuntu username must be: globetrek.
- Ensure the following directory exists: /home/globetrek/data/mysql_globetrek

This is required by docker-compose.yml for volume mapping.
