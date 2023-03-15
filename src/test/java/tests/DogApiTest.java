package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DogApiTest {

    public static final String BASE_URL = "https://api.thedogapi.com/v1";
    public static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";

    @Test
    //post action
    public void createFavouriteBySubId() {
        // Specify the sub ID of the dog you want to create a favourite for
        String subId = "Ghost123";

        // Create a JSON object with the required request parameters
        JSONObject requestParams = new JSONObject();
        requestParams.put("image_id", "BossFav");
        requestParams.put("sub_id", subId);

        // Send the POST request to create a favourite for the specified sub ID
        Response response = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(requestParams.toString())
                .post(BASE_URL + "v1/favourites");

        // Assert that the response has a 200 OK status code
        response.then().statusCode(200);

        System.out.println("Favourite created with sub ID: " + (subId));
    }
//        @Test
//        public void createFavouriteWithoutSubId() {
//            // Specify the sub ID of the dog you want to create a favourite for
//            String subId = "Ghost123";
//
//            // Create a JSON object with the required request parameters
//            String imageId = "BossFav";
//            String requestParams = "{\"image_id\": \"" + imageId + "\", \"sub_id\": \"" + subId + "\"}";
//
//            // Send the POST request to create a favourite for the specified sub ID
//            Response response = given()
//                    .contentType(ContentType.JSON)
//                    .header("x-api-key", API_KEY)
//                    .body(requestParams)
//                    .post(BASE_URL + "/favourites");
//
//            // Assert that the response has a 200 OK status code
//            response.then().statusCode(400);
//
//            // Get the ID of the favourite created in the response
//            String favouriteId = response.jsonPath().getString("id");
//
//            // Send a GET request to retrieve the favourite by ID
//            Response getResponse = given()
//                    .contentType(ContentType.JSON)
//                    .header("x-api-key", API_KEY)
//                    .get(BASE_URL + "/favourites/" + favouriteId);
//
//            // Assert that the response has a 200 OK status code
//            getResponse.then().statusCode(400);
//
//            // Assert that the retrieved favourite has the same image ID and sub ID as the one created
//            String retrievedImageId = getResponse.jsonPath().getString("image_id");
//            String retrievedSubId = getResponse.jsonPath().getString("sub_id");
//            assertEquals(imageId, retrievedImageId);
//            assertEquals(subId, retrievedSubId);
//
//            System.out.println("Favourite created with ID: " + favouriteId);
//        }
 }
//
//
//
