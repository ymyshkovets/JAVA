
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class DeliveryCostCalculationTest {

    @Test
    @Tag("Smoke")
    void testSmoke() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testNineKmBig() {
        Delivery delivery = new Delivery(9, CargoDimension.LARGE, false, ServiceWorkload.NORMAL);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testFrigileAndDistanceElevenKm() {
        Delivery delivery = new Delivery(11, CargoDimension.LARGE, false, ServiceWorkload.NORMAL);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testMore30km() {
        Delivery delivery = new Delivery(31, CargoDimension.LARGE, false, ServiceWorkload.NORMAL);
        assertEquals(500, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testWorkLoadIncreased() {
        Delivery delivery = new Delivery(9, CargoDimension.LARGE, false, ServiceWorkload.INCREASED);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testWorkLoadHigh() {
        Delivery delivery = new Delivery(9, CargoDimension.LARGE, false, ServiceWorkload.HIGH);
        assertEquals(420, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testWorkLoadVeryHigh() {
        Delivery delivery = new Delivery(9, CargoDimension.LARGE, false, ServiceWorkload.VERY_HIGH);
        assertEquals(480, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    void testCheapest() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Negative")
    void testFrafaleAndLongDistance() {
        Delivery delivery = new Delivery(33, CargoDimension.SMALL, true, ServiceWorkload.NORMAL);
        Throwable exception = assertThrows(
                UnsupportedOperationException.class,
                delivery::calculateDeliveryCost
        );
        assertEquals("Fragile cargo cannot be delivered for the distance more than 30", exception.getMessage());
    }
}
