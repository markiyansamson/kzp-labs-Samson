package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class VetVisitQueriesTest {

    private final List<VetVisit> visits = List.of(
            new RoutineVisit(
                    "Мурка",
                    "кіт",
                    "Іван Петренко",
                    LocalDate.of(2026, 10, 1),
                    "профілактичний огляд",
                    350.0),

            new RoutineVisit(
                    "Бім",
                    "собака",
                    "Олена Коваль",
                    LocalDate.of(2026, 10, 2),
                    "вакцинація",
                    500.0),

            new UrgentVisit(
                    "Рекс",
                    "собака",
                    "Андрій Мельник",
                    LocalDate.of(2026, 10, 5),
                    "травма лапи",
                    1200.0),

            new RoutineVisit(
                    "Альфа",
                    "кінь",
                    "Марія Бондар",
                    LocalDate.of(2026, 10, 3),
                    "огляд",
                    1200.0),

            new RoutineVisit(
                    "Луна",
                    "кіт",
                    "Ірина Савчук",
                    LocalDate.of(2026, 10, 4),
                    "аналізи",
                    700.0),

            new RoutineVisit(
                    "Барсик",
                    "кіт",
                    "Іван Петренко",
                    LocalDate.of(2026, 10, 6),
                    "контрольний огляд",
                    450.0));

    @Test
    void filtersVisitsStrictlyAbovePriceThreshold() {
        List<VetVisit> result =
                VetVisitQueries.visitsAbovePrice(
                        visits,
                        500.0);

        assertEquals(3, result.size());

        assertEquals(
                List.of(
                        "Рекс",
                        "Альфа",
                        "Луна"),
                result.stream()
                        .map(VetVisit::getAnimal)
                        .toList());
    }

    @Test
    void filterExcludesValueExactlyOnThreshold() {
        List<VetVisit> result =
                VetVisitQueries.visitsAbovePrice(
                        visits,
                        500.0);

        assertTrue(
                result.stream()
                        .noneMatch(
                                visit ->
                                        visit.getPrice()
                                                == 500.0));
    }

    @Test
    void rejectsInvalidPriceThreshold() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisitQueries.visitsAbovePrice(
                        visits,
                        -1.0));

        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisitQueries.visitsAbovePrice(
                        visits,
                        Double.NaN));

        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisitQueries.visitsAbovePrice(
                        visits,
                        Double.POSITIVE_INFINITY));
    }

    @Test
    void mapsSpeciesPreservingOrderAndDuplicates() {
        assertEquals(
                List.of(
                        "кіт",
                        "собака",
                        "собака",
                        "кінь",
                        "кіт",
                        "кіт"),
                VetVisitQueries.species(visits));
    }

    @Test
    void groupsVisitsBySpecies() {
        Map<String, Long> result =
                VetVisitQueries.countBySpecies(visits);

        assertEquals(3L, result.get("кіт"));
        assertEquals(2L, result.get("собака"));
        assertEquals(1L, result.get("кінь"));
        assertEquals(3, result.size());
    }

    @Test
    void calculatesPriceSummaryStatistics() {
        DoubleSummaryStatistics statistics =
                VetVisitQueries.priceStatistics(visits);

        assertEquals(6, statistics.getCount());
        assertEquals(
                4400.0,
                statistics.getSum(),
                0.001);
        assertEquals(
                350.0,
                statistics.getMin(),
                0.001);
        assertEquals(
                1200.0,
                statistics.getMax(),
                0.001);
        assertEquals(
                733.333333,
                statistics.getAverage(),
                0.001);
    }

    @Test
    void returnsTopFiveByPriceAndDate() {
        List<VetVisit> result =
                VetVisitQueries.topByPrice(
                        visits,
                        5);

        assertEquals(5, result.size());

        assertEquals(
                List.of(
                        "Альфа",
                        "Рекс",
                        "Луна",
                        "Бім",
                        "Барсик"),
                result.stream()
                        .map(VetVisit::getAnimal)
                        .toList());

        assertEquals(
                List.of(
                        1200.0,
                        1200.0,
                        700.0,
                        500.0,
                        450.0),
                result.stream()
                        .map(VetVisit::getPrice)
                        .toList());
    }

    @Test
    void usesDateAsSecondComparatorCriterion() {
        List<VetVisit> result =
                VetVisitQueries.topByPrice(
                        visits,
                        2);

        assertEquals(
                "Альфа",
                result.get(0).getAnimal());

        assertEquals(
                LocalDate.of(2026, 10, 3),
                result.get(0).getDate());

        assertEquals(
                "Рекс",
                result.get(1).getAnimal());

        assertEquals(
                LocalDate.of(2026, 10, 5),
                result.get(1).getDate());
    }

    @Test
    void handlesTopLimitEdgeCases() {
        assertTrue(
                VetVisitQueries.topByPrice(
                        visits,
                        0)
                        .isEmpty());

        assertEquals(
                visits.size(),
                VetVisitQueries.topByPrice(
                        visits,
                        100)
                        .size());

        assertThrows(
                IllegalArgumentException.class,
                () -> VetVisitQueries.topByPrice(
                        visits,
                        -1));
    }

    @Test
    void findsFirstVisitByOwner() {
        Optional<VetVisit> result =
                VetVisitQueries.findByOwner(
                        visits,
                        "Іван Петренко");

        assertTrue(result.isPresent());

        VetVisit visit =
                result.orElseThrow();

        assertEquals(
                "Мурка",
                visit.getAnimal());

        assertEquals(
                "профілактичний огляд",
                visit.getDiagnosis());

        assertEquals(
                LocalDate.of(2026, 10, 1),
                visit.getDate());
    }

    @Test
    void returnsEmptyOptionalForMissingOwner() {
        Optional<VetVisit> result =
                VetVisitQueries.findByOwner(
                        visits,
                        "Власник якого немає");

        assertTrue(result.isEmpty());
    }

    @Test
    void handlesEmptyInput() {
        List<VetVisit> empty = List.of();

        assertTrue(
                VetVisitQueries.visitsAbovePrice(
                        empty,
                        500.0)
                        .isEmpty());

        assertTrue(
                VetVisitQueries.species(empty)
                        .isEmpty());

        assertTrue(
                VetVisitQueries.countBySpecies(empty)
                        .isEmpty());

        DoubleSummaryStatistics statistics =
                VetVisitQueries.priceStatistics(empty);

        assertEquals(
                0,
                statistics.getCount());

        assertEquals(
                0.0,
                statistics.getSum(),
                0.001);

        assertTrue(
                VetVisitQueries.topByPrice(
                        empty,
                        5)
                        .isEmpty());

        assertTrue(
                VetVisitQueries.findByOwner(
                        empty,
                        "Іван Петренко")
                        .isEmpty());
    }

    @Test
    void queriesDoNotMutateInputCollection() {
        List<VetVisit> before =
                List.copyOf(visits);

        VetVisitQueries.visitsAbovePrice(
                visits,
                500.0);

        VetVisitQueries.species(visits);

        VetVisitQueries.countBySpecies(visits);

        VetVisitQueries.priceStatistics(visits);

        VetVisitQueries.topByPrice(
                visits,
                5);

        VetVisitQueries.findByOwner(
                visits,
                "Іван Петренко");

        assertEquals(
                before,
                visits);
    }
}