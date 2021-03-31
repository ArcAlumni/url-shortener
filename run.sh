docker-compose down
docker image rm url-shortener_app:latest
docker image rm url-shortener_kgs:latest
cd kgs
mvn clean install
cd ../app
mvn clean install
docker-compose up
