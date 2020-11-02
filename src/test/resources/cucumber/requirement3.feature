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
	
  Scenario: The user does not choose to set a date range for the graph
    Given I am on the login page
	When I log in
  	And the buttons to select the dates are not filled in
    Then I should see the graph displaying data from one year ago to today
    
  Scenario: Graph should redraw once a custom start and end date are entered
  	Given I am on the login page
	When I log in
  	And I enter "07022020" into the from date
  	And I enter "07202020" into the to date
  	Then a graph should be displayed
  	
  Scenario: Graph should redraw if the start date changes
  	Given I am on the login page
	When I log in
  	And I change the start date
  	Then the graph should update to account for the new start date
  	
  Scenario: Graph should redraw if the end date changes
  	Given I am on the login page
	When I log in
  	And I change the end date
  	Then the graph should update to account for the new end date
  	
  Scenario: Graph should draw once a proper start and end date are entered
  	Given I am on the login page
	When I log in
  	And I enter "07022020" into the from date
  	And I enter "07202020" into the to date
  	Then a graph should be displayed
  	
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
  	
  