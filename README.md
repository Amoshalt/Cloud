# Cloud
API rest for Antoine Caron, CLOUD.

Subject [here](https://slashgear.github.io/cloud/)

## Students
 - LOMBARDO Florian
 - REMOND Victor
 - TARDY Martial

## Introduction
Groupe D
Spring Boot

## MongoDB

### Local use
A local use of this application and MongoDB is possible without editing the application's configuration.
All you have to do is adding the user *user* with a *read-write* access to the *cloud* database.
If you don't know how to do that, follow the next steps :
 1. Connect to your *MongoDB* shell
  * If *MongoDB* is installed directly on your machine :
  ```sh
  mongo
  ```
  * If you are using a docker environment (where *pmongo* is the name of your mongo process) :
  ```sh
  docker exec -it pmongo mongo
  ```
 2. Use this command to add the locally needed mongo user
 ```mongodb
 db.createUser({user: "user",pwd: "pwd",roles: [{ role: "readWrite", db: "cloud" }]})
 ```
 3. Enjoy, the database used is *cloud* and the collection edited is named *users*

## Clever-Cloud use
Manipulation to do on first use only.
 * Go on your machine view
 * In '*Services Liés*' panel add your *MongoDB* addon
 * Application detects all parameters from the addon's published configuration by itself.
