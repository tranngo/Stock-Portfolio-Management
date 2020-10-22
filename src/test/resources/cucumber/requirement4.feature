Feature: View and compare the historical performance of a stock
  Scenario: I see a line chart that displays the value of the user's portfolio over time
    Given I am on the home page
    Then I should see a line chart that displays the value of the user's portfolio over time
    
  Scenario: I see buttons to select the from date and to date
  	Given I am on the home page
  	Then I should see buttons to select the from date and to date
