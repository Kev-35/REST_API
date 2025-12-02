package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ApiTests {

    @BeforeAll
    public static void setupEnvironmentForAllTests() {
        RestAssured.baseURI = "https://reqres.in";
        basePath = "/api";
    }

    @Test
    @DisplayName("Успешное создание пользователя в системе")
    void createUserTest(){
        String bodyRq = "{\"name\": \"Кочка\", \"job\": \"Инженер\"}";

        given()
                .log().uri()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .post("/users/1")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("Кочка"))
                .body("job", equalTo("Инженер"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Запрос без атрибута \"name\"")
    void unsuccessfulCreateUserWithoutNameTest(){
        String bodyRq = "{\"job\": \"leader\"}";

        given()
                .log().uri()
                .log().body()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Пустое тело запроса")
    void unsuccessfulCreateUserEmptyRequest(){
        String bodyRq = "{}";

        given()
                .log().uri()
                .log().body()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Отсутствие в хедерах Api-Key")
    void unsuccessfulCreateUserWithoutApiKeyTest(){
        String bodyRq = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .log().uri()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "")
        .when()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(401)
                .body("error", equalTo("api_key_required"));
    }

    @Test
    @DisplayName("Отсутствие тело запроса")
    void unsuccessfulCreateUserWithoutBody(){

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }
    @Test
    @DisplayName("Обновление пользователя")
    public void updateUserTest() {
        String bodyRq = "{\"name\": \"Дима\", \"job\": \"Грузчик\"}";

        given()
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
                .contentType(ContentType.JSON)
                .body(bodyRq)
        .when()
                .put("/users/1")
        .then()
                .log().status()
                .statusCode(200)
                .log().body()
                .body("name", equalTo("Дима"))
                .body("job", equalTo("Грузчик"))
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {

        given()
                .header("x-api-key", "reqres_cf57c7dd8106450392f3dc134b1e4c2f")
        .when()
                .delete("/users/1")
        .then()
                .log().body()
                .statusCode(204).body(equalTo(""));
    }
}
