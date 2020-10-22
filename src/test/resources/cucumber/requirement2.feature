Feature: Ability to add or remove stocks from the portfolio
  Scenario: I see a button to add a stock to my portfolio
    Given I am on the login page
    When I log in
    Then I should see a button to add a stock to my portfolio on the home page
    
  Scenario: When I click on the add to portfolio button, I should see a modal with a confirm and cancel button
  	Given I am on the login page
    When I log in
    And I click on the add to portfolio button
    Then I should see a modal with a confirm and cancel button
    
  Scenario: Add to portfolio modal contains field for stock name, stock quantity, purchase date, and sell date
  	Given I am on the login page
    When I log in
    And I click on the add to portfolio button
    Then I should see a field for stock name, stock quantity, purchase date, and sell date
    
  Scenario: I see a button to upload a stock to my portfolio
  	Given I am on the login page
    When I log in
    Then I should see a button to upload a stock to my portfolio on the home page
    
  Scenario: When I click on the upload to portfolio button, I should see a modal with a confirm and cancel button
  	Given I am on the login page
  	When I log in
  	And I click on the upload to portfolio button
  	Then I should see a modal with a confirm and cancel button
  	
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
  	And I click on the confirm button
  	Then I should see "TSLA" under my list of stocks in my portfolio
  	
  Scenario: I upload a stock to my portfolio
  	Given I am on the login page
  	When I log in
  	And I click on the upload to portfolio button
  	And I upload a CSV file with a TSLA stock
  	And I click on the confirm button
  	Then I should see "TSLA" under my list of stocks in my portfolio
    
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