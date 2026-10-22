package ua.lpnu.kzp.vetclinic;

/**
 * Звичайний плановий візит до ветеринарної клініки.
 */
public final class RoutineVisit extends VetVisit {

    /**
     * Створює звичайний візит.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param visitType тип візиту
     * @param price базова вартість
     */
    public RoutineVisit(
            String animal,
            String species,
            String visitType,
            double price) {

        super(
                animal,
                species,
                visitType,
                price,
                VisitKind.ROUTINE);
    }

    /**
     * Для звичайного візиту підсумкова вартість
     * дорівнює базовій вартості.
     *
     * @return підсумкова вартість
     */
    @Override
    public double finalCost() {
        return getPrice();
    }
}