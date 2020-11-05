Feature: Works on the Chrome web browser and mobile devices
  Scenario: Seeing mobile registration page
    Given I am on login page on mobile
    When I click on sign up button
    Then I should see mobile register page