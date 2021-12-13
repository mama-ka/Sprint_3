import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;
    private String message;

    CourierRegister courier = CourierRegister.getRandom();


    @Before
    public void setUp() {
        courierCreateAndDelete = new CourierCreateAndDelete();
        courierCreateAndDelete.create(courier);

    }
    @After
    public void deletedCourier(){
        courierCreateAndDelete.delete(courierId);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void authorizationCourierCheckStatusCode200AndReturnId() {
        CourierLogin bodyLogin = CourierLogin.from (courier);
        courierId = courierCreateAndDelete.login(bodyLogin)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        assertThat("Запрос не возвращает id", courierId, is(not(0)));

    }


}