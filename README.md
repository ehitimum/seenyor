# Seenyor 
1. Opem terminal ctrl+T 
2. copy this line and paste in the terminal
```
git clone https://github.com/ehitimum/seenyor.git
``` 
3. Hit enter button
4. After the the cloning is done enter the sennyor folder and open vscode
```
   cd seenyor
   code
```
5. Now after openning the project you can head to the maven section in the bottom left corner of vscode
   ![Maven Lifecycle](https://github.com/ehitimum/seenyor/blob/main/Capture.PNG)
6. From here you can run clean, install operations. Previously we used terminal commands but errors are returning using those commands so this is better. 

## MySql
1. Head to the Authorization\src\main\resource\application.yaml
   ```
   spring:
    datasource:
      url: jdbc:mysql://localhost:3306/my_database
      username: root
      password: admin
      driver-class-name: com.mysql.cj.jdbc.Driver
      # type: com.zaxxer.hikari.HikariDataSource
      # pool-name: myPool
    jpa:
      hibernate:
        ddl-auto: update
        show-sql: true
   ```
   2. You can change the database name from url. Mine is my_database
## Running
1. Go to \src\main\java\com\yunyan\project\authorization\AuthorizationApplication.java and run it.
2. If successfully running you can start hitting the API

#### Running from command:
1. Head to the project directory seenyor
2. In the terminal execute this command
```
java -jar .\authorization\target\authorization-0.0.1-SNAPSHOT.jar
```
3. This will execute the jar file of this project.

## Server
1. Server is running at http://localhost:8080/

## Current API's
For Roles:
1. POST: ``` http://localhost:8080/roles/create-role ```
2. GET: ```http://localhost:8080/roles```
3. PUT: ```http://localhost:8080/roles/update-role/uuid``` (you can change uuid to path variable number such as 1 or 2 etc)
4. PUT: ```http://localhost:8080/roles/delete-role/uuid``` (uuid is path variable). This is a soft delete method where I flagged the is_deleted to true.

