package ua.lpnu.kzp.vetclinic;

/**
 * Категорія візиту до ветеринарної клініки.
 */
public enum VisitKind {

    ROUTINE("звичайний"),
    URGENT("терміновий");

    private final String label;

    VisitKind(String label) {
        this.label = label;
    }

    /**
     * Повертає текстове представлення категорії.
     *
     * @return назва категорії
     */
    public String label() {
        return label;
    }
}