import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
public class CourierCreateAndDelete extends RestAssuredClient {

    @Step ("Создание курьера")
    public ValidatableResponse create(CourierRegister courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

   @Step ("Авторизация курьера")
   public ValidatableResponse login(CourierLogin bodyLogin) {
       return given()
               .spec(getBaseSpec())
               .body(bodyLogin)
               .when()
               .post("/api/v1/courier/login")
               .then();
   }

    @Step("Удаление курьера")
    public ValidatableResponse delete (int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete("/api/v1/courier/{id}", courierId) // отправка DELETE-запроса
                .then();

        }
}
