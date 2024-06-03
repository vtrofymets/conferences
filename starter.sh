echo "Start docker containers up"
docker-compose up -d

#docker container ls --all --quiet --filter "name=conferences-service"
#docker inspect -f '{{ .State.Running }}' `docker container ls --all | grep conferences-service | awk '{print $1}'`
docker ps