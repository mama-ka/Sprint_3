import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)

public class CreatOrderTest {
    private OrderCreate orderCreate;
    private String[] color;

    @Before
    public void setUp() {
        orderCreate = new OrderCreate();

        }
    public CreatOrderTest(String color) {
        this.color = new String[]{color};
    }

    @Parameterized.Parameters
    public static Object[] getColorBody() {
        return new Object[][]{
                {"GREY\" , \"BLACK"},
                {"GREY"},
                {"BLACK"},
                {""}
        };
    }
    @Test
    @DisplayName("Создание заказа для разных вариантов поля color")
    public void selectionScooterColorCheckStatusCode201AndReturnTrack() {

        OrderBody orderBody = OrderBody.getRandom();
        orderBody.setColor(color);
        int trackId = orderCreate.createOrder(orderBody).assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        assertThat("Courier track is incorrect", trackId, is(not(0)));
    }

}
