docker run -d \
    --name kgs-db-postgres \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=pwd123 \
    -e PGDATA=/data/postgres-data \
    -v /custom/mount:/var/lib/postgresql/data \
    postgres