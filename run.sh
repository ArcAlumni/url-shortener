docker-compose down
docker image rm url-shortener_app:latest
docker image rm url-shortener_kgs:latest
cd ./kgs/web
sh build.sh
cd ../kgs/worker
sh build.sh
cd ../../web 
mvn clean package
docker-compose up
