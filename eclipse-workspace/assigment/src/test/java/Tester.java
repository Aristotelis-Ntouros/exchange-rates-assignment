import io.restassured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class Tester {

	@Test 
	public void testSize() {		
				
		String url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json";
		
		RestAssured.given()
			.urlEncodingEnabled(false)
			.when()
			.get(url)
		.then().statusCode(200).body("keySet().size()", Matchers.greaterThan(20));
	}
	
	@Test
	public void testCurrency() {
		
		String url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json";

		RestAssured.given()
		.urlEncodingEnabled(false)
		.when()
		.get(url)
		.then().statusCode(200).body("keySet()", Matchers.hasItems("usd", "gbp"));
	}
	
	@Test
	public void testExchange() {
		
		RestAssured.baseURI = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json";

		RequestSpecification httpRequest = RestAssured.given().urlEncodingEnabled(false);
		Response response = httpRequest.get("");		
				
		JsonPath jsonPathEvaluator = response.jsonPath();
		
		List<Object> a = Arrays.asList(jsonPathEvaluator.getMap("").keySet().toArray());
		
		ArrayList<Object> arraylist = new ArrayList<Object>(a);
		
		ArrayList<Integer> exchange_rate_pairs_count = new ArrayList<Integer>();
		
		for (Object object : arraylist) {
			
			RestAssured.baseURI = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"+ object.toString() + ".json";
			
			httpRequest = RestAssured.given().urlEncodingEnabled(false);
			response = httpRequest.get("");		
			
			jsonPathEvaluator = response.jsonPath();
						
			Object[] currencies = jsonPathEvaluator.getMap(object.toString()).keySet().toArray();
			
			exchange_rate_pairs_count.add(currencies.length);
			
		}
		
		Integer base = exchange_rate_pairs_count.get(0);
		
		for (Integer rate : exchange_rate_pairs_count) {			
			
			assert base.equals(rate);
			
		}
				
				
		
	}
	
}
