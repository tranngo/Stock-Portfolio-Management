Feature: Ability to add or remove stocks from the portfolio
  Scenario: I see a button to add a stock to my portfolio
    Given I am on the login page
    When I log in
    Then I should see a button to add a stock to my portfolio on the home page
    
  Scenario: When I click on the add to portfolio button, I should see a modal with an add stock and cancel button
  	Given I am on the login page
    When I log in
    And I click on the add to portfolio button
    Then I should see a modal with an add stock and cancel button
    
  Scenario: Add to portfolio modal contains field for stock name, stock quantity, purchase date, and sell date
  	Given I am on the login page
    When I log in
    And I click on the add to portfolio button
    Then I should see a field for stock name, stock quantity, purchase date, and sell date
    
  Scenario: I see a button to upload a stock to my portfolio
  	Given I am on the login page
    When I log in
    Then I should see a button to upload a stock to my portfolio on the home page
    
  Scenario: When I click on the upload to portfolio button, I should see a modal that allows for a file upload
  	Given I am on the login page
  	When I log in
  	And I click on the upload to portfolio button
  	Then I should see a modal with an add stock and cancel button
  	
  Scenario: Upload to portfolio modal contains input to upload a file
  	Given I am on the login page
    When I log in
    And I click on the upload to portfolio button
    Then I should see an input to upload a file
    
  Scenario: I add a stock to my portfolio
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07022020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see "TSLA" under my list of stocks in my portfolio
  	
  Scenario: Adding a stock to the portfolio should show a confirmation dialogue
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07022020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see a confirmation dialogue
  	
  Scenario: I upload a stock to my portfolio
  	Given I am on the login page
  	When I log in
  	And I click on the upload to portfolio button
  	And I upload a CSV file with a TSLA stock
  	And I click on the add stock button
  	Then I should see "TSLA" under my list of stocks in my portfolio
  	
  Scenario: Uploading a stock to the portfolio should show a confirmation dialogue
  	Given I am on the login page
  	When I log in
  	And I click on the upload to portfolio button
  	And I upload a CSV file with a TSLA stock
  	And I click on the add stock button
  	Then I should see a confirmation dialogue
    
  Scenario: If I have one or more stocks in my portfolio, then I should see a button to remove a stock from my portfolio
  	Given I am on the login page
  	When I log in
  	And I have one or more stocks in my portfolio
  	Then I should see a button to remove a stock from my portfolio
  	
  Scenario: I remove a stock from my portfolio
  	Given I am on the login page
  	When I log in
  	And I have "TSLA" stock in my portfolio
  	And I press on the remove stock button
  	Then I should no longer see "TSLA" in my portfolio
  	
  Scenario: Removing a stock from my portfolio should show a confirmation dialogue
  	Given I am on the login page
  	When I log in
  	And I have "TSLA" stock in my portfolio
  	And I press on the remove stock button
  	Then I should see a confirmation dialogue
  	
  Scenario: Entering an invalid date should display an error
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07332020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see an invalid date error
  	
  Scenario: Entering a negative stock quantity should display an error
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "-2" into the stock quantity input field
  	And I enter "07022020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see an invalid stock quantity error
  	
  Scenario: Entering an invalid ticker symbol should display an error
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "INVALID" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07022020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see an invalid ticker error
  	
  Scenario: Entering a start date after an end date should display an error
  	Given I am on the login page
  	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07042020" into the stock purchase date input field
  	And I enter "07022020" into the stock sell date input field
  	And I click on the add stock button
  	Then I should see a start date comes after end date error