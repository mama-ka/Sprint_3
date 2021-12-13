import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;


public class GetListOfOrdersTest {

    private OrderCreate orderCreate;
    @Before
    public void setUp() {
        orderCreate = new OrderCreate();
            }

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOfOrdersCheckStatusCode200AndReturnListOfOrders() {
        orderCreate.orderResponseBody().then().
        assertThat().body("orders.size()", is(not(0)));

}
}

