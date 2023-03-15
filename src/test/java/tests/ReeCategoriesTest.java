package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReeCategoriesTest {
    static final String API_KEY = "live_HgOK0tilfFf4nopnDCXWhErGJNt1ufZTJKjAXJySkDf4PgirlRK8aFGLBgWot3iw";
    static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri("https://api.thedogapi.com")
            .build();
    @Test
    public void getCategoriesTest() {
        Response response = RestAssured.given(SPECIFICATION)
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .when()
                .get("/v1/categories");

        assertEquals(200, response.getStatusCode());

        List<String> categories = response.jsonPath().getList("name");
        assertTrue(categories.size() > 0);
        System.out.println("Categories:");
        categories.forEach(System.out::println);
    }


}
