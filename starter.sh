echo "Stop containers"
docker stop conferences-service
docker stop conferences-db
echo

echo "Remove containers"
docker rm conferences-service
docker rm conferences-db
echo

echo "Build conferences-service"
docker build -t conferences-service .
echo

echo "Init variables"
dbHost=host.docker.internal
dbUser=postgres
dbPassword=postgres
dbPort=5432
echo

echo "Run postgres"
docker run -d --name conferences-db --env POSTGRES_DB=conferences --env POSTGRES_USER=$dbUser --env POSTGRES_PASSWORD=$dbPassword -p 5432:5432 harbor.avalaunch.aval/docker-hub-proxy/postgres:15

docker ps
echo

echo "Run conferences-service"
docker run -d --name conferences-service --env DB_URL=$dbHost --env DB_USER=$dbUser --env DB_PASSWORD=$dbPassword --env DB_PORT=$dbPort -p 8080:8080 conferences-service

docker ps
echo

echo "Finish"