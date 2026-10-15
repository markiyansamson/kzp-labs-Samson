package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VetVisitTest {

    @Test
    void constructorStoresValidValues() {
        VetVisit visit = new VetVisit(
                " Боня ",
                " кіт ",
                " огляд ",
                500.0,
                true);

        assertEquals("Боня", visit.getAnimal());
        assertEquals("кіт", visit.getSpecies());
        assertEquals("огляд", visit.getVisitType());
        assertEquals(500.0, visit.getPrice());
        assertTrue(visit.isUrgent());
    }

    @Test
    void constructorRejectsBlankFields() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "",
                        "кіт",
                        "огляд",
                        500.0,
                        false));

        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "Боня",
                        " ",
                        "огляд",
                        500.0,
                        false));

        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "Боня",
                        "кіт",
                        "",
                        500.0,
                        false));
    }

    @Test
    void constructorRejectsInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        -1.0,
                        false));

        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        Double.NaN,
                        false));

        assertThrows(
                IllegalArgumentException.class,
                () -> new VetVisit(
                        "Боня",
                        "кіт",
                        "огляд",
                        Double.POSITIVE_INFINITY,
                        false));
    }

    @Test
    void fromCsvCreatesValidVisit() {
        VetVisit visit =
                VetVisit.fromCsv(
                        "Боня;кіт;огляд;650.50;true");

        assertEquals("Боня", visit.getAnimal());
        assertEquals("кіт", visit.getSpecies());
        assertEquals("огляд", visit.getVisitType());
        assertEquals(650.50, visit.getPrice());
        assertTrue(visit.isUrgent());
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
        VetVisit visit = new VetVisit(
                "Боня",
                "кіт",
                "огляд",
                500.0,
                false);

        assertEquals(
                "Боня; кіт; огляд; 500.00; urgent=false",
                visit.toString());

        assertFalse(visit.isUrgent());
    }
}