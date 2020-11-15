# Stock Portfolio Management

![image](https://user-images.githubusercontent.com/31317867/99146809-35ede180-2630-11eb-87d1-dab1e7405a0d.png)

Stock Portfolio Management is a responsive web application that helps users track the value of their stock portfolio over time and make investment decisions.

## Usage

Import this repository into Eclipse. This project provides everything needed to:

* Host the web application on a local web server
* Run unit tests with coverage
* Run acceptance tests

**To run JUnit tests:**

Right-click project -> Run As -> "Maven test"

**To generate coverage report for JUnit tests:**

Right-click "cobertura.launch" -> Run As -> "cobertura".

**To host your web application:**

Right-click "run.launch" -> Run As -> "run". It will be hosted on http://localhost:8081 (https://localhost:8080/ for HTTPS).

**To run Cucumber tests:**

Make sure the web server is running when you run the Cucumber tests. Right-click "cucumber.launch" -> Run As -> "cucumber".

## Screenshots

### Home

![image](https://user-images.githubusercontent.com/31317867/99160700-c400b100-269e-11eb-992e-8d8a03a01d9f.png)
**Figure 1. *Home page*. Users can add a stock transaction to their portfolio by clicking on the "Add" button on the left side or uploading a CSV file by clicking on the "Upload" button. Users can also add or remove an external stock by clicking on the "Add" or "Remove" buttons respectively.**

### Add stock transaction to portfolio

![image](https://user-images.githubusercontent.com/31317867/99160950-c284b800-26a1-11eb-9322-dc0b42bcb330.png)
**Figure 2. *Add stock transaction modal*. After pressing on the "Add" button on the left side, users fill out information about their stock transaction to add it to their portfolio.**
