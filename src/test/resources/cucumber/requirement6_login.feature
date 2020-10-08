Feature: Login
  Scenario: Open register page from login page via sign up button
    Given I am on the login page
    When I click on the sign up button
    Then I should be on the register page

  Scenario: Stay on page when giving empty username
    Given I am on the login page
    When I type "pass" into the login password text box
    And I click on the login button
    Then I should be on the login page
    
  Scenario: Stay on page when giving empty password
    Given I am on the login page
    When I type "usr" into the login username text box
    And I click on the login button
    Then I should be on the login page
    
   Scenario: Stay on page when giving empty username and empty password
    Given I am on the login page
    And I click on the login button
    Then I should be on the login page
    
  Scenario: Display please enter a username message when no username is specified
  	Given I am on the login page
  	When I type "pass" into the login password text box
  	And I click on the login button
  	Then I should see please enter a username error message
  	
           	
  Scenario: Display please enter your password message when no password is specified
  	Given I am on the login page
  	When I type "usr" into the login username text box
  	And I click on the login button
  	Then I should see please enter your password error message
  	
  Scenario: Display please enter a username and please enter your password message when neither username nor password is specified
  	Given I am on the login page
  	When I click on the login button
  	Then I should see please enter a username and please enter your password error messages