import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CreatCourierWithoutLoginPasswordTest {
    private CourierCreateAndDelete courierCreateAndDelete;
    @Before
    public void setUp() {
        courierCreateAndDelete = new CourierCreateAndDelete();
    }
    @Test
    @DisplayName("Нельзя создать курьера без заполнения поля Логин")
    public void creatCourierWithoutLoginCheckStatusCode400Error() {
        CourierRegister courier = new CourierRegister("", "1234", "Amm");

        String withoutLogin = courierCreateAndDelete.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat("Курьер создан без обязательного логина", withoutLogin, equalTo("Недостаточно данных для создания учетной записи"));

    }
    @Test
    @DisplayName("Нельзя создать курьера без заполнения поля Пароль")
    public void creatCourierWithoutPasswordCheckStatusCode400Error() {
        CourierRegister courier = new CourierRegister("mag", "", "Amm");

        String withoutPassword = courierCreateAndDelete.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertThat("Курьер создан без обязательного пароля", withoutPassword, equalTo("Недостаточно данных для создания учетной записи"));

    }
}
