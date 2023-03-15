package tests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReeVotesTest {
    static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";
    static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri("https://api.thedogapi.com")
            .build();
    static final ResponseSpecification SUCCESS = new ResponseSpecBuilder()
            .expectStatusCode(200).build();
    static int id;
    String imageid;
    String subid;
    String value_;

    @BeforeEach
    public void setUp() {
        String requestBody = "{\"image_id\": \"pisces\", \"value\": 13, \"sub_id\": \"my-user-1234\"}";
        subid = "my-user-1234";

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
        System.out.println("Id fetched  " + id);
    }

    @Test
    public void getCreatedDogTest() {
        Response response = RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .when()
                .get("/v1/votes/{id}", id);

        assertEquals(200, response.getStatusCode());
        String responseBody = response.getBody().asString();
        System.out.println("created dog fetched!! ");
        System.out.println("\n");
        System.out.println(responseBody);

        this.imageid = response.jsonPath().getString("image_id");
        this.value_ = response.jsonPath().getString("value");

    }

    @Test
    public void voteDownCreatedDogTest() {
        // Vote down the created ID without using the current votes count
        String requestBody = "{\"image_id\": \"" + imageid + "\", \"sub_id\": \""+ subid + "\" ,\"value\": " +value_+"}";

        Response putResponse = RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .put("/v1/votes/{vote}", id); // provide value for the `vote` parameter here

        putResponse.then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("SUCCESS"));

        String putResponseBody = putResponse.getBody().asString();
        System.out.println("Created dog vote down!!");
        System.out.println("\n");
        System.out.println(putResponseBody);
    }


    @Test
    public void EvoteUpCreatedDogTest() {
        // Vote up the created ID without using the current votes count
        int newVotes = 1; // set the new votes count to a value of your choice
        Response putResponse =RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .body("{\"votes\": " + newVotes + "}")
                .when()
                .put();
        putResponse.then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("SUCCESS"));

        String putResponseBody = putResponse.getBody().asString();
        System.out.println("Created dog vote up!!");
        System.out.println("\n");
        System.out.println(putResponseBody);
    }
    @Test
    public void FdeleteVoteForCreatedDogTest() {
        // Delete the vote for the created ID
        Response deleteResponse = RestAssured.given(SPECIFICATION)
                .header("x-api-key", API_KEY)
                .when()
                .delete("/v1/votes/{id}", id);

        deleteResponse.then()
                .log().all()
                .statusCode(204)
                .body("message", equalTo("SUCCESS"));


        System.out.println("vote down deleted for created Id");
        System.out.println(id);
        System.out.println("\n");
    }

//    @Test
//    public void getCurrentVotesForCreatedDogTest() {
//        // Retrieve the current votes count for the created ID
//        Response response = RestAssured.given(SPECIFICATION)
//                .header("x-api-key", API_KEY)
//                .when()
//                .get("/v1/votes/{id}", id);
//
//        assertEquals(200, response.getStatusCode());
//        String responseBody = response.getBody().asString();
//        int currentVotes = JsonPath.parse(responseBody).read("$.votes");
//        System.out.println("Current votes for created dog: " + currentVotes);
//        assertTrue(currentVotes >= 0);
//    }






}















