import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class GetListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOfOrdersCheckStatusCode200AndReturnListOfOrders() {
        Response response =
                given().get("/api/v1/orders");
        response.then().assertThat()
                .statusCode(200) // проверь статус ответа
                .and()
                .body("data.orders", not(emptyArray()));
    }


}
