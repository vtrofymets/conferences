echo "Start to shutdown"

docker stop conferences-service
docker stop conferences-postgres

docker ps