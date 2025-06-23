## Pasos para compilar en docker:

### 1.- Generar Dockerfile: Dockerfile

### 2.- Clean and install del proyecto
    mvn clean package -DskipTests

### 3.- Construir la imagen (estando en la misma carpeta donde esta el Dockerfile)
    docker build -t juantiradoboza/api-class-room:latest .


### 4.- Ejecutar el contenedor

#### Con Docker compose file: docker-compose.yml
    docker compose -f docker-compose.yml -p api-class-room --env-file .dev.env up --force-recreate --build -d