package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class VetClinicStreamRegressionTest {

    @Test
    void streamSummaryMatchesPreviousLoopImplementation() {
        List<VetVisit> visits = List.of(
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
                        1200.0));

        double legacyTotalPrice = 0.0;
        int legacyUrgentCount = 0;

        for (VetVisit visit : visits) {
            legacyTotalPrice += visit.finalCost();

            if (visit.isUrgent()) {
                legacyUrgentCount++;
            }
        }

        PriceUrgency streamSummary =
                VetClinicController.calculateSummary(visits);

        assertEquals(
                legacyTotalPrice,
                streamSummary.totalPrice(),
                0.001);

        assertEquals(
                legacyUrgentCount,
                streamSummary.urgentCount());
    }
}