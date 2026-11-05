package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RoutineVisitTest {

    @Test
    void storesRoutineVisitData() {
        VetVisit visit = new RoutineVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "профілактичний огляд",
                350.0);

        assertEquals("Мурка", visit.getAnimal());
        assertEquals("кіт", visit.getSpecies());
        assertEquals("Іван Петренко", visit.getOwner());
        assertEquals(
                LocalDate.of(2026, 10, 1),
                visit.getDate());
        assertEquals(
                "профілактичний огляд",
                visit.getDiagnosis());
        assertEquals(
                350.0,
                visit.getPrice(),
                0.001);
        assertEquals(
                VisitKind.ROUTINE,
                visit.getKind());
        assertFalse(visit.isUrgent());
    }

    @Test
    void finalCostEqualsBasePrice() {
        VetVisit visit = new RoutineVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "огляд",
                350.0);

        assertEquals(
                350.0,
                visit.finalCost(),
                0.001);
    }
}