﻿# DS2022_30441_Keresztes_Beata_Assignment_1

For the deployment of the application, it is necessary to have Docker installed, as well
as a Linux distribution, or a Linux image installed in WSL in case of a Windows system.

The application can be deployed locally using Docker, by executing the following instructions in bash:
 
Create a private network in Docker for the communication between the containers by running the script:
> ./create-private-network.sh

Make sure to have the most up-to-date version of the application jar file, build the jar file with:
> mvn clean package

Navigate to the application root folder, then run the command:
> docker-compose --build up -d

This command will create the docker containers for the application, which consist of 
the back-end application (spring-boot app), the front-end application in React, and the database server, MySQL.
It will also start the servers, which can then communicate with each other on the configured addresses.

The user can access the application from any browser using the url address:
> http://localhost:3001

This will open the welcome page, from where the user can choose to log in or register with a new account.
![img.png](doc/welcome_page.png)

Depending on the role of the user, a different dashboard will be displayed, from where they can
have access to certain functionalities:

Admin:
* manage users
* manage devices
* link devices to users
![img.png](doc/admin.png)

Client:
* view associated devices
* view energy consumption
![img.png](doc/client.png)
