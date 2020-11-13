Feature: The application must be secure and protect confidentiality of users’ data.  
  Scenario: I should be able to securely log out
    Given I am on the login page
    When I log in
    And I click on the log out button
    Then I should be on the login page
  
  Scenario: Only logged in users should be able access the home page
    Given I am on the login page
    When I directly navigate to the home page
    Then I should be on the login page
    
  Scenario: The password should be hidden when you register
    Given I am on the registration page
    When I type "usr" into the username text box
    And I type "pass" into the password text box
    And I type "pass" into the confirm password text box
    Then my password in the register box should be hidden
    
  Scenario: The password should be hidden when you log in
  	Given I am on the login page
    When I type "pass" into the login password text box
    Then my password in the login box should be hidden
    
  Scenario: After three invalid login attempts, user should be locked out for one minute
  	Given I am on the login page
  	When I type "usr" into the login username text box
  	And I type "pass" into the login password text box
  	And I click on the login button
  	And I wait 3 seconds
  	And I type "usr" into the login username text box
  	And I type "pass" into the login password text box
  	And I click on the login button
  	And I wait 3 seconds
  	And I type "usr" into the login username text box
  	And I type "pass" into the login password text box
  	And I click on the login button
  	And I wait 3 seconds
  	Then I should see the login button is disabled
  	
    
  Scenario: After 120 seconds, the user should get a timeout warning
    Given I am on the login page
    When I log in
    And I wait 123 seconds 
    Then I should see a timeout warning popup