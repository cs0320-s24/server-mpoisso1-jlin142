> **GETTING STARTED:** You must start from some combination of the CSV Sprint code that you and your partner ended up with. Please move your code directly into this repository so that the `pom.xml`, `/src` folder, etc, are all at this base directory.

> **IMPORTANT NOTE**: In order to run the server, run `mvn package` in your terminal then `./run` (using Git Bash for Windows users). This will be the same as the first Sprint. Take notice when transferring this run sprint to your Sprint 2 implementation that the path of your Server class matches the path specified in the run script. Currently, it is set to execute Server at `edu/brown/cs/student/main/server/Server`. Running through terminal will save a lot of computer resources (IntelliJ is pretty intensive!) in future sprints.

# Project Details
### loadcsv
The loadcsv class is a handler class. The class takes in a file path as a parameter and returns
success if the file is accessible. An error message is returned if parameters are missing, the file 
is inaccessible, or some other error occurred. Upon success, the file is parsed using the CSVParser 
class. The parsed data is then stored in the  ParsedData class so that it can be passed to both 
viewcsv and searchcsv via dependency injection.

### viewcsv
The viewcsv class is a handler class. The class takes in an instance of the ParsedData class as a 
parameter. On success, all parsed data is returned via a response map. An error message is returned 
if parameters are missing, no file has been loaded, or some other error occurs.

### searchcsv
The searchcsv class is a handler class. The class takes in an instance of the ParsedData class as a
parameter. On success, all parsed data, the parameters, and the rows containing the provided search 
value are returned. An error message is returned if parameters are missing, no file has been loaded,
or some other error occurs.

### ParsedData
The ParsedData class stores the data that is parsed via the CSVParser. 

# Design Choices

# Errors/Bugs
Bugs:None 
Injuries: None

# Tests

# How to
1. Select green play button (top right corner)
2. Open up a browser and specify the action that you would like performed.
