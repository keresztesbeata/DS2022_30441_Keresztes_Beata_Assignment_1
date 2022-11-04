# DS2022_30441_Keresztes_Beata_Assignment_1

For the deployment of the application, it is necessary to have Docker installed, as well
as a Linux distribution, or a Linux image installed in WSL in case of a Windows system.

The application can be deployed locally using Docker, by executing the following instructions:
 
Navigate to the application root folder, then run the command:
> docker-compose --build up -d

This command will create a docker container for the application, which contains the back-end application (spring-boot app), 
the front-end application in React, and the database server, MySQL.
It will also start the servers, which can communicate with each other on
the configured ports.
