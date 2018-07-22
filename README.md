# Project Title

This is a demo application for parsing a huge CSV file with taking into consideration the memory size. To parse the file you have to 
post the file path and run mode in a JSON object. I block the user until the parsing finishes, this is for demo only in real application I start the process and returns direct to the user with the process id where he can check letter the status of the operation, or this operation could be a cron job.

The application splits the data into small batches where the user can control the batch size.

The parsing has two modes the sequential mode and parallel mode. The parallel mode is faster than the sequential but it swallows the resources in case of you are trying to insert the file data into a database or posting to a remote server This option is not sufficient this option only for data manipulation.

 
## Getting Started

The project entry point is com.ahkamel.starter.Application.java class, you can run it to start the application. 

There is only one service:

```
Title : Parses CSV file and splits it to small batches of DTO object
URL : http://localhost:8080/parser/parse
Method : POST 
Data Params : {path: [string],  runMode:[string] }

| Parameter | Type     | Required/Optional| Description |
|:----------|:---------|:------------|----------------
|path       |string    |Required     | The path to the CSV file on the application running machine
|rumMode    |string    |Required     | Running patches in sequential or parallel mode values [sequential|parallel] 

Response Codes: Success (200 OK), Bad Request (400)
```

To run it from windows command line using curl tool.

```
curl http://localhost:8080/parser/parse -X POST -H "Content-Type:application/json" -d^
"{\"path\": \"D:\\work\\testdata.csv\",\"runMode\":\"sequential\"}"
```

## Prerequisites

Java 8 JDK

## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Junit](https://junit.org/junit4/) - Used for testing

## Authors
* **Ahmed Kamel** - 
