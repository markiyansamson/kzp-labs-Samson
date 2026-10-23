package ua.lpnu.kzp.vetclinic;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Locale;
import java.util.Objects;

/**
 * Спільний базовий тип візитів до ветеринарної клініки.
 */
public abstract class VetVisit {

    private final String animal;
    private final String species;
    private final String visitType;
    private final double price;
    private final VisitKind kind;

    /**
     * Створює коректний візит до ветеринарної клініки.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param visitType тип візиту
     * @param price базова вартість візиту
     * @param kind категорія візиту
     */
    @SuppressFBWarnings(
            value = "CT_CONSTRUCTOR_THROW",
            justification = "Конструктор навмисно відхиляє некоректний предметний стан")
    protected VetVisit(
            String animal,
            String species,
            String visitType,
            double price,
            VisitKind kind) {

        this.animal = requireText(animal, "animal");
        this.species = requireText(species, "species");
        this.visitType = requireText(visitType, "visitType");

        if (price < 0 || !Double.isFinite(price)) {
            throw new IllegalArgumentException(
                    "price має бути скінченним невід'ємним числом");
        }

        this.price = price;
        this.kind = Objects.requireNonNull(
                kind,
                "kind не може бути null");
    }

    /**
     * Повертає назву або кличку тварини.
     *
     * @return назва або кличка тварини
     */
    public final String getAnimal() {
        return animal;
    }

    /**
     * Повертає вид тварини.
     *
     * @return вид тварини
     */
    public final String getSpecies() {
        return species;
    }

    /**
     * Повертає тип візиту.
     *
     * @return тип візиту
     */
    public final String getVisitType() {
        return visitType;
    }

    /**
     * Повертає базову вартість візиту.
     *
     * @return базова вартість
     */
    public final double getPrice() {
        return price;
    }

    /**
     * Повертає категорію візиту.
     *
     * @return категорія візиту
     */
    public final VisitKind getKind() {
        return kind;
    }

    /**
     * Повертає ознаку термінового візиту.
     *
     * @return true, якщо візит терміновий
     */
    public final boolean isUrgent() {
        return kind == VisitKind.URGENT;
    }

    /**
     * Обчислює підсумкову вартість відповідно до конкретного підтипу.
     *
     * @return підсумкова вартість
     */
    public abstract double finalCost();

    /**
     * Створює конкретний підтип візиту з одного CSV-рядка.
     *
     * @param line рядок у форматі animal;species;visitType;price;urgent
     * @return RoutineVisit або UrgentVisit через спільний тип VetVisit
     * @throws IllegalArgumentException якщо структура або значення некоректні
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

        boolean urgent = Boolean.parseBoolean(urgentText);

        if (urgent) {
            return new UrgentVisit(
                    fields[0],
                    fields[1],
                    fields[2],
                    price);
        }

        return new RoutineVisit(
                fields[0],
                fields[1],
                fields[2],
                price);
    }

    /**
     * Порівнює візити за конкретним підтипом і спільними даними.
     *
     * @param other інший об'єкт
     * @return true, якщо об'єкти логічно рівні
     */
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        VetVisit visit = (VetVisit) other;

        return Double.compare(price, visit.price) == 0
                && animal.equals(visit.animal)
                && species.equals(visit.species)
                && visitType.equals(visit.visitType);
    }

    /**
     * Повертає хеш-код, узгоджений із equals.
     *
     * @return хеш-код візиту
     */
    @Override
    public final int hashCode() {
        return Objects.hash(
                getClass(),
                animal,
                species,
                visitType,
                price);
    }

    /**
     * Повертає текстове представлення візиту.
     *
     * @return відформатований опис візиту
     */
    @Override
    public final String toString() {
        return String.format(
                Locale.ROOT,
                "%s; %s; %s; %.2f; urgent=%s",
                animal,
                species,
                visitType,
                price,
                isUrgent());
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