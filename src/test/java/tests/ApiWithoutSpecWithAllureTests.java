package tests;

import models.RqUpdateUserApiModel;
import models.RsCreateUserApiModel;
import models.RsUpdateUserApiModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiWithoutSpecWithAllureTests extends TestBaseApi {

    @Test
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest(){
        RqUpdateUserApiModel bodyRq = new RqUpdateUserApiModel();
        RsCreateUserApiModel bodyRs = new RsCreateUserApiModel();

        bodyRq.setName("Кочка");
        bodyRq.setJob("Инженер");

         RsCreateUserApiModel response = step("Создание нового юзера", () ->
                given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(bodyRq)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")

                .when()
                .post("/users/1")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(RsCreateUserApiModel.class));

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

        RsUpdateUserApiModel bodyRs = with()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .body(bodyRq)
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
                .log().headers()
                .contentType(JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .statusCode(204).body(equalTo(""));
    }
}
