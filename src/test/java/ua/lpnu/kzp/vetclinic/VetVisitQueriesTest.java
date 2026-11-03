package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                    "огляд",
                    350.0),
            new RoutineVisit(
                    "Бім",
                    "собака",
                    "вакцинація",
                    500.0),
            new UrgentVisit(
                    "Рекс",
                    "собака",
                    "невідкладна допомога",
                    1200.0),
            new RoutineVisit(
                    "Альфа",
                    "кінь",
                    "огляд",
                    1200.0),
            new RoutineVisit(
                    "Луна",
                    "кіт",
                    "аналізи",
                    700.0),
            new RoutineVisit(
                    "Рекс",
                    "кіт",
                    "контроль",
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
        assertEquals(4400.0, statistics.getSum(), 0.001);
        assertEquals(350.0, statistics.getMin(), 0.001);
        assertEquals(1200.0, statistics.getMax(), 0.001);
        assertEquals(
                733.333333,
                statistics.getAverage(),
                0.001);
    }

    @Test
    void returnsTopFiveByPriceAndAnimal() {
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
                        "Рекс"),
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
    void findsFirstVisitByAnimal() {
        Optional<VetVisit> result =
                VetVisitQueries.findByAnimal(
                        visits,
                        "Рекс");

        assertTrue(result.isPresent());

        assertEquals(
                "невідкладна допомога",
                result.orElseThrow().getVisitType());

        assertEquals(
                1200.0,
                result.orElseThrow().getPrice(),
                0.001);
    }

    @Test
    void returnsEmptyOptionalForMissingAnimal() {
        Optional<VetVisit> result =
                VetVisitQueries.findByAnimal(
                        visits,
                        "Барсик");

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

        assertEquals(0, statistics.getCount());
        assertEquals(0.0, statistics.getSum(), 0.001);

        assertTrue(
                VetVisitQueries.topByPrice(
                        empty,
                        5)
                        .isEmpty());

        assertTrue(
                VetVisitQueries.findByAnimal(
                        empty,
                        "Рекс")
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

        VetVisitQueries.findByAnimal(
                visits,
                "Рекс");

        assertEquals(before, visits);
    }
}