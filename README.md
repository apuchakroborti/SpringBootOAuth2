In this project I will show how to implement authorization using oauth2 from scratch:- \
SpringBoot
Flyway
Lombok
OAuth2

Steps to follow: 
1. Install MySQL \
1.1. Create user and give permission to it:- \
$ CREATE USER 'apu'@'localhost' IDENTIFIED BY 'tigerit'; \
$ create database studentdb; \
$ GRANT ALL PRIVILEGES ON studentdb.* TO 'apu'@'localhost'; 

2. Now to you have to create the necessary tables listed in the migration script 1. 
If you run this project the tables will be created automatically. 

3. Now to you have to insert some initial data into some tables: \
3.1. Insert two roles into the oauth_authority table authority name like ADMIN and USER \
3.2. Create an ADMIN user by inserting data into the oauth_user table \
username: admin@gmail.com \
password: The hashed password of '1234' by using the Bcrypt password encoder algorithm of strength 8. \
You can get it online and use any one of them. \
3.3. Insert data into the oauth_client_details tables. Insert rows based on the different types of client. The attributes are 
CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY
Here the client secret must be hashed. You can use the Bcrypt password encoder algorithm of strength 4. 

4. BUILD and RUN the Project: \
BUILD: $ mvn clean package \
Then run the project by using the below command: \
$ mvn spring-boot:run 

5. Access and Refresh Token generation: \
URL: http://127.0.0.1:9000/service-api/oauth/token \
Request Method: POST 

Headers: \
Content-Type: application/x-www-form-urlencoded \
Accept: application/json \
Authorization: Basic {{$token}} \
Here, $token: 'dGVzdC13ZWJhcHAtcnc6dGVzdC13ZWJhcHAtcnctMTIzNA==' \
How to make token: Base64Encoded string of 'test-webapp-rw:test-webapp-rw-1234' 

Body: \
grant_type: password \
username: YOUR_USERNAME (here, admin@gmail.com) \
password: YOUR_PASSWORD (admin123) 

You will get the access and refresh token here

6. Now You can call the Custom user related crud services:
HEADERS:
Content-Type: application/json
Authorization: Bearer {{access_token}} //from step 5

6.1 To create a user: \
POST http://127.0.0.1:9000/service-api/api/user \
6.2 To get  user by id: \
GET http://127.0.0.1:9000/service-api/api/user/{id} \
6.3 To get paginated users by search criteria: \
GET http://127.0.0.1:9000/service-api/api/user \
6.4 To update a user: \
PUT http://127.0.0.1:9000/service-api/api/user/{id} \
6.5 To delete a user by id: \
DELETE http://127.0.0.1:9000/service-api/api/user/{id} \
6.6 To update password: \
POST http://127.0.0.1:9000/service-api/api/user/update-password \
6.7 To reset password by username: \
POST http://127.0.0.1:9000/service-api/api/user/reset-password 
