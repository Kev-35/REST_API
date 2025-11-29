
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserApiTests {

    @BeforeAll
    public static void setupEnvironmentForAllTests() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @DisplayName("Успешное создание пользователя в системе")
    void successfulUserCreationTest(){
        String bodyRq = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .log().uri()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
        .when()
                .post("/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Запрос без атрибута \"name\"")
    void unsuccessfulUserCreationWithoutNameTest(){
        String bodyRq = "{\"job\": \"leader\"}";

        given()
                .log().uri()
                .log().body()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
        .when()
                .post("/api/users")
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
    void unsuccessfulUserCreationEmptyRequest(){
        String bodyRq = "{}";

        given()
                .log().uri()
                .log().body()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
        .when()
                .post("/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Отсутствие в хедерах Api-Key")
    void unsuccessfulUserCreationWithoutApiKeyTest(){
        String bodyRq = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .log().uri()
                .body(bodyRq)
                .contentType(ContentType.JSON)
                .header("", "")
        .when()
                .post("/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(401)
                .body("error", equalTo("Missing API key"));
    }

    @Test
    @DisplayName("Отсутствие тело запроса")
    void unsuccessfulUserCreationWithoutBody(){

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
        .when()
                .post("/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }
}
