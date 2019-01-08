# Cloud
API rest for Antoine Caron, CLOUD.

Subject [here](https://slashgear.github.io/cloud/)

Git repository [here](https://www.github.com/Amoshalt/Cloud)

## Students
 - LOMBARDO Florian
 - REMOND Victor
 - TARDY Martial

## Introduction
Groupe D - Spring Boot

Cloud initiation with : 
 * [GitHub](https://www.github.com) for versioning
 * [Clever-Cloud](https://www.clever-cloud.com/en/) for hosting
 * [CircleCI](https://circleci.com/) for CI

## MongoDB

The following paragraphs are for using *MongoDB* while running the server. No *MongoDB* installation is needed for running tests.

### Local use
A local use of this application and MongoDB is possible without editing the application's configuration if you don't need to authenticate yourself for using MongoDB.
If you need to authenticate, create an environement variable called `MONGODB_ADDON_URI` containing you mongo connection URI (see [this page](http://www.mongoing.com/docs/reference/connection-string.html)).
Enjoy, the database used is *cloud* if you don't set your own URI and the collection edited is named *users*

## Clever-Cloud use
Manipulation to do on first use only.
 * Go on your machine view
 * In '*Services Liés*' panel add your *MongoDB* addon
 * Application detects all parameters from the addon's published configuration by itself.

## Relaunch process
  * Create new machine
  * Select 'From local repo'
  * Select 'Java - Maven' machine
  * Say OK to all
  * In same time (to be more efficient)
    * Set tokens in *CircleCI*
    * Bind *MongoDB* addon to machine in *Clever Cloud*
  * Make a commit on master