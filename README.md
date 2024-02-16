# Project Details

### LoadCSVHandler
The LoadCSVHandler class is a handler class. The class takes in a file path as a parameter and returns
success if the file is accessible. An error message is returned if parameters are missing, the file
is inaccessible, or some other error occurred. Upon success, the file is parsed using the CSVParser
class. The parsed data is then stored in the  ParsedData class so that it can be passed to both
ViewCSVHandler and SearchCSVHandler via dependency injection.

### ViewCSVHandler
The ViewCSVHandler class is a handler class. The class takes in an instance of the ParsedData class as a
parameter. On success, all parsed data is returned via a response map. An error message is returned
if parameters are missing, no file has been loaded, or some other error occurs.

### SearchCSVHandler
The SearchCSVHandler class is a handler class. The class takes in an instance of the ParsedData class as a
parameter. On success, all parsed data, the parameters, and the rows containing the provided search
value are returned. An error message is returned if parameters are missing, no file has been loaded,
or some other error occurs.

### ParsedData
The ParsedData class stores the data that is parsed via the CSVParser.

### Serializer
The Serializer class serializes data into a JSON object.

### BroadbandAPI
The BroadbandAPI class connects to the ACS API and gets the broadband access percentage based on
state and county name. It ensures that the state name and county name are valid and throws errors
accordingly. It saves the state code and searches for county codes based on inputted state code.

### BroadbandData
This record holds data about broadband access percentage.

### DatasourceException
This class throws exceptions for bad data. It is the default exception used when handling ACS API 
data.

# Design Choices
For design choices, we followed the outline in the Feb 8 livecode. The functionality of ACS data and
CSV data are separate since they are separate data.

# Errors/Bugs
We have issues with handing our states with CSV data.

# Tests
We have not written any tests.

# How to
Click the run button in the Server class.
