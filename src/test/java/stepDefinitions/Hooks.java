package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	StepDefinition sp = new StepDefinition();
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// write code that will give place id
		//execute only place id is null
		if (StepDefinition.place_id==null) {
			sp.add_place_payload_with("Prabu", "Telugu", "Andhra");
			sp.user_calls_with_post_http_request("addPlaceAPI", "POST");
			sp.verify_place_id_created_maps_to_using("Prabu", "getPlaceAPI");	
		}
		
	}

}
