import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreatIdenticalCourierTest {
    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;
    CourierRegister courier = CourierRegister.getRandom();
    CourierLogin bodyLogin = CourierLogin.from (courier);

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courierCreateAndDelete = new CourierCreateAndDelete();
        courierCreateAndDelete.create(courier);
        courierId = courierCreateAndDelete.login(bodyLogin);
    }
    @After
    public void deletedCourier(){
        courierCreateAndDelete.delete(courierId);
    }

    //"message""Этот логин уже используется.", несовпадает текст, возможно баг
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void creatIdenticalCourierCheckStatusCode409Error() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat()
                .statusCode(409) // проверь статус ответа
                .and()
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой.")); // проверь поле message
    }

}

