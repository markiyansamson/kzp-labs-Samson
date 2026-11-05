package ua.lpnu.kzp.vetclinic;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * Спільний базовий тип візитів до ветеринарної клініки.
 *
 * <p>Варіант №21: тварина, вид, власник, дата,
 * діагноз і вартість.
 */
public abstract class VetVisit {

    private final String animal;
    private final String species;
    private final String owner;
    private final LocalDate date;
    private final String diagnosis;
    private final double price;
    private final VisitKind kind;

    /**
     * Створює коректний візит до ветеринарної клініки.
     *
     * @param animal кличка або назва тварини
     * @param species вид тварини
     * @param owner власник тварини
     * @param date дата візиту
     * @param diagnosis діагноз
     * @param price базова вартість візиту
     * @param kind категорія візиту
     */
    @SuppressFBWarnings(
            value = "CT_CONSTRUCTOR_THROW",
            justification = "Constructor validates immutable domain data")
    protected VetVisit(
            String animal,
            String species,
            String owner,
            LocalDate date,
            String diagnosis,
            double price,
            VisitKind kind) {

        this.animal = requireText(animal, "animal");
        this.species = requireText(species, "species");
        this.owner = requireText(owner, "owner");
        this.date = Objects.requireNonNull(
                date,
                "поле date не може бути null");
        this.diagnosis = requireText(
                diagnosis,
                "diagnosis");

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
     * Повертає кличку або назву тварини.
     *
     * @return тварина
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
     * Повертає власника тварини.
     *
     * @return власник
     */
    public final String getOwner() {
        return owner;
    }

    /**
     * Повертає дату візиту.
     *
     * @return дата візиту
     */
    public final LocalDate getDate() {
        return date;
    }

    /**
     * Повертає діагноз.
     *
     * @return діагноз
     */
    public final String getDiagnosis() {
        return diagnosis;
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
     * Обчислює підсумкову вартість відповідно
     * до конкретного підтипу.
     *
     * @return підсумкова вартість
     */
    public abstract double finalCost();

    /**
     * Створює конкретний підтип візиту з CSV-рядка.
     *
     * <p>Формат:
     * animal;species;owner;date;diagnosis;price;urgent
     *
     * @param line CSV-рядок
     * @return створений конкретний підтип VetVisit
     * @throws IllegalArgumentException якщо структура
     *                                  або значення некоректні
     */
    public static VetVisit fromCsv(String line) {
        Objects.requireNonNull(
                line,
                "Рядок не може бути null");

        String[] fields = line.split(";", -1);

        if (fields.length != 7) {
            throw new IllegalArgumentException(
                    "очікується 7 полів, отримано "
                            + fields.length);
        }

        LocalDate date;

        try {
            date = LocalDate.parse(
                    fields[3].trim());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "поле date має бути датою у форматі yyyy-MM-dd",
                    exception);
        }

        double price;

        try {
            price = Double.parseDouble(
                    fields[5].trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "поле price має бути числом",
                    exception);
        }

        String urgentText = fields[6].trim();

        if (!urgentText.equalsIgnoreCase("true")
                && !urgentText.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException(
                    "поле urgent має бути true або false");
        }

        boolean urgent =
                Boolean.parseBoolean(urgentText);

        if (urgent) {
            return new UrgentVisit(
                    fields[0],
                    fields[1],
                    fields[2],
                    date,
                    fields[4],
                    price);
        }

        return new RoutineVisit(
                fields[0],
                fields[1],
                fields[2],
                date,
                fields[4],
                price);
    }

    /**
     * Порівнює візити за конкретним підтипом
     * та всіма предметними даними.
     *
     * @param other інший об'єкт
     * @return true, якщо об'єкти логічно рівні
     */
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null
                || getClass() != other.getClass()) {
            return false;
        }

        VetVisit visit = (VetVisit) other;

        return Double.compare(
                price,
                visit.price) == 0
                && animal.equals(visit.animal)
                && species.equals(visit.species)
                && owner.equals(visit.owner)
                && date.equals(visit.date)
                && diagnosis.equals(visit.diagnosis);
    }

    /**
     * Повертає хеш-код, узгоджений із equals.
     *
     * @return хеш-код
     */
    @Override
    public final int hashCode() {
        return Objects.hash(
                getClass(),
                animal,
                species,
                owner,
                date,
                diagnosis,
                price);
    }

    /**
     * Повертає стабільне текстове представлення візиту.
     *
     * @return опис візиту
     */
    @Override
    public final String toString() {
        return String.format(
                Locale.ROOT,
                "%s; %s; %s; %s; %s; %.2f; urgent=%s",
                animal,
                species,
                owner,
                date,
                diagnosis,
                price,
                isUrgent());
    }

    /**
     * Перевіряє текстове поле.
     *
     * @param value значення
     * @param fieldName назва поля
     * @return очищене значення
     */
    private static String requireText(
            String value,
            String fieldName) {

        Objects.requireNonNull(
                value,
                "поле " + fieldName
                        + " не може бути null");

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(
                    "поле " + fieldName
                            + " не може бути порожнім");
        }

        return trimmed;
    }
}