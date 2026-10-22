package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VetVisitEqualityTest {

    @Test
    void equalRoutineVisitsAreEqual() {
        VetVisit first = new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        VetVisit second = new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        assertEquals(first, second);
    }

    @Test
    void equalVisitsHaveEqualHashCodes() {
        VetVisit first = new UrgentVisit(
                "Рекс",
                "собака",
                "допомога",
                1200.0);

        VetVisit second = new UrgentVisit(
                "Рекс",
                "собака",
                "допомога",
                1200.0);

        assertEquals(
                first.hashCode(),
                second.hashCode());
    }

    @Test
    void hashSetDoesNotDuplicateEqualVisits() {
        Set<VetVisit> visits = new HashSet<>();

        visits.add(new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0));

        visits.add(new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0));

        assertEquals(1, visits.size());
    }

    @Test
    void differentSubtypesAreNotEqual() {
        VetVisit routine = new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        VetVisit urgent = new UrgentVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        assertNotEquals(routine, urgent);
    }

    @Test
    void visitsWithDifferentDataAreNotEqual() {
        VetVisit first = new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        VetVisit second = new RoutineVisit(
                "Мурка",
                "кіт",
                "огляд",
                500.0);

        assertNotEquals(first, second);
    }
}