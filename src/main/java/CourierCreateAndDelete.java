import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class CourierCreateAndDelete {
    @Step("Создание курьера")
    public boolean create (CourierRegister courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(201) // проверь статус ответа
                .extract()
                .path("ok");

          }

    @Step("Невозможно создать курьера")
    public String dontCreate (CourierRegister courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400) // проверь статус ответа
                .extract()
                .path("message");
    }

    @Step("Авторизация курьера")
    public int login (CourierLogin bodyLogin){
        return  given().header("Content-type", "application/json")
                .body(bodyLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(200) // проверь статус ответа
                .extract()
                .path("id");
    }
    @Step("Некорректная авторизация курьера")
    public String incorrectLogin (CourierLogin bodyLogin){
        return  given().header("Content-type", "application/json")
                .body(bodyLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(404) // проверь статус ответа
                .extract()
                .path("message");
    }
    @Step("Авторизация без логина/пароля")
    public String loginWithoutPasswordLogin (CourierLogin bodyLogin){
        return  given().header("Content-type", "application/json")
                .body(bodyLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(400) // проверь статус ответа
                .extract()
                .path("message");
    }
    @Step("Удаление курьера")
    public boolean delete (int courierId){
        return given().header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/{id}", courierId) // отправка DELETE-запроса
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
}
}
