package ua.lpnu.kzp.vetclinic;

import java.util.Locale;
import java.util.Objects;

/**
 * Описує один візит до ветеринарної клініки.
 */
public final class VetVisit {

    private final String animal;
    private final String species;
    private final String visitType;
    private final double price;
    private final boolean urgent;

    /**
     * Створює коректний запис про візит до ветеринарної клініки.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param visitType тип візиту
     * @param price вартість візиту
     * @param urgent ознака термінового візиту
     */
    public VetVisit(
            String animal,
            String species,
            String visitType,
            double price,
            boolean urgent) {

        this.animal = requireText(animal, "Тварина");
        this.species = requireText(species, "Вид");
        this.visitType = requireText(visitType, "Тип візиту");

        if (price < 0 || !Double.isFinite(price)) {
            throw new IllegalArgumentException(
                    "Ціна має бути скінченним невід'ємним числом");
        }

        this.price = price;
        this.urgent = urgent;
    }

    /**
     * Повертає назву або кличку тварини.
     *
     * @return тварина
     */
    public String getAnimal() {
        return animal;
    }

    /**
     * Повертає вид тварини.
     *
     * @return вид тварини
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Повертає тип візиту.
     *
     * @return тип візиту
     */
    public String getVisitType() {
        return visitType;
    }

    /**
     * Повертає вартість візиту.
     *
     * @return вартість
     */
    public double getPrice() {
        return price;
    }

    /**
     * Повертає ознаку термінового візиту.
     *
     * @return true, якщо візит терміновий
     */
    public boolean isUrgent() {
        return urgent;
    }

    /**
     * Повертає текстове представлення візиту.
     *
     * @return відформатований опис
     */
    @Override
    public String toString() {
        return String.format(
                Locale.ROOT,
                "%s; %s; %s; %.2f; urgent=%s",
                animal,
                species,
                visitType,
                price,
                urgent);
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " не може бути null");

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(
                    fieldName + " не може бути порожнім");
        }

        return trimmed;
    }
}