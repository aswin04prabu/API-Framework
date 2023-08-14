Feature: Validating Place APIs

@AddPlace @Regression
Scenario Outline: Verify if place is being successfully add using AddPlaceAPI
	Given Add Place Payload with "<name>", "<language>", "<address>"
	When user calls "addPlaceAPI" with "Post" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
Examples:
		| name   |language |address            |
		|A house |English  |World Cross center |
		|B house |Tamil    |Sea Cross center   |

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace Payload
	When user calls "deletePlaceAPI" with "Post" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"