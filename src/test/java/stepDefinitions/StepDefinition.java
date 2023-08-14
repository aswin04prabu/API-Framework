package stepDefinitions;

import static io.restassured.RestAssured.given;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;

import static org.junit.Assert.*;

import java.io.IOException;

public class StepDefinition extends Utils{
	
	ResponseSpecification respSpec;
	RequestSpecification res;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	//	#### NO Data Driven ####
//	@Given("Add Place Payload")
//	public void add_place_payload() throws IOException {
//		
//		respSpec = new ResponseSpecBuilder()
//				.expectStatusCode(200)
//				.expectContentType(ContentType.JSON)
//				.build();
//		
//		res = given().spec(requestSpecification())
//		.body(data.addPlacePayload());
//	}
	
	//Data Driven
	
	@Given("Add Place Payload with {string}, {string}, {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		
		respSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		
		res = given().spec(requestSpecification())
			.body(data.addPlacePayload(name, language, address));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource,String method) {
		
		//Constructor will be called with valueOf resource which we pass
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
//		response = res.when().post("maps/api/place/add/json")
//				.then().assertThat().spec(respSpec).extract().response();
		
		if (method.equalsIgnoreCase("Post"))
			response = res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
		
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String expKeyValue, String expValue) {
	    
	    assertEquals(getJsonPath(response, expKeyValue), expValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expName, String resource) throws IOException {
	    
		place_id = getJsonPath(response,"place_id");
		
		//request Specification
		
		res = given().spec(requestSpecification())
				.queryParam("place_id", place_id);
		user_calls_with_post_http_request(resource, "GET");
		
		String actName = getJsonPath(response,"name");
		assertEquals(actName, expName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}
