package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class VetVisitPolymorphismTest {

    @Test
    void storesDifferentSubtypesThroughBaseType() {
        List<VetVisit> visits = List.of(
                new RoutineVisit(
                        "Мурка",
                        "кіт",
                        "Іван Петренко",
                        LocalDate.of(2026, 10, 1),
                        "огляд",
                        350.0),
                new UrgentVisit(
                        "Рекс",
                        "собака",
                        "Олена Коваль",
                        LocalDate.of(2026, 10, 5),
                        "травма лапи",
                        1200.0));

        assertInstanceOf(
                RoutineVisit.class,
                visits.get(0));

        assertInstanceOf(
                UrgentVisit.class,
                visits.get(1));
    }

    @Test
    void finalCostIsDispatchedPolymorphically() {
        List<VetVisit> visits = List.of(
                new RoutineVisit(
                        "Мурка",
                        "кіт",
                        "Іван Петренко",
                        LocalDate.of(2026, 10, 1),
                        "огляд",
                        350.0),
                new UrgentVisit(
                        "Бім",
                        "собака",
                        "Марія Бондар",
                        LocalDate.of(2026, 10, 2),
                        "гострий біль",
                        600.0),
                new UrgentVisit(
                        "Рекс",
                        "собака",
                        "Олена Коваль",
                        LocalDate.of(2026, 10, 5),
                        "травма лапи",
                        1200.0));

        assertEquals(
                List.of(
                        350.0,
                        1000.0,
                        1200.0),
                visits.stream()
                        .map(VetVisit::finalCost)
                        .toList());
    }

    @Test
    void factoryCreatesCorrectSubtype() {
        VetVisit routine =
                VetVisit.fromCsv(
                        "Мурка;кіт;Іван Петренко;"
                                + "2026-10-01;"
                                + "огляд;"
                                + "350.00;false");

        VetVisit urgent =
                VetVisit.fromCsv(
                        "Рекс;собака;Олена Коваль;"
                                + "2026-10-05;"
                                + "травма лапи;"
                                + "1200.00;true");

        assertInstanceOf(
                RoutineVisit.class,
                routine);

        assertInstanceOf(
                UrgentVisit.class,
                urgent);
    }

    @Test
    void controllerSummaryUsesPolymorphicFinalCost() {
        List<VetVisit> visits = List.of(
                new RoutineVisit(
                        "Мурка",
                        "кіт",
                        "Іван Петренко",
                        LocalDate.of(2026, 10, 1),
                        "огляд",
                        350.0),
                new UrgentVisit(
                        "Бім",
                        "собака",
                        "Марія Бондар",
                        LocalDate.of(2026, 10, 2),
                        "гострий біль",
                        600.0));

        PriceUrgency result =
                VetClinicController.calculateSummary(
                        visits);

        assertEquals(
                1350.0,
                result.totalPrice(),
                0.001);

        assertEquals(
                1,
                result.urgentCount());
    }
}