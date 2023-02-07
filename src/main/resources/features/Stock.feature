Feature: Stock Details
  #I want to use this template for my feature file

Background: Webdriver is launch before execution
	Given Webdriver set up and launching 
	
	

  @Smoke
  Scenario: Fetch details for few stocks
    Given Fetch records from excel 
    When Get latest data
    Then print details 
    And close broser 
    
 