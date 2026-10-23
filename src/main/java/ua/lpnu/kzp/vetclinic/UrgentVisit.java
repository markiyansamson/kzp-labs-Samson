package ua.lpnu.kzp.vetclinic;

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
     * @param visitType тип візиту
     * @param price базова вартість
     */
    public UrgentVisit(
            String animal,
            String species,
            String visitType,
            double price) {

        super(
                animal,
                species,
                visitType,
                price,
                VisitKind.URGENT);
    }

    /**
     * Повертає підсумкову вартість термінового візиту.
     * Для термінового візиту діє мінімальна вартість 1000 грн.
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