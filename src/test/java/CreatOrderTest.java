import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import java.util.Arrays;
import java.util.Collection;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreatOrderTest {

    private String filePath;

    public CreatOrderTest(String filePath) {
        this.filePath = filePath;
    }
    @Parameterized.Parameters
    public static Collection getOrderData() {
        return Arrays.asList(new Object[][] {
                {"src/test/resources/scooterColorGreyBlack.json"},
                {"src/test/resources/scooterColorBlack.json"},
                {"src/test/resources/scooterColorGrey.json"},
                {"src/test/resources/scooterColorNull.json"}
        });
    }

    @Test
    @DisplayName("Создание заказа для разных вариантов поля color")
    public void selectionScooterColorCheckStatusCode201AndReturnTrack() {
        File json = new File(filePath);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("https://qa-scooter.praktikum-services.ru/api/v1/orders");
        response.then().assertThat()
                .statusCode(201) // проверь статус ответа
                .and()
                .body("track",notNullValue()); // проверь поле track не пустое
    }

}
