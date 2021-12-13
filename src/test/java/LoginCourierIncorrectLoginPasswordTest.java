import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierIncorrectLoginPasswordTest {
    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;
    private String message;

    CourierRegister courier = CourierRegister.getRandom();


    @Before
    public void setUp() {
        courierCreateAndDelete = new CourierCreateAndDelete();
        courierCreateAndDelete.create(courier);
        CourierLogin bodyLogin = CourierLogin.from (courier);
        courierId = courierCreateAndDelete.login(bodyLogin)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }
    @After
    public void deletedCourier(){
        courierCreateAndDelete.delete(courierId);
    }


    @Test
    @DisplayName("Нельязя авторизоваться без Логина")
    public void authorizationCourierWithoutLoginCheckStatusCode400Error() {
        CourierLogin bodyLogin = new CourierLogin ("", courier.password);
        String message = courierCreateAndDelete.login (bodyLogin)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertThat("Можно зарегестрироваться без логина", message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельязя авторизоваться без Пароля")
    public void authorizationCourierWithoutPasswordCheckStatusCode400Error() {
        CourierLogin bodyLogin = new CourierLogin (courier.login, "");
        String message = courierCreateAndDelete.login (bodyLogin)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat("Можно зарегестрироваться без пароля", message, equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Нельязя авторизоваться с некорректным Логином")
    public void authorizationCourierIncorrectLoginCheckStatusCode404Error() {
        CourierLogin bodyLogin = new CourierLogin ("м", courier.password);
        message = courierCreateAndDelete.login (bodyLogin)
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertThat("Можно зарегестрироваться с некорректным логином", message, equalTo("Учетная запись не найдена"));

    }
    @Test
    @DisplayName("Нельязя авторизоваться с некорректным Паролем")
    public void authorizationCourierIncorrectPasswordCheckStatusCode404Error() {
        CourierLogin bodyLogin = new CourierLogin (courier.login, "5");
        message = courierCreateAndDelete.login (bodyLogin)
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertThat("Можно зарегестрироваться с некорректным паролем", message, equalTo("Учетная запись не найдена"));
    }

}

