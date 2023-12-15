# Steps to Run 
1. After cloning the repository run
  ```bash
   mvn clean install
  ```
2. Then run

   ```bash
     docker-compose up -d
   ```
3. After some time, the system will be up and running
4. To access API documentation, Actuator endpoints, and PgAdmin UI, you need to go to the following url
   ```bash
   http://localhost:8000/swagger-ui/index.html
   ```
   ```bash
   http://localhost:8000/actuator
   ```
   ```bash
   http://localhost:8881
   ```
5. To login in PgAdmin you need to use the following user & password
  ```bash
    User: dev@test.com
    Password: password@test
  ```
6. and also you need to connect DB server to the PG admin by yourself with the following information
  ```bash
    Host: db
    Port: 5432
    DB: booking_db
    User: dev@test
    Password: password@test
  ```
7. I also have pushed the Booking Service image to the docker hub, you can access the image by following url
   ```bash
   https://hub.docker.com/repository/docker/shahedbhuiyan/astha-assesment-repo/general
   ```
