$ CREATE USER 'apu'@'localhost' IDENTIFIED BY 'tigerit';
$ create database studentdb;
$ GRANT ALL PRIVILEGES ON studentdb.* TO 'apu'@'localhost';

Insert the initial role:
INSERT INTO studentdb.ROLES(ROLE_NAME, STATUS, DESCRIPTION, created_by, create_time, updated_by, updated_time, internal_version) VALUES('SUPER_ADMIN', 1, 'SUPER ADMIN ROLE', 0, CURRENT_TIMESTAMP , null, null, 1);

Insert The initial user:
INSERT INTO studentdb.users(
	first_name, last_name, username, email, password, user_role_id, status, created_by, create_time, updated_by, updated_time, internal_version)
	VALUES ('Admin', 'Admin', 'admin@gmail.com', 'admin@gmail.com','admin123', 1, 1, 0, CURRENT_TIMESTAMP , null, null, 1);

Note: here the password must be encrypted


Then run the project by using the below command:
$ mvn spring-boot:run

Then enter the path: http://127.0.0.1:8080/login