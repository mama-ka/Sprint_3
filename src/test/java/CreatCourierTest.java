import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CreatCourierTest {

    private CourierCreateAndDelete courierCreateAndDelete;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
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
        boolean isCourierCreated = courierCreateAndDelete.create(courier);

        CourierLogin bodyLogin = CourierLogin.from (courier);
        courierId = courierCreateAndDelete.login(bodyLogin);


        assertTrue("Курьер не создан",isCourierCreated);
        assertThat("Для курьера не создан id", courierId, is(not(0)));


    }

}

