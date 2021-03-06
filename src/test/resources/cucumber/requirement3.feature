Feature: Track and visualize changes in value over time of user’s portfolio
  Scenario: I see a line chart that displays the value of the user's portfolio over time
    Given I am on the login page
	When I log in
    Then I should see a line chart that displays the value of the user's portfolio over time
   
  Scenario: I see the value of the portfolio in dollars
  	Given I am on the login page
	When I log in
  	Then I should see the portfolio value
  	
  Scenario: The portfolio shows the change since the day before
  	Given I am on the login page
	When I log in
  	Then I should see the change in portfolio value since the previous day
  	
  Scenario: The portfolio should show an arrow indicating the direction of change
  	Given I am on the login page
	When I log in
  	Then I should see a corresponding arrow next to the portfolio change
  	
  Scenario: The portfolio change should show red for loss and green for growth
  	Given I am on the login page
	When I log in
  	Then the portfolio change and corresponding arrow should display the proper color
    
  Scenario: I see buttons to select the from date and to date
  	Given I am on the login page
	When I log in
  	Then I should see buttons to select the from date and to date
    
  Scenario: Graph should redraw once a custom start and end date are entered
  	Given I am on the login page
	When I log in
  	And I enter "06012020" into the from date
  	And I enter "11112020" into the to date
  	And I click the show me button
  	Then a graph should be displayed
  	
  Scenario: Graph should redraw if the start date changes
  	Given I am on the login page
	When I log in
  	And I enter "06012020" into the from date
  	And I enter "11112020" into the to date
  	And I click the show me button
  	And I enter "05012020" into the from date
  	And I click the show me button
  	Then the graph should update to account for the new start date
  	
  Scenario: Graph should redraw if the end date changes
  	Given I am on the login page
	When I log in
  	And I enter "06012020" into the from date
  	And I enter "11112020" into the to date
  	And I click the show me button
  	And I enter "12122020" into the to date
  	And I click the show me button
  	Then the graph should update to account for the new end date
  	
  Scenario: Graph should redraw if a stock is added
  	Given I am on the login page
	When I log in
  	And I click on the add to portfolio button
  	And I enter "TSLA" into the stock name input field
  	And I enter "2" into the stock quantity input field
  	And I enter "07022020" into the stock purchase date input field
  	And I enter "07042020" into the stock sell date input field
  	And I click on the add stock button
  	Then the graph should update to account for the new stock
  	
  Scenario: Error should be displayed if dates are not selected
  	Given I am on the login page
  	When I log in
  	And I click the show me button
  	Then an error should display asking to enter valid start and end dates
  	
  Scenario: Error should be displayed if start date is over one year ago 
  	Given I am on the login page
  	When I log in
  	And I enter "06012017" into the from date
  	And I enter "11112020" into the to date
  	And I click the show me button
  	Then an error should display requesting a start date within the last year
  	
  Scenario: Error should be displayed if start date is over one year ago 
  	Given I am on the login page
  	When I log in
  	And I enter "11012020" into the from date
  	And I enter "10012020" into the to date
  	And I click the show me button
  	Then an error should display requesting a start date before the end date