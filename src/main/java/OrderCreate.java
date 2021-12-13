import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
public class OrderCreate extends RestAssuredClient {

    @Step ("Создание заказа")
    public ValidatableResponse createOrder(OrderBody order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("api/v1/orders/")
                .then();
    }
    @Step ("Получение списка заказов")
    public Response orderResponseBody() {
        return given()
                .spec(getBaseSpec())
                .get("/api/v1/orders");
    }
}
