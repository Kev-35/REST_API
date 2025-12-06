package tests;

import models.RqCreateUserApiModel;
import models.RqUpdateUserApiModel;
import models.RsCreateUserApiModel;
import models.RsUpdateUserApiModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specification.SpecificanionForApi.baseReqSpec;
import static specification.SpecificanionForApi.getResSpec;

public class ApiWithSpecWithAllureTests extends TestBaseApi {
    RsCreateUserApiModel responseCreate;
    RsUpdateUserApiModel responseUpdate;

    @Test
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest() {
        RqCreateUserApiModel requestBody = new RqCreateUserApiModel("Кочка", "Инженер");
        step("Создание нового юзера", () -> {
            responseCreate = given()
                    .spec(baseReqSpec)
                    .body(requestBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(getResSpec(201))
                    .extract().as(RsCreateUserApiModel.class);
        });
        assertEquals(requestBody.getName(), responseCreate.getName());
        assertEquals(requestBody.getJob(), responseCreate.getJob());
        assertNotNull(responseCreate.getId());
        assertNotNull(responseCreate.getCreatedAt());

    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateUserTest() {
        RqUpdateUserApiModel requestBody = new RqUpdateUserApiModel("Дима", "Грузчик");

        step("Обновление данных пользователя", () -> {
            responseUpdate = given()
                    .spec(baseReqSpec)
                    .body(requestBody)
                    .when()
                    .put("/users/1")
                    .then()
                    .spec(getResSpec(200))
                    .extract().as(RsUpdateUserApiModel.class);
        });
        assertEquals(requestBody.getName(), responseUpdate.getName());
        assertEquals(requestBody.getJob(), responseUpdate.getJob());
        assertNotNull(responseUpdate.getUpdatedAt());
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {

        given()
                .spec(baseReqSpec)
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .spec(getResSpec(204));
    }
}
