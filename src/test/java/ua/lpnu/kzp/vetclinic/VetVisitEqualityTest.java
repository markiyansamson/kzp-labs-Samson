package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VetVisitEqualityTest {

    @Test
    void equalRoutineVisitsHaveEqualHashCodes() {
        VetVisit first = routine();
        VetVisit second = routine();

        assertEquals(first, second);
        assertEquals(
                first.hashCode(),
                second.hashCode());
    }

    @Test
    void differentOwnerMakesVisitsDifferent() {
        VetVisit first = routine();

        VetVisit second = new RoutineVisit(
                "Мурка",
                "кіт",
                "Інший власник",
                LocalDate.of(2026, 10, 1),
                "огляд",
                350.0);

        assertNotEquals(first, second);
    }

    @Test
    void differentDateMakesVisitsDifferent() {
        VetVisit first = routine();

        VetVisit second = new RoutineVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 2),
                "огляд",
                350.0);

        assertNotEquals(first, second);
    }

    @Test
    void differentDiagnosisMakesVisitsDifferent() {
        VetVisit first = routine();

        VetVisit second = new RoutineVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "інший діагноз",
                350.0);

        assertNotEquals(first, second);
    }

    @Test
    void differentSubtypesAreNotEqual() {
        VetVisit routine = routine();

        VetVisit urgent = new UrgentVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "огляд",
                350.0);

        assertNotEquals(
                routine,
                urgent);
    }

    @Test
    void hashSetRemovesLogicalDuplicates() {
        Set<VetVisit> visits =
                new HashSet<>();

        visits.add(routine());
        visits.add(routine());

        assertEquals(
                1,
                visits.size());

        assertTrue(
                visits.contains(routine()));
    }

    private static VetVisit routine() {
        return new RoutineVisit(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "огляд",
                350.0);
    }
}