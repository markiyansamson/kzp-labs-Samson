package ua.lpnu.kzp.vetclinic;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

/**
 * Плоский запис ветеринарного візиту для CSV round-trip.
 */
public final class VetVisitRecord {

    @CsvColumn("тварина")
    private final String animal;

    @CsvColumn("вартість")
    private final double cost;

    @CsvColumn("дата")
    private final LocalDate date;

    @CsvColumn("діагноз")
    private final String diagnosis;

    @CsvColumn("власник")
    private final String owner;

    @CsvColumn("вид")
    private final String species;

    /**
     * Створює перевірений запис ветеринарного візиту.
     *
     * @param animal тварина
     * @param species вид тварини
     * @param owner власник
     * @param date дата візиту
     * @param diagnosis діагноз
     * @param cost вартість
     */
    public VetVisitRecord(
            String animal,
            String species,
            String owner,
            LocalDate date,
            String diagnosis,
            double cost) {

        this.animal = requireText(animal, "animal");
        this.species = requireText(species, "species");
        this.owner = requireText(owner, "owner");
        this.date = Objects.requireNonNull(
                date,
                "date не може бути null");
        this.diagnosis = requireText(diagnosis, "diagnosis");

        if (!Double.isFinite(cost) || cost < 0.0) {
            throw new IllegalArgumentException(
                    "cost має бути скінченним невід'ємним числом");
        }

        this.cost = cost;
    }

    /**
     * Створює плоский CSV-запис із предметного візиту.
     *
     * @param visit предметний візит
     * @return CSV-запис
     */
    public static VetVisitRecord fromVisit(VetVisit visit) {
        Objects.requireNonNull(
                visit,
                "visit не може бути null");

        return new VetVisitRecord(
                visit.getAnimal(),
                visit.getSpecies(),
                visit.getOwner(),
                visit.getDate(),
                visit.getDiagnosis(),
                visit.getPrice());
    }

    /**
     * Створює запис з одного CSV-рядка.
     *
     * @param line CSV-рядок
     * @return відновлений запис
     * @throws DataStorageException якщо рядок некоректний
     */
    public static VetVisitRecord fromCsv(String line)
            throws DataStorageException {

        return fromFields(CsvParser.parse(line));
    }

    /**
     * Створює запис із полів у порядку експорту.
     *
     * @param fields CSV-поля
     * @return відновлений запис
     * @throws DataStorageException якщо поля некоректні
     */
    public static VetVisitRecord fromFields(List<String> fields)
            throws DataStorageException {

        Objects.requireNonNull(fields, "fields");

        if (fields.size() != 6) {
            throw new DataStorageException(
                    "Очікується шість CSV-полів");
        }

        try {
            String animal = fields.get(0);
            double cost = Double.parseDouble(fields.get(1));
            LocalDate date = LocalDate.parse(fields.get(2));
            String diagnosis = fields.get(3);
            String owner = fields.get(4);
            String species = fields.get(5);

            return new VetVisitRecord(
                    animal,
                    species,
                    owner,
                    date,
                    diagnosis,
                    cost);

        } catch (NumberFormatException exception) {
            throw new DataStorageException(
                    "Некоректне поле cost",
                    exception);

        } catch (DateTimeParseException exception) {
            throw new DataStorageException(
                    "Некоректне поле date",
                    exception);

        } catch (IllegalArgumentException exception) {
            throw new DataStorageException(
                    "Некоректний запис ветеринарного візиту",
                    exception);
        }
    }

    /**
     * Повертає назву тварини.
     *
     * @return назва тварини
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
     * Повертає власника тварини.
     *
     * @return власник
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Повертає дату візиту.
     *
     * @return дата візиту
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Повертає діагноз.
     *
     * @return діагноз
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Повертає вартість візиту.
     *
     * @return вартість візиту
     */
    public double getCost() {
        return cost;
    }

    private static String requireText(
            String value,
            String fieldName) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " не може бути порожнім");
        }

        return value.trim();
    }

    /**
     * Порівнює записи за всіма полями.
     *
     * @param other інший об'єкт
     * @return {@code true}, якщо записи однакові
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof VetVisitRecord visit)) {
            return false;
        }

        return Double.compare(cost, visit.cost) == 0
                && animal.equals(visit.animal)
                && species.equals(visit.species)
                && owner.equals(visit.owner)
                && date.equals(visit.date)
                && diagnosis.equals(visit.diagnosis);
    }

    /**
     * Повертає хеш-код запису.
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                animal,
                species,
                owner,
                date,
                diagnosis,
                cost);
    }
}