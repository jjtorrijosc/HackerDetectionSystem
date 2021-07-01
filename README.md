# HackerDetectionSystem

Spring Boot application that provide a REST service with the operation 'parseLine'.
This operation receives a String with one log line of a external signin process. 
The format of this log line is "ip,date,action,username".
  IP look like 80.238.9.179 Date is in the epoch format like 1336129471 Action is one of the following:
      SIGNIN_SUCCESS or SIGNIN_FAILURE Username is a String like Pedro.Lopez
  
The operation return the IP address if any suspicious activity is identified or null if the activity appears to be normal.
It determines an IP like suspicious if that has attempted a failed login 5 or more times within a 5 minute period.
 
Use a MongoDB database to store failure signins. Only store 'ip' and 'date' fields of the log line.
As the application only need process failure signins in the last five minutes, for reduce the memory consumption, an TTL index has been created. With this index type, MongoDB deletes automatically the register after Time To Live especified have been passed (5 minutes in this case).
The application also creates another index in "ip" field to reduce request time because all the database queries will be by this field. 
  
Maybe another solutions like in-memory data structure stores like REDIS would take better performance, but this option was discarded about greater data structures complexity (maybe Redis List or Set was the better option at this case).
With MongoDB we are free to create simplier data estructures and this will increase maintenability for the SW.
 
