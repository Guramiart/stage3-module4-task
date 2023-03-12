package com.mjc.school.controller.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthorTest {

    @BeforeAll
    public static void setup() {
        baseURI = "http://localhost:8081/api";
    }

    @Test
    public void basicPingTest() {
        given().when().get("/authors").then().statusCode(200);
    }

    @Test
    public void createTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("name", "Author-Test");
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())

                .when()
                .post("/authors")

                .then()
                .assertThat().statusCode(201)
                .body("name", equalTo("Author-Test"));
    }

    @Test
    public void readByIdTest() {
        Response empResponse = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .get("/authors/{id}")
                .then()

                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathObj = empResponse.jsonPath();
        Assertions.assertEquals(jsonPathObj.getLong("id"), 1);
        Assertions.assertEquals(jsonPathObj.getString("name"), "Author-Test");
    }

    @Test
    public void updateTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("id", 1L);
        empParams.put("name", "Author-upd");
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())
                .pathParam("id", "1")

                .when()
                .put("/authors/{id}")

                .then()
                .assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Author-upd"));
    }

    @Test
    public void deleteTest() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .delete("/authors/{id}")
                .then()
                .assertThat().statusCode(204);
    }
}
