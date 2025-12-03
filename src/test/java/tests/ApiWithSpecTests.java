package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.RqCreateUserApiModel;
import models.RqUpdateUserApiModel;
import models.RsCreateUserApiModel;
import models.RsUpdateUserApiModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiWithSpecTests {

    @Test
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest(){
        RqCreateUserApiModel bodyRq = new RqCreateUserApiModel();
        bodyRq.setName("Кочка");
        bodyRq.setJob("Инженер");
        RsCreateUserApiModel bodyRs = given()
                .filter(new AllureRestAssured())
                        .log().uri()
                        .body(bodyRq)
                        .log().body()
                        .contentType(ContentType.JSON)
                        .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
                .when()
                        .post("/users/1")
                .then()
                        .log().status()
                        .log().body()
                        .statusCode(201)
                .extract().as(RsCreateUserApiModel.class);

        assertEquals("Кочка",bodyRs.getName());
        assertEquals("Инженер",bodyRs.getJob());
        assertNotNull(bodyRs.getId());
        assertNotNull(bodyRs.getCreatedAt());
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateUserTest() {
        RqUpdateUserApiModel bodyRq = new RqUpdateUserApiModel();
        bodyRq.setName("Дима");
        bodyRq.setJob("Грузчик");

        RsUpdateUserApiModel bodyRs = given()
                .log().uri()
                .body(bodyRq)
                .log().body()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .put("/users/1")
        .then()
                .log().status()
                .statusCode(200)
                .log().body()
                .extract().as(RsUpdateUserApiModel.class);

        assertEquals("Дима",bodyRs.getName());
        assertEquals("Грузчик",bodyRs.getJob());
        assertNotNull(bodyRs.getUpdatedAt());
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .statusCode(204).body(equalTo(""));
    }
}
