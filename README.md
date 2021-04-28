# Horse Datatable
## Individual assignment


This web application allows users to store horses and suitable sports. Every horse can be releated upto one sport.

## Features

- Horses can be added, deleted, updated, searched and shown in detail-view
- Sports can be added and deleted
- For every horse name, date of birth and sex are required fields
- Horses can have and be parents of other horses, this results in a family-tree structure
- Horses can be searched by name, dob, sex, description and favorite sport

By clicking the "Load all Horses" Button all horses are shown regardless of their family releation.
By ticking the checkfield near the "Name"-Label the tree structure gets enabled and you have only access to the leave nodes(Children). 
By ticking the ckeckfield besides a childhorse will show their related parents, if those exists
To reset the family-view just tick the checkfield near the "Name"-Label again.

## Tech

This is a full stack application with a back and frontend

- [Java JDK 11](https://openjdk.java.net/projects/jdk/11/) - used as main language for the backend
- [Maven](https://maven.apache.org/) - oftware project management and comprehension tool
- [Spring Boot](https://start.spring.io/) - Framework used in the backend
- [Node.js 14](https://nodejs.org/en/download/current/) - designed to build scalable network applications
- [Angular 11](https://angular.io/) - web developent platform


## Installation

Wendy's family tree requires [Node.js](https://nodejs.org/) v10+ to run.

Install angular and start the server.

```sh
cd wendys-frontend
npm install -g @angular/cli
ng serve
```

For the backend....

```sh
cd wendys-backend
mvn clean package
mvn clean compile spring-boot:run
```

