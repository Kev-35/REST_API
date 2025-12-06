package tests;

import models.RqCreateUserApiModel;
import models.RqUpdateUserApiModel;
import models.RsCreateUserApiModel;
import models.RsUpdateUserApiModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specification.SpecificanionForApi;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specification.SpecificanionForApi.*;

public class ApiWithSpecWithAllureTests extends TestBaseApi {

    @Test
    @Tag("API-test")
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest() {

        RqCreateUserApiModel bodyRq = new RqCreateUserApiModel("Роман", "Инженер");
        RsCreateUserApiModel bodyRs = step("Создание пользователя", () ->
                given(baseReqSpec)
                        .body(bodyRq)
                        .when()
                        .post("/users/1")
                        .then()
                        .spec(SpecificanionForApi.getResSpec(201))
                        .extract().as(RsCreateUserApiModel.class));

        step("Результаты теста", () ->
                assertEquals("Роман", bodyRs.getName()));
        assertEquals("Инженер", bodyRs.getJob());
        assertNotNull(bodyRs.getId());
        assertNotNull(bodyRs.getCreatedAt());
    }

    @Test
    @Tag("API-test")
    @DisplayName("Обновление пользователя")
    public void updateUserTest() {

        RqUpdateUserApiModel bodyRq = new RqUpdateUserApiModel("Дима", "Грузчик");
        RsUpdateUserApiModel bodyRs = step("Изменение данных пользователя", () ->
                given(baseReqSpec)
                        .body(bodyRq)
                        .when()
                        .put("/users/1")
                        .then()
                        .spec(SpecificanionForApi.getResSpec(200))
                        .extract().as(RsUpdateUserApiModel.class));

        step("Результаты теста", () ->
                assertEquals("Дима", bodyRs.getName()));
        assertEquals("Грузчик", bodyRs.getJob());
        assertNotNull(bodyRs.getUpdatedAt());
    }

    @Test
    @Tag("API-test")
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {
        given(baseReqSpec)
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .spec(SpecificanionForApi.getResSpec(204));
    }
}
