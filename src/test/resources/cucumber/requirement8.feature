Feature: User interfaces must look modern and be attractive
  Scenario: I see a banner across the top of the screen with "USC CS310 Stock Portfolio Management" text
    Given I am on the home page
    Then I should see a gray banner across the top with "USC CS310 Stock Portfolio Management" text
    
  Scenario: I see a banner across the top of the screen with the color #787878
  	Given I am on the home page
  	Then I should see a gray banner across the top with the color #787878
    