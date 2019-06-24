Feature: TestAiport
	If a user selects a different country and main city to fly to, they should be presented with the 
	main airport option for said location.

	@scopedBinding
	Scenario: Checking for accurate drop down options
	Given I have navigated to the ReadyGOTravel website
	And I have moved to the search page
	When I select a different country and city of said country
	Then I should be presented with the main airport for that city
