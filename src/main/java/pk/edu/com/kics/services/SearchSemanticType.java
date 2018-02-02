package pk.edu.com.kics.services;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import pk.edu.com.kics.util.RestTicketClient;
import pk.edu.com.kics.util.SearchResult;

/*This example allows you to search terms in the UMLS
Examples are at https://github.com/jayway/rest-assured/tree/master/examples/rest-assured-itest-java/src/test/java/com/jayway/restassured/itest/java
For convenience, google's quick json parser is also included in the pom.xml file:
https://code.google.com/p/quick-json/
You can run this class as a Junit4 test case - be sure and put each of the arguments as VM arguments
You may page through results returned from the /search endpoint until you reach the null  'ui:NONE' or 'name:NO RESULTS'.  These results will always be a single result on their own page.
in your runtime configuration, such as -Dapikey -Dterm = "diabetic foot"

*/

public class SearchSemanticType {

	String apiKey = "16dd79bf-31f9-4944-8f0b-d449548c6c09";
	String version;
	RestTicketClient ticketClient;
	String tgt;

	public SearchSemanticType(String term) throws Exception {
		version = System.getProperty("version");
		ticketClient = new RestTicketClient(apiKey);
		version = System.getProperty("version") == null ? "current" : System.getProperty("version");
		tgt = ticketClient.getTgt();
		//SearchCUI(term);
	}

	public void SearchCUI(String term) throws Exception {
		int total = 0;
		int pageNumber = 0;
		SearchResult[] results;
		do {
			pageNumber++;
			System.out.println("Fetching results for page " + pageNumber);
			RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
			Response response = given()// .log().all()
					.request().with().param("ticket", ticketClient.getST(tgt)).param("string", term)
					.param("pageNumber", pageNumber).param("searchType", "exact") // set
																					// different
																					// parameters
					.expect().statusCode(200).when().get("/rest/search/" + version);

			String output = response.getBody().asString();
			Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
			results = JsonPath.using(config).parse(output).read("$.result.results", SearchResult[].class);

			for (SearchResult result : results) {
				String ui = result.getUi();
				String name = result.getName();
				String rootSource = result.getRootSource();
				String uri = result.getUri();
				System.out.println("ui: " + ui);
				// System.out.println("name: " + name);
				// System.out.println("rootSource: " + rootSource);
				// System.out.println("uri: " + uri);

				System.out.println("**");
			}

			total += results.length;
		} while (!results[0].getUi().equals("NONE"));
		// account for the one 'NO RESULTS' result :-/
		total--;
		System.out.println("Found " + total + " results for " + term);

	}

	public void getTerm(String cui) throws Exception {

		SearchResult[] results;

		RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
		Response response = given()// .log().all()
				.request().with().param("ticket", ticketClient.getST(tgt))

				.expect().statusCode(200).when().get("/rest/content/" + version + "/CUI/" + cui);

		String output = response.getBody().asString();
		Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
		results = JsonPath.using(config).parse(output).read("$.result.semanticTypes", SearchResult[].class);

		// the /search endpoint returns an array of 'result' objects
		// See
		// http://documentation.uts.nlm.nih.gov/rest/search/index.html#sample-output
		// for a complete list of fields output under the /search endpoint

		String name = results[0].getSemanticType();

		String uri = results[0].getUri();

		System.out.println("semantic type: " + name);
		// System.out.println("rootSource: " + rootSource);
		System.out.println("uri: " + uri);

	}

	public void getTerm(String[] cui) throws Exception {

		SearchResult[] results;

		RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
		Response response = given()// .log().all()
				.request().with().param("ticket", ticketClient.getST(tgt))

				.expect().statusCode(200).when().get("/rest/content/" + version + "/CUI/C0009044");

		String output = response.getBody().asString();
		Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
		results = JsonPath.using(config).parse(output).read("$.result.semanticTypes", SearchResult[].class);

		// the /search endpoint returns an array of 'result' objects
		// See
		// http://documentation.uts.nlm.nih.gov/rest/search/index.html#sample-output
		// for a complete list of fields output under the /search endpoint

		String name = results[0].getName();

		String uri = results[0].getUri();

		System.out.println("semantic type: " + name);
		// System.out.println("rootSource: " + rootSource);
		System.out.println("uri: " + uri);

	}

}
