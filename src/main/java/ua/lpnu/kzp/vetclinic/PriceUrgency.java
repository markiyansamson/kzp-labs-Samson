package ua.lpnu.kzp.vetclinic;

/**
 * Зберігає незмінний підсумок вартості та термінових візитів.
 *
 * @param totalPrice загальна вартість коректних візитів
 * @param urgentCount кількість термінових візитів
 */
public record PriceUrgency(
        double totalPrice,
        int urgentCount) {

    /**
     * Перевіряє коректність підсумкових значень.
     */
    public PriceUrgency {
        if (totalPrice < 0 || !Double.isFinite(totalPrice)) {
            throw new IllegalArgumentException(
                    "Загальна вартість має бути скінченним невід'ємним числом");
        }

        if (urgentCount < 0) {
            throw new IllegalArgumentException(
                    "Кількість термінових візитів не може бути від'ємною");
        }
    }
}