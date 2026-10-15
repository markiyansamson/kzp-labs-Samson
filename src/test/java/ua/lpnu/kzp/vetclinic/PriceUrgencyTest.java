package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PriceUrgencyTest {

    @Test
    void storesValidValues() {
        PriceUrgency summary =
                new PriceUrgency(2050.0, 1);

        assertEquals(2050.0, summary.totalPrice());
        assertEquals(1, summary.urgentCount());
    }

    @Test
    void rejectsNegativeTotalPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PriceUrgency(-1.0, 1));
    }

    @Test
    void rejectsNonFiniteTotalPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PriceUrgency(
                        Double.NaN,
                        1));

        assertThrows(
                IllegalArgumentException.class,
                () -> new PriceUrgency(
                        Double.POSITIVE_INFINITY,
                        1));
    }

    @Test
    void rejectsNegativeUrgentCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PriceUrgency(
                        100.0,
                        -1));
    }
}