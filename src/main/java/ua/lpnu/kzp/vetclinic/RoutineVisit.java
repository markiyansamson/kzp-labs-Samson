package ua.lpnu.kzp.vetclinic;

import java.time.LocalDate;

/**
 * Звичайний плановий візит до ветеринарної клініки.
 */
public final class RoutineVisit extends VetVisit {

    /**
     * Створює плановий візит.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param owner власник тварини
     * @param date дата візиту
     * @param diagnosis діагноз
     * @param price базова вартість візиту
     */
    public RoutineVisit(
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
                VisitKind.ROUTINE);
    }

    /**
     * Для планового візиту підсумкова вартість
     * дорівнює базовій вартості.
     *
     * @return підсумкова вартість
     */
    @Override
    public double finalCost() {
        return getPrice();
    }
}