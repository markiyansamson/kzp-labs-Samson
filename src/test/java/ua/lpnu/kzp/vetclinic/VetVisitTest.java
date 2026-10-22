package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VetVisitTest {

    @Test
    void constructorStoresValidValues() {
        VetVisit visit = new UrgentVisit(
                " Боня ",
                " кіт ",
                " огляд ",
                500.0);

        assertEquals("Боня", visit.getAnimal());
        assertEquals("кіт", visit.getSpecies());
        assertEquals("огляд", visit.getVisitType());
        assertEquals(500.0, visit.getPrice());
        assertTrue(visit.isUrgent());
        assertEquals(VisitKind.URGENT, visit.getKind());
    }

    @Test
    void constructorRejectsBlankFields() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "",
                        "кіт",
                        "огляд",
                        500.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        " ",
                        "огляд",
                        500.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "",
                        500.0));
    }

    @Test
    void constructorRejectsInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        -1.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        Double.NaN));

        assertThrows(
                IllegalArgumentException.class,
                () -> new RoutineVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        Double.POSITIVE_INFINITY));
    }

    @Test
    void fromCsvCreatesValidVisit() {
        VetVisit visit =
                VetVisit.fromCsv(
                        "Боня;кіт;огляд;650.50;true");

        assertInstanceOf(UrgentVisit.class, visit);

        assertEquals("Боня", visit.getAnimal());
        assertEquals("кіт", visit.getSpecies());
        assertEquals("огляд", visit.getVisitType());
        assertEquals(650.50, visit.getPrice());
        assertTrue(visit.isUrgent());
        assertEquals(VisitKind.URGENT, visit.getKind());
    }

    @Test
    void fromCsvRejectsWrongFieldCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;огляд;500"));
    }

    @Test
    void fromCsvRejectsInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;огляд;abc;false"));
    }

    @Test
    void fromCsvRejectsInvalidUrgent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisit.fromCsv(
                        "Боня;кіт;огляд;500;maybe"));
    }

    @Test
    void toStringUsesStableFormat() {
        VetVisit visit = new RoutineVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0);

        assertEquals(
                "Боня; кіт; огляд; 500.00; urgent=false",
                visit.toString());

        assertFalse(visit.isUrgent());
        assertEquals(VisitKind.ROUTINE, visit.getKind());
    }
}