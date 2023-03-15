package tests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReeBreedsTest {

    static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";
    static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri("https://api.thedogapi.com")
            .build();
    @Test
    public void getBreedsTest() {
        // Send a GET request to retrieve breeds
        Response response = RestAssured.given(SPECIFICATION)
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .param("limit", 10)
                .param("page", 0)
                .when()
                .get("/v1/breeds");

        // Assert the status code and content type
        response.then()
                .statusCode(200)
                .log().all()
                .contentType("application/json");

        // Assert that the response contains 10 breeds
        List<String> breeds = response.jsonPath().getList("$");
        assertEquals(10, breeds.size());

    }
    @Test
    public void testGetBreedById() {
        int breedId = 1; // set the ID of the breed you want to retrieve

        Response response = RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .when()
                .get("/v1/breeds/{id}", breedId);

        assertEquals(200, response.getStatusCode());

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Parse the JSON response to extract breed information
        String name = JsonPath.parse(responseBody).read("$.name");
        String temperament = JsonPath.parse(responseBody).read("$.temperament");
        String bredFor = JsonPath.parse(responseBody).read("$.bred_for");

        // Verify that the retrieved breed matches the expected values
        assertEquals("Affenpinscher", name);
        assertEquals("Stubborn, Curious, Playful, Adventurous, Active, Fun-loving", temperament);
        assertEquals("Small rodent hunting, lapdog", bredFor);
    }

}
