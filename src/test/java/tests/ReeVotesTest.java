package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class ReeVotesTest {
    static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";
    static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri("https://api.thedogapi.com")
            .build();
    static final ResponseSpecification SUCCESS = new ResponseSpecBuilder()
            .expectStatusCode(200).build();
    static int id;

    @BeforeEach
    public void setUp() {
        String requestBody = "{\"image_id\": \"pisces\", \"value\": 13}";

        Response getResponse = given(SPECIFICATION)
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .post("/v1/votes");

        getResponse.then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("SUCCESS"));

        id = getResponse.jsonPath().getInt("id");
    }

    @Test
    public void postVoteTest() {
        String requestBody = "{\"image_id\": \"pisces\", \"value\": 13}";

        Response getResponse = given(SPECIFICATION)
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .post("/v1/votes");

        getResponse.then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("SUCCESS"));

        int id = getResponse.jsonPath().getInt("id");
        System.out.println("Id fetched"  + id);


    }

    @Test
    public void getCreatedDogTest() {
        Response response = RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .when()
                .get("/v1/votes/{id}", id);

        assertEquals(200, response.getStatusCode());
        String responseBody = response.getBody().asString();
        System.out.println("created dog fetched!!");
        System.out.println("\n");
        System.out.println(responseBody);

    }


      @Test
        public void voteDownCreatedDogTest() {
            // retrieve current votes count
            Response getResponse = RestAssured.given(SPECIFICATION)
                    .header("x-api-key", API_KEY)
                    .when()
                    .get("/v1/votes/{id}", id);

            assertEquals(200, getResponse.getStatusCode());
            String getResponseBody = getResponse.getBody().asString();
            int currentVotes = Integer.parseInt(JsonPath.from(getResponseBody).getString("votes"));

            // vote down the created ID
            int newVotes = currentVotes - 1;
            Response putResponse = RestAssured.given(SPECIFICATION)
                    .header("x-api-key", API_KEY)
                    .body("{\"votes\": " + newVotes + "}")
                    .when()
                    .put("/v1/votes/{id}", id);

            assertEquals(200, putResponse.getStatusCode());
            String putResponseBody = putResponse.getBody().asString();
            System.out.println("created dog vote down!!");
            System.out.println("\n");
            System.out.println(putResponseBody);
        }




        // Use the ID to get the corresponding dog
//        given(SPECIFICATION)
//                .header("x-api-key", API_KEY)
//                .when()
//                .get("/v1/images/{image_id}", "pisces") // Replace with the image ID used in the vote request
//                .then()
//                .statusCode(200)
//                .body("vote_id", hasItem("id"));

    }













