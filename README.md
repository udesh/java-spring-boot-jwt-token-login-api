
This project use a postgresql db, 
you can even use mysql if mysql driver and dependancies are added.

*You can add the required postgresql database user credentials to the application.properties file.
Eg -
spring.datasource.url=jdbc:postgresql://localhost:5432/demo
spring.datasource.username=demo
spring.datasource.password=demo

*How you can run the project-

Unzip the project file and go to the folder. ( cd estabild-demo )
You can run Gradle tasks from that folder.
Eg - ./gradlew clean build
To run the Spring boot application on 8080 port.
./gradlew bootRun

*You can use the below API endpoints.

*Register User
localhost:8080/api/v1/register-user

POST
Request body -
{
    "username": "udeshl",
    "password": "qwert123",
    "firstName": "Udesh",
    "lastName": "Liyanaarachchi",
    "email": "udesh@gmail.com",
    "createdBy": "Udesh",
    "updatedBy": "Udesh"
}

*Login User

http://localhost:8080/api/v1/auth/login
POST
Request body -
{
    "username": "udesh",
    "password": "qwert123"
}

*Login History

http://localhost:8080/api/v1/login-history/{username}
with authorization Header
{ Authorization : <jwt token> }
We can get this from the login response header.