package tests;

import models.RqCreateUserApiModel;
import models.RqUpdateUserApiModel;
import models.RsCreateUserApiModel;
import models.RsUpdateUserApiModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specification.SpecsForApi.*;

public class ApiWithSpecWithAllureTests extends TestBaseApi {

    @Test
    @Tag("API-test")
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest(){

        RqCreateUserApiModel bodyRq = new RqCreateUserApiModel();
        bodyRq.setName("Роман");
        bodyRq.setJob("Инженер");

        RsCreateUserApiModel bodyRs = step("Создание пользователя", ()->
                        given(rqCreateUserSpec)
                                .body(bodyRq)

                .when()
                .post("/users/1")

                                .then()
                                .spec(rsCreateUserSpec)
                .extract().as(RsCreateUserApiModel.class));

        step("Результаты теста", ()->
                assertEquals("Роман",bodyRs.getName()));
                assertEquals("Инженер",bodyRs.getJob());
                assertNotNull(bodyRs.getId());
                assertNotNull(bodyRs.getCreatedAt());
    }

    @Test
    @Tag("API-test")
    @DisplayName("Обновление пользователя")
    public void updateUserTest() {

        RqUpdateUserApiModel bodyRq = new RqUpdateUserApiModel();
        bodyRq.setName("Дима");
        bodyRq.setJob("Грузчик");

        RsUpdateUserApiModel bodyRs = step("Изменение данных пользователя", ()->
                given(rqUpdateUserSpec)
                        .body(bodyRq)

                        .when()
                        .put("/users/1")

                        .then()
                        .spec(rsUpdateUserSpec)
                        .extract().as(RsUpdateUserApiModel.class));

        step("Результаты теста", ()->
        assertEquals("Дима",bodyRs.getName()));
        assertEquals("Грузчик",bodyRs.getJob());
        assertNotNull(bodyRs.getUpdatedAt());
    }

    @Test
    @Tag("API-test")
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {

        given(rqDeleteUserSpec)
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .spec(rsDeleteUserSpec);
    }
}
