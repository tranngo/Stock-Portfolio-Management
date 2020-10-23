Feature: View and compare the historical performance of a stock
  Scenario: I see a line chart that displays the value of the user's portfolio over time
    Given I am on the home page
    Then I should see a line chart that displays the value of the user's portfolio over time
    
  Scenario: I see buttons to select the from date and to date
  	Given I am on the home page
  	Then I should see buttons to select the from date and to date
  	
  Scenario: I see a button to add an external stock on the home page
	Given I am on the login page
	When I log in
	Then I should see a button to add an external stock on the home page
	    
  Scenario: I see a button to remove an external stock on the home page
	Given I am on the login page
	When I log in
	Then I should see a button to remove an external stock on the home page
	
  Scenario: I see a button to toggle S&P
	Given I am on the login page
	When I log in
	Then I should see a button to toggle S&P on the home page
	
  Scenario: I should see a list of external stocks I added
  	Given I am on the login page
	When I log in
	Then I should see a list of external stocks I added
	
  Scenario: I should see the external stock when I add "TSLA" external stock
  	Given I am on the login page
	When I log in
	And I click on the add external stock button
	And I enter "TSLA" into the add external stock name field
	And I click on the view stock button
	Then I should see "TSLA" in my list of external stocks
	
  Scenario: I should no longer see the external stock when I remove "TSLA" external stock
  	Given I am on the login page
	When I log in
	And I click on the remove external stock button
	And I enter "TSLA" into the remove external stock name field
	And I click on the remove stock button
	Then I shouldn't see "TSLA" in my list of external stocks