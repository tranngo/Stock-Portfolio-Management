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
**Figure 1.** *Home page*. Users can add a stock transaction to their portfolio by clicking on the "Add" button on the left side or uploading a CSV file by clicking on the "Upload" button. Users can also add or remove an external stock by clicking on the "Add" or "Remove" buttons on the right side respectively. The buttons below the chart can be used to view stock information within a date range.

### Add Stock Transaction to Portfolio

![image](https://user-images.githubusercontent.com/31317867/99160950-c284b800-26a1-11eb-9322-dc0b42bcb330.png)
**Figure 2.** *Add stock transaction modal*. After pressing on the "Add" button on the left side, users fill out information about their stock transaction to add it to their portfolio.

![image](https://user-images.githubusercontent.com/31317867/99161169-1bede680-26a4-11eb-8508-21cb493f8c87.png)
**Figure 3.** *Add stock transaction confirmation message*.

### Upload Stock Transactions to Portfolio

![image](https://user-images.githubusercontent.com/31317867/99161253-3a081680-26a5-11eb-9b62-3a89047b2c17.png)
**Figure 4.** *Upload stock transaction modal*. Users can add multiple stock transactions to their portfolio by uploading a CSV file.

### Add External Stock

![image](https://user-images.githubusercontent.com/31317867/99161305-c31f4d80-26a5-11eb-9bea-27a3364098da.png)
**Figure 5.** *Add external stock*. Users can add an external stock to the chart by pressing on the "Add" button on the right side. External stocks do not affect the portfolio and are displayed on the chart as separate lines.

![image](https://user-images.githubusercontent.com/31317867/99161343-42ad1c80-26a6-11eb-98db-6b7305841247.png)
**Figure 6.** *Add external stock confirmation message*.

### Remove External Stock

![image](https://user-images.githubusercontent.com/31317867/99161380-a1729600-26a6-11eb-9a28-7a829b1a8afd.png)
**Figure 7.** *Remove external stock*. Users can remove an external stock from the chart by pressing on the "Remove" button on the right side.

![image](https://user-images.githubusercontent.com/31317867/99161415-f3b3b700-26a6-11eb-8d99-eef3fcfa4130.png)
**Figure 8.** *Remove external stock confirmation message*.
