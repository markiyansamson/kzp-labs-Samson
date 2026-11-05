package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class VetVisitTest {

    @Test
    void constructorStoresValidValues() {
        VetVisit visit = new RoutineVisit(
                " Боня ",
                " кіт ",
                " Іван Петренко ",
                LocalDate.of(2026, 10, 15),
                " профілактичний огляд ",
                500.0);

        assertEquals(
                "Боня",
                visit.getAnimal());

        assertEquals(
                "кіт",
                visit.getSpecies());

        assertEquals(
                "Іван Петренко",
                visit.getOwner());

        assertEquals(
                LocalDate.of(2026, 10, 15),
                visit.getDate());

        assertEquals(
                "профілактичний огляд",
                visit.getDiagnosis());

        assertEquals(
                500.0,
                visit.getPrice(),
                0.001);

        assertEquals(
                VisitKind.ROUTINE,
                visit.getKind());

        assertFalse(
                visit.isUrgent());
    }

    @Test
    void constructorRejectsBlankTextFields() {
        LocalDate date =
                LocalDate.of(2026, 10, 15);

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "",
                        "кіт",
                        "Іван Петренко",
                        date,
                        "огляд",
                        500.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        " ",
                        "Іван Петренко",
                        date,
                        "огляд",
                        500.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "",
                        date,
                        "огляд",
                        500.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "Іван Петренко",
                        date,
                        " ",
                        500.0));
    }

    @Test
    void constructorRejectsNullDate() {
        assertThrows(
                NullPointerException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "Іван Петренко",
                        null,
                        "огляд",
                        500.0));
    }

    @Test
    void constructorRejectsInvalidPrice() {
        LocalDate date =
                LocalDate.of(2026, 10, 15);

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "Іван Петренко",
                        date,
                        "огляд",
                        -1.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "Іван Петренко",
                        date,
                        "огляд",
                        Double.NaN));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "Іван Петренко",
                        date,
                        "огляд",
                        Double.POSITIVE_INFINITY));
    }

    @Test
    void fromCsvCreatesRoutineVisit() {
        VetVisit visit =
                VetVisit.fromCsv(
                        "Боня;кіт;Іван Петренко;"
                                + "2026-10-15;"
                                + "профілактичний огляд;"
                                + "650.50;false");

        assertInstanceOf(
                RoutineVisit.class,
                visit);

        assertEquals(
                "Боня",
                visit.getAnimal());

        assertEquals(
                "кіт",
                visit.getSpecies());

        assertEquals(
                "Іван Петренко",
                visit.getOwner());

        assertEquals(
                LocalDate.of(2026, 10, 15),
                visit.getDate());

        assertEquals(
                "профілактичний огляд",
                visit.getDiagnosis());

        assertEquals(
                650.50,
                visit.getPrice(),
                0.001);

        assertFalse(
                visit.isUrgent());
    }

    @Test
    void fromCsvCreatesUrgentVisit() {
        VetVisit visit =
                VetVisit.fromCsv(
                        "Рекс;собака;Олена Коваль;"
                                + "2026-10-20;"
                                + "травма лапи;"
                                + "1200.00;true");

        assertInstanceOf(
                UrgentVisit.class,
                visit);

        assertEquals(
                "Олена Коваль",
                visit.getOwner());

        assertEquals(
                LocalDate.of(2026, 10, 20),
                visit.getDate());

        assertEquals(
                "травма лапи",
                visit.getDiagnosis());

        assertTrue(
                visit.isUrgent());
    }

    @Test
    void fromCsvRejectsWrongFieldCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;Іван Петренко;"
                                + "2026-10-15;огляд;500"));
    }

    @Test
    void fromCsvRejectsInvalidDate() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;Іван Петренко;"
                                + "15-10-2026;"
                                + "огляд;500;false"));
    }

    @Test
    void fromCsvRejectsInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;Іван Петренко;"
                                + "2026-10-15;"
                                + "огляд;abc;false"));
    }

    @Test
    void fromCsvRejectsInvalidUrgent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;Іван Петренко;"
                                + "2026-10-15;"
                                + "огляд;500;maybe"));
    }

    @Test
    void toStringUsesStableFormat() {
        VetVisit visit = new RoutineVisit(
                "Боня",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 15),
                "огляд",
                500.0);

        assertEquals(
                "Боня; кіт; Іван Петренко; "
                        + "2026-10-15; огляд; "
                        + "500.00; urgent=false",
                visit.toString());
    }
}