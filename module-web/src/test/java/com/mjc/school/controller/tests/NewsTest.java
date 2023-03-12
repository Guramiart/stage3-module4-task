package com.mjc.school.controller.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NewsTest {

    @BeforeAll
    public static void setup() {
        baseURI = "http://localhost:8081/api";
    }

    @Test
    public void basicPingTest() {
        given().when().get("/news").then().statusCode(200);
    }

    @Test
    public void createTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        empParams.put("title", "Title");
        empParams.put("content", "Content");
        empParams.put("authorId", 1L);
        jsonArray.put(1L);
        empParams.put("tagsIds", jsonArray);
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())

                .when()
                .post("/news")

                .then()
                .assertThat().statusCode(201)
                .body("title", equalTo("Title"))
                .body("content", equalTo("Content"));
    }

    @Test
    public void readByIdTest() {
        Response empResponse = given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .get("/news/{id}")
                .then()

                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathObj = empResponse.jsonPath();
        Assertions.assertEquals(jsonPathObj.getLong("id"), 1);
        Assertions.assertEquals(jsonPathObj.getString("title"), "Title");
        Assertions.assertEquals(jsonPathObj.getString("content"), "Content");
    }

    @Test
    public void updateTest() throws JSONException {
        JSONObject empParams = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        empParams.put("id", 1L);
        empParams.put("title", "Title-upd");
        empParams.put("content", "Content-upd");
        empParams.put("authorId", 1L);
        jsonArray.put(1L);
        empParams.put("tagsIds", jsonArray);
        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())
                .pathParam("id", "1")

                .when()
                .put("/news/{id}")

                .then()
                .assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Title-upd"))
                .body("content", equalTo("Content-upd"));
    }

    @Test
    public void deleteTest() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
                .when()
                .delete("/news/{id}")
                .then()
                .assertThat().statusCode(204);
    }

}
