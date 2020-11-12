Feature: View and compare the historical performance of a stock
  Scenario: I see a line chart that displays the value of the user's portfolio over time
    Given I am on the login page
	When I log in
    Then I should see a line chart that displays the value of the user's portfolio over time
    
  Scenario: I see buttons to select the from date and to date
  	Given I am on the login page
	When I log in
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
	
  Scenario: I should see the external stock when I add any external stock
  	Given I am on the login page
	When I log in
	And I click on the add external stock button
	And I enter "TSLA" into the add external stock name field
	And I click on the view stock button
	Then I should see "TSLA" in my list of external stocks
	
  Scenario: I should no longer see the external stock when I remove any external stock
  	Given I am on the login page
	When I log in
	And I click on the remove external stock button
	And I enter "TSLA" into the remove external stock name field
	And I click on the remove stock button
	Then I shouldn't see "TSLA" in my list of external stocks
	
  Scenario: Adding a stock to the external stocks should show a confirmation dialogue
  	Given I am on the login page
  	When I log in
	And I click on the add external stock button
	And I enter "TSLA" into the add external stock name field
	And I click on the view stock button
  	Then I should see a confirmation dialogue

  Scenario: Removing a stock from external stocks should show a confirmation dialogue
  	Given I am on the login page
  	When I log in
  	And I click on the remove external stock button
	And I enter "TSLA" into the remove external stock name field
	And I click on the remove stock button
  	Then I should see a confirmation dialogue
  	
  Scenario: Graph should draw once a proper start and end date are entered
  	Given I am on the login page
  	When I log in
  	And I enter "07022020" into the from date
  	And I enter "07202020" into the to date
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
  	
  Scenario: Clicking the S&P button should toggle a visual line
  	Given I am on the login page
  	When I log in
  	And I click the S&P button
  	Then a visual line should be toggled
  	
  Scenario: Multiple external stocks on the graph should have different line colors
  	Given I am on the login page
  	When I log in
  	And there are multiple external stocks on the graph
  	Then each external stock line should be a different color