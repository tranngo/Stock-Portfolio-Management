Feature: Users must be able to create an account in the system (register)
  Scenario: Open login page from registration page via successful registration
    Given I am on the registration page
    When I type "usr" into the username text box
    And I type "pass" into the password text box
    And I type "pass" into the confirm password text box
    And I click on the create account button
    Then I should be on the registration servlet page
    
  Scenario: Open login page from registration page via login button
    Given I am on the registration page
    When I click on the already registered login button
    Then I should be on the login page
    
  Scenario: Stay on page when giving empty username
    Given I am on the registration page
    When I type "pass" into the password text box
    And I type "pass" into the confirm password text box
    And I click on the create account button
    Then I should be on the registration page
    
  Scenario: Stay on page when giving empty password
    Given I am on the registration page
    When I type "usr" into the username text box
    And I type "pass" into the confirm password text box
    And I click on the create account button
    Then I should be on the registration page
    
  Scenario: Stay on page when giving empty confirm password
    Given I am on the registration page
    When I type "usr" into the username text box
    And I type "pass" into the password text box
    And I click on the create account button
    Then I should be on the registration page