package ua.lpnu.kzp.vetclinic;

import java.time.LocalDate;

/**
 * Терміновий візит до ветеринарної клініки.
 */
public final class UrgentVisit extends VetVisit {

    private static final double MINIMUM_URGENT_COST = 1000.0;

    /**
     * Створює терміновий візит.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param owner власник тварини
     * @param date дата візиту
     * @param diagnosis діагноз
     * @param price базова вартість візиту
     */
    public UrgentVisit(
            String animal,
            String species,
            String owner,
            LocalDate date,
            String diagnosis,
            double price) {

        super(
                animal,
                species,
                owner,
                date,
                diagnosis,
                price,
                VisitKind.URGENT);
    }

    /**
     * Обчислює вартість термінового візиту.
     *
     * <p>Мінімальна вартість термінового візиту —
     * 1000.00.
     *
     * @return підсумкова вартість
     */
    @Override
    public double finalCost() {
        return Math.max(
                getPrice(),
                MINIMUM_URGENT_COST);
    }
}