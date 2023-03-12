package com.mjc.school.controller.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CommentTest {

    @BeforeAll
    public static void setup() {
        baseURI = "http://localhost:8081/api";
    }

    @Test
    public void basicPingTest() {
        given().when().get("/comments").then().statusCode(200);
    }

    @Test
    public void createTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("name", "Comment");
        empParams.put("newsId", 1);
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())

                .when()
                .post("/comments")

                .then()
                .assertThat().statusCode(201)
                .body("name", equalTo("Comment"));
    }

    @Test
    public void readByIdTest() {
        Response empResponse = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .get("/comments/{id}")
                .then()

                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathObj = empResponse.jsonPath();
        Assertions.assertEquals(jsonPathObj.getLong("id"), 1);
        Assertions.assertEquals(jsonPathObj.getString("name"), "Comment");
    }

    @Test
    public void updateTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("id", 1L);
        empParams.put("name", "Comment-upd");
        empParams.put("newsId", 1);
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())
                .pathParam("id", "1")

                .when()
                .put("/comments/{id}")

                .then()
                .assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Comment-upd"));
    }
}
