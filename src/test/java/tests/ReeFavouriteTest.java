package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import org.junit.runners.MethodSorters;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 public class ReeFavouriteTest {

     static final String BASE_URL = "https://api.thedogapi.com";
     static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";
     static final String SUB_ID = "Ghost123";
     static final String PARAM = "sub_id=" + SUB_ID;

    int favourite_id;

    @BeforeEach
    public void initiliseObjects(){
        //Running this test to set favourite_id value
        apostFavouriteBySubId();
    }


    @Test
    public void apostFavouriteBySubId() {
        //create a random IMAGE_ID
        Random random = new Random();
        String imageID = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(imageID.length());
            char randomChar = imageID.charAt(index);
            sb.append(randomChar);
        }
        String IMAGE_ID = sb.toString();
        // Create a favourite by subId
        JSONObject requestParams = new JSONObject();
        requestParams.put("image_id", IMAGE_ID);
        requestParams.put("sub_id", SUB_ID);

        // Send the POST request to create a favourite for the specified sub ID
        Response postResponse = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(requestParams.toString())
                .post(BASE_URL + "/v1/favourites");

        // Verify that the response code is 200 OK
        assertEquals(200, postResponse.getStatusCode());
        //Print response body
        String responseBody = postResponse.getBody().asString();
        if(postResponse.getStatusCode() == 200){
            System.out.println("Record Created!");
            System.out.println("\n");
        }
        System.out.println(responseBody);
        System.out.println("\n");

        this.favourite_id = postResponse.jsonPath().getInt("id");
        System.out.println("Created with id output: "+favourite_id);


    }
    @Test
    public void bgetFavouriteBySubId() {
        // Send a GET request to retrieve the favourite by sub ID
        Response getResponse = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .get(BASE_URL + "/v1/favourites?" + PARAM);

        // Verify that the response code is 200 OK
        assertEquals(200, getResponse.getStatusCode());
        String responseBody = getResponse.getBody().asString();
        System.out.println("Results fetched!");
        System.out.println("\n");
        System.out.println(responseBody);
    }

    @Test
    public void cdeleteFavouriteById() {
// Send the POST request to create a favourite for the specified sub ID
        System.out.println("Running delete with id: "+favourite_id);
        Response deleteResponse =
                given()
                        .when()
                        .contentType(ContentType.JSON)
                        .header("x-api-key", API_KEY)
                        .delete(BASE_URL + "/v1/favourites/"+favourite_id );
        deleteResponse.then().log().all();
//        String responseBody = deleteResponse.then().log().all();
        System.out.println("Item Deleted!");
//        System.out.println(responseBody);

    }

}
