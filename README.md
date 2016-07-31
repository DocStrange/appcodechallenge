#Api Integration challenge

This is a web application that ties in with AppDirect's API.
Currently it supports 5 different actions for subscriptions.

 * You can create new subscriptions to the application
 * You can cancel a subscription at any time
 * You can modify the type of subscription
 * You can assign users to a particular accounts subscription
 * You can unassign users from a particular accounts subscription


This web app is built using Spring and Hibernate, PostgreSQL for the database, and dozer for mapping objects to and from DTO (Data transfer objects).
JUnit and Mockito are used for the unit tests. Maven is the build tool used to manage the dependencies as well as packaging.
Finally, Apache Tomcat is the container used to run the application.

Everything has been deployed and running on my Digital Ocean virtual private server.

To test this application go to http://shaunmccready.com:8080/api/


## Installation

Download the source code. You must use a build tool such as maven and run the command:
    mvn clean install

This will produce a WAR file for deployment. It is located in the /target folder within the root folder of the project.
This file must be deployed in a container such as Tomcat.  Once that is done, you run the server.

After the server starts up go to this address in your browser:
    localhost:8080/api


The PostgreSQL database settings are located in the db.properties folder. These can be changed out for another Postgres server however if thats the case,
there is a file called 'init_tables.sql' located in the folder /db_scripts just off the root directory. This will initialize the database to be used.


## These are the end points supported by this application:

* /subscription/create?url={eventUrl}
* /subscription/cancel?url={eventUrl}
* /subscription/change?url={eventUrl}
* /user/assign?url={eventUrl}
* /user/unassign?url={eventUrl}




