Feature: Works on the Chrome web browser and mobile devices
  Scenario: Checking registration page on mobile
    Given I am on login page on mobile
    When I click on sign up button
    Then I should see mobile register page

  Scenario: Checking login page on mobile
    Given I am on registration page on mobile
    When I click on cancel
    Then I should see mobile login page
  
  Scenario: Checking home page on mobile
    Given I am on the login page on mobile
    When I login
    Then I should see mobile home page