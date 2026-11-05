package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class UrgentVisitTest {

    @Test
    void storesUrgentVisitData() {
        VetVisit visit = new UrgentVisit(
                "Рекс",
                "собака",
                "Олена Коваль",
                LocalDate.of(2026, 10, 5),
                "травма лапи",
                1200.0);

        assertEquals("Рекс", visit.getAnimal());
        assertEquals("собака", visit.getSpecies());
        assertEquals("Олена Коваль", visit.getOwner());
        assertEquals(
                LocalDate.of(2026, 10, 5),
                visit.getDate());
        assertEquals(
                "травма лапи",
                visit.getDiagnosis());
        assertEquals(
                1200.0,
                visit.getPrice(),
                0.001);
        assertEquals(
                VisitKind.URGENT,
                visit.getKind());
        assertTrue(visit.isUrgent());
    }

    @Test
    void finalCostUsesBasePriceWhenAboveMinimum() {
        VetVisit visit = new UrgentVisit(
                "Рекс",
                "собака",
                "Олена Коваль",
                LocalDate.of(2026, 10, 5),
                "травма лапи",
                1200.0);

        assertEquals(
                1200.0,
                visit.finalCost(),
                0.001);
    }

    @Test
    void finalCostUsesMinimumUrgentCost() {
        VetVisit visit = new UrgentVisit(
                "Бім",
                "собака",
                "Марія Бондар",
                LocalDate.of(2026, 10, 8),
                "гострий біль",
                600.0);

        assertEquals(
                1000.0,
                visit.finalCost(),
                0.001);
    }
}