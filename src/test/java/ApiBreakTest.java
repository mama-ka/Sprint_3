import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ApiBreakTest extends RestAssuredClient{
    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;
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


    //ломаем АПИ, тест падает, время ответа превышает 60 сек
    @Test (timeout = 60000)

    @DisplayName("Апи ломается при авторизации с паролем null")
    public void authorizationCourierPasswordNullApiBreak() {
        CourierLogin bodyLogin = new CourierLogin (courier.login, null);
        String message = courierCreateAndDelete.login (bodyLogin)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat("Можно зарегестрироваться без пароля", message, equalTo("Недостаточно данных для входа"));

    }

}
