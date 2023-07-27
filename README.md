# SpringBootLearningAppIntegratedWithAdminVideoFunction

# How to run this SpringBoot project locally
1. Install MySQL server (for the database)
2. Install MySQL client (for friendly user interface to view the database):
Windows: HeidiSQL 
Mac (Intel): MySQLWorkBench 
Mac (M1 core): Sequel Ace
3. Connect to MySQL server from this SpringBoot Application
4. Install Maven (for building the app)
5. Install Java SDK (for running the app)
6. Install Spring Tool Suite or Visual Studio Code (IDE for easier view of the code)
7. Update the application properties file in this application folder by replacing
 **** with your database name 
spring.datasource.url=jdbc:mysql://localhost:3306/****?useSSL=false
spring.datasource.username= ****
spring.datasource.password= ****
8. Update the main application file by replacing **** with the directory at which the test images are stored
9. Ensure that the Flask Server is already running, if testing via http://localhost:8080/android or http://localhost:8080/test. Current configuration: consuming Flask Server deployed on google cloud: http://34.87.43.62:8080/android or http://34.87.43.62:8080/test
9. Within the root directory of this application:
run the following command on the terminal:
mvn spring-boot:run
