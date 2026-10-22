package ua.lpnu.kzp.vetclinic;

/**
 * Категорія візиту до ветеринарної клініки.
 */
public enum VisitKind {

    /**
     * Звичайний плановий візит.
     */
    ROUTINE("звичайний"),

    /**
     * Терміновий візит.
     */
    URGENT("терміновий");

    private final String label;

    /**
     * Створює категорію з текстовим поданням.
     *
     * @param label текстове подання категорії
     */
    VisitKind(String label) {
        this.label = label;
    }

    /**
     * Повертає текстове подання категорії для звіту.
     *
     * @return текстове подання категорії
     */
    public String label() {
        return label;
    }
}