import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreatCourierTest {

    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;

    @Before
    public void setUp() {
        courierCreateAndDelete = new CourierCreateAndDelete();

    }
    @After
    public void deletedCourier(){
        courierCreateAndDelete.delete(courierId);
    }

    @Test
    @DisplayName("Создание курьера")
    public void creatCourierAndCheckStatusCode(){
        CourierRegister courier = CourierRegister.getRandom();
        boolean isCourierCreated = courierCreateAndDelete.create(courier)
              .assertThat()
                .statusCode(201)
               .extract()
              .path("ok");

      CourierLogin bodyLogin = CourierLogin.from (courier);
        courierId = courierCreateAndDelete.login(bodyLogin)
               .assertThat()
               .statusCode(200)
                .extract()
               .path("id");

        assertTrue("Курьер не создан",isCourierCreated);
        assertThat("Для курьера не создан id", courierId, is(not(0)));
    }

    //"message""Этот логин уже используется. Попробуйте другой.", несовпадает текст, возможно баг
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void creatIdenticalCourierCheckStatusCode409Error() {
        CourierRegister courier = CourierRegister.getRandom();
        boolean isCourierCreated = courierCreateAndDelete.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
               .path("ok");
       CourierLogin bodyLogin = CourierLogin.from (courier);
        courierId = courierCreateAndDelete.login(bodyLogin)
               .assertThat()
               .statusCode(200)
                .extract()
                .path("id");

        String isNotCourierCreated = courierCreateAndDelete.create(courier)
              .assertThat()
               .statusCode(409)
                .extract()
               .path("message");

        assertTrue("Курьер создан", isCourierCreated);
        assertEquals("Создались два одинаковых курьера", "Этот логин уже используется. Попробуйте другой.", isNotCourierCreated);
    }
}

