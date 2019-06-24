Feature: TestLoginName
	If a user is logged in, they should be greeted by first name which should be displayed on the navigation bar
	and the top location page.

@mytag
Scenario: Checking for Logged in First Name to Appear
	Given I have clicked on log in
	And I have entered my login creditials
	When I move to the Top Locations Page
	Then the result should show my name as a greating on the page
