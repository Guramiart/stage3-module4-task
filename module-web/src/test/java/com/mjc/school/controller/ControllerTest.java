package com.mjc.school.controller;

import com.mjc.school.controller.tests.AuthorTest;
import com.mjc.school.controller.tests.CommentTest;
import com.mjc.school.controller.tests.NewsTest;
import com.mjc.school.controller.tests.TagTest;
import org.json.JSONException;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {

    @BeforeAll
    static void setup() {
        baseURI = "http://localhost:8081/api";
    }

    TagTest tagTest = new TagTest();
    AuthorTest authorTest = new AuthorTest();
    NewsTest newsTest = new NewsTest();
    CommentTest commentTest = new CommentTest();

    @Test
    @Order(1)
    void getTest() {
        tagTest.basicPingTest();
        authorTest.basicPingTest();
        newsTest.basicPingTest();
        commentTest.basicPingTest();
    }

    @Test
    @Order(2)
    void createTest() throws JSONException {
        tagTest.createTest();
        authorTest.createTest();
        newsTest.createTest();
        commentTest.createTest();
    }

    @Test
    @Order(3)
    void readByIdTest() {
        tagTest.readByIdTest();
        authorTest.readByIdTest();
        newsTest.readByIdTest();
        commentTest.readByIdTest();
    }

    @Test
    @Order(4)
    void updateTest() throws JSONException {
        tagTest.updateTest();
        authorTest.updateTest();
        newsTest.updateTest();
        commentTest.updateTest();
    }

    @Test
    @Order(5)
    void deleteTest() {
        newsTest.deleteTest();
        authorTest.deleteTest();
        tagTest.deleteTest();
    }
}
