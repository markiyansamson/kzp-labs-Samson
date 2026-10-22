package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class VetVisitPolymorphismTest {

    @Test
    void routineVisitKeepsBasePrice() {
        VetVisit visit = new RoutineVisit(
                "Мурка",
                "кіт",
                "огляд",
                500.0);

        assertEquals(
                500.0,
                visit.finalCost(),
                0.001);

        assertEquals(
                VisitKind.ROUTINE,
                visit.getKind());
    }

    @Test
    void urgentVisitUsesMinimumUrgentCost() {
        VetVisit visit = new UrgentVisit(
                "Рекс",
                "собака",
                "невідкладна допомога",
                800.0);

        assertEquals(
                1000.0,
                visit.finalCost(),
                0.001);

        assertEquals(
                VisitKind.URGENT,
                visit.getKind());
    }

    @Test
    void urgentVisitKeepsHigherBasePrice() {
        VetVisit visit = new UrgentVisit(
                "Рекс",
                "собака",
                "невідкладна допомога",
                1200.0);

        assertEquals(
                1200.0,
                visit.finalCost(),
                0.001);
    }

    @Test
    void subtypesHaveDifferentPolymorphicBehavior() {
        VetVisit routine = new RoutineVisit(
                "Мурка",
                "кіт",
                "огляд",
                800.0);

        VetVisit urgent = new UrgentVisit(
                "Рекс",
                "собака",
                "невідкладна допомога",
                800.0);

        assertEquals(
                800.0,
                routine.finalCost(),
                0.001);

        assertEquals(
                1000.0,
                urgent.finalCost(),
                0.001);

        assertNotEquals(
                routine.finalCost(),
                urgent.finalCost());
    }

    @Test
    void polymorphicCollectionProcessesDifferentSubtypes() {
        List<VetVisit> visits = List.of(
                new RoutineVisit(
                        "Мурка",
                        "кіт",
                        "огляд",
                        500.0),
                new UrgentVisit(
                        "Рекс",
                        "собака",
                        "невідкладна допомога",
                        800.0));

        double totalFinalCost = visits.stream()
                .mapToDouble(VetVisit::finalCost)
                .sum();

        assertEquals(
                1500.0,
                totalFinalCost,
                0.001);
    }

    @Test
    void csvFactoryCreatesBothConcreteSubtypes() {
        VetVisit routine = VetVisit.fromCsv(
                "Мурка;кіт;огляд;350.00;false");

        VetVisit urgent = VetVisit.fromCsv(
                "Рекс;собака;невідкладна допомога;1200.00;true");

        assertInstanceOf(
                RoutineVisit.class,
                routine);

        assertInstanceOf(
                UrgentVisit.class,
                urgent);

        assertEquals(
                VisitKind.ROUTINE,
                routine.getKind());

        assertEquals(
                VisitKind.URGENT,
                urgent.getKind());
    }
}