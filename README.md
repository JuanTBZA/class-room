
## PASOS COMPLETOS

# 1. Compilar el proyecto (genera el .jar)
mvn clean package -DskipTests

# 2. Construir la imagen Docker
docker build -t juantiradoboza/api-class-room:latest .

# 3. Eliminar contenedor previo (si existe)
docker rm -f archivos-app || echo "Sin contenedor anterior"

# 4. Ejecutar con Docker Compose usando archivo de entorno
docker compose --env-file .dev.env up --build -d

# 5. Subir la imagen a Docker Hub
docker push juantiradoboza/api-class-room:latest
