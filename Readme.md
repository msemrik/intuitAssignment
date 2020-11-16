#Installation
gradlew clean build

The installation includes the in-memory database. 

To access the db:
http://localhost:8080/h2-console/login.jsp <br>
Credentials: intuit<br>
User: assignment<br>
JDBC URL: should point to the folder where the project is hosted. Something like this: jdbc:h2:~\development\projects\intuitAssignment\database

#Running the app
gradlew bootRun

###Heroku
App is deployed in heroku. Can be find in following URL:

https://intuit-assignment.herokuapp.com/

# Usage
The app contains two endpoints:
###GET - /refresh
Receives no parameters. 
Example:
https://intuit-assignment.herokuapp.com/refresh

###GET - /myAggregatedHub
Receives JSON body as parameter for filtering. Every value is optional.
<br>
 Empty object apply no filter to search.

Example:
GET https://intuit-assignment.herokuapp.com/refresh
{
  "product": "BLUE",
  "provider": "6111",
  "customerId": 818591,
  "errorCode": 324,
  "open": true,
  "dateRange": {
    "startDate": "2019-01-28",
    "endDate": "2019-01-28"
  }
}


# Application configuration
Configuration is placed in application.properties

Relevant properties:<br>
relevantProducts: only products that will be stored and retrieved in the app
minimumRefreshTimeInMins: minimum time between two refresh calls<br>
automaticRefreshInMinutes : maximum time without refresh<br>
crm.crmname.identifier: CRM unique identifier<br>
crm.crmname.fullAggregationUrl: CRM Url for full aggregation<br>
crm.crmname.minimumTimeBetweenFullAggregationInMins: CRM minimum time needed between full aggregation calls<br> 

#Future implementations
For adding new option calls to CRM the following changes are needed:

### CRMHandler interface and implementation of Interfaces 
Add new method for calling new endpoints.<br>
Each of this new methods will only have to define in the application.properties the URL.
If it follows the minimum time between calls policies could follow fullAggregation property: minimumTimeBetweenFullAggregationInMins

If it has a new restriction policy (as maximum concurrent thread) this could be independently defined in each CRM method.

###ApplicationController class
Add request body or param to refresh endpoint to determine type of refresh
 
###ApplicationRequestHandler class
In method refreshDataForRequest determine which method in CRMHandler implementation should be called.
