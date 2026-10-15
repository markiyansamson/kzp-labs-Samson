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

        this.animal = requireText(animal, "animal");
        this.species = requireText(species, "species");
        this.visitType = requireText(visitType, "visitType");

        if (price < 0 || !Double.isFinite(price)) {
            throw new IllegalArgumentException(
                    "price має бути скінченним невід'ємним числом");
        }

        this.price = price;
        this.urgent = urgent;
    }

    /**
     * Повертає назву або кличку тварини.
     *
     * @return назва або кличка тварини
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
     * @return вартість візиту
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
     * Створює візит із одного CSV-рядка.
     *
     * @param line рядок у форматі animal;species;visitType;price;urgent
     * @return створений об'єкт VetVisit
     * @throws IllegalArgumentException якщо структура рядка
     *                                  або значення некоректні
     */
    public static VetVisit fromCsv(String line) {
        Objects.requireNonNull(
                line,
                "Рядок не може бути null");

        String[] fields = line.split(";", -1);

        if (fields.length != 5) {
            throw new IllegalArgumentException(
                    "очікується 5 полів, отримано "
                            + fields.length);
        }

        double price;

        try {
            price = Double.parseDouble(
                    fields[3].trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "поле price має бути числом",
                    exception);
        }

        String urgentText = fields[4].trim();

        if (!urgentText.equalsIgnoreCase("true")
                && !urgentText.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException(
                    "поле urgent має бути true або false");
        }

        boolean urgent =
                Boolean.parseBoolean(urgentText);

        return new VetVisit(
                fields[0],
                fields[1],
                fields[2],
                price,
                urgent);
    }

    /**
     * Повертає текстове представлення візиту.
     *
     * @return відформатований опис візиту
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

    /**
     * Перевіряє текстове поле.
     *
     * @param value значення поля
     * @param fieldName назва поля
     * @return очищене текстове значення
     */
    private static String requireText(
            String value,
            String fieldName) {

        Objects.requireNonNull(
                value,
                "поле " + fieldName + " не може бути null");

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(
                    "поле " + fieldName
                            + " не може бути порожнім");
        }

        return trimmed;
    }
}