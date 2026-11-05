package ua.lpnu.kzp.vetclinic;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Містить чисті Stream API запити до колекції
 * ветеринарних візитів.
 *
 * <p>Варіант №21:
 * filter — вартість понад поріг;
 * map — види тварин;
 * groupingBy — кількість за видом;
 * statistics — вартість;
 * top-N — вартість за спаданням і дата за зростанням;
 * Optional — пошук за власником.
 */
public final class VetVisitQueries {

    /**
     * Забороняє створення екземплярів службового класу.
     */
    private VetVisitQueries() {
    }

    /**
     * Повертає візити, базова вартість яких
     * строго перевищує заданий поріг.
     *
     * @param visits список візитів
     * @param threshold поріг вартості
     * @return візити з ціною більшою за поріг
     * @throws IllegalArgumentException якщо поріг некоректний
     */
    public static List<VetVisit> visitsAbovePrice(
            List<VetVisit> visits,
            double threshold) {

        if (threshold < 0
                || !Double.isFinite(threshold)) {
            throw new IllegalArgumentException(
                    "Поріг вартості має бути "
                            + "скінченним невід'ємним числом");
        }

        return streamOf(visits)
                .filter(
                        visit ->
                                visit.getPrice()
                                        > threshold)
                .toList();
    }

    /**
     * Повертає види тварин у початковому порядку.
     *
     * @param visits список візитів
     * @return список видів тварин
     */
    public static List<String> species(
            List<VetVisit> visits) {

        return streamOf(visits)
                .map(VetVisit::getSpecies)
                .toList();
    }

    /**
     * Групує візити за видом тварини
     * та підраховує кількість записів.
     *
     * @param visits список візитів
     * @return карта вид тварини -> кількість візитів
     */
    public static Map<String, Long> countBySpecies(
            List<VetVisit> visits) {

        return streamOf(visits)
                .collect(Collectors.groupingBy(
                        VetVisit::getSpecies,
                        TreeMap::new,
                        Collectors.counting()));
    }

    /**
     * Обчислює зведену статистику
     * базової вартості візитів.
     *
     * @param visits список візитів
     * @return статистика вартості
     */
    public static DoubleSummaryStatistics priceStatistics(
            List<VetVisit> visits) {

        return streamOf(visits)
                .collect(
                        Collectors.summarizingDouble(
                                VetVisit::getPrice));
    }

    /**
     * Повертає перші N візитів після
     * складеного впорядкування.
     *
     * <p>Основний критерій:
     * вартість за спаданням.
     *
     * <p>Другий критерій:
     * дата за зростанням.
     *
     * @param visits список візитів
     * @param limit максимальна кількість результатів
     * @return впорядкований top-N
     * @throws IllegalArgumentException якщо limit від'ємний
     */
    public static List<VetVisit> topByPrice(
            List<VetVisit> visits,
            int limit) {

        if (limit < 0) {
            throw new IllegalArgumentException(
                    "Ліміт не може бути від'ємним");
        }

        Comparator<VetVisit> byPriceAndDate =
                Comparator.comparingDouble(
                                VetVisit::getPrice)
                        .reversed()
                        .thenComparing(
                                VetVisit::getDate);

        return streamOf(visits)
                .sorted(byPriceAndDate)
                .limit(limit)
                .toList();
    }

    /**
     * Шукає перший візит за ім'ям власника.
     *
     * <p>Якщо один власник має кілька візитів,
     * повертається перший запис у початковому
     * порядку колекції.
     *
     * @param visits список візитів
     * @param owner власник тварини
     * @return знайдений візит або Optional.empty()
     */
    public static Optional<VetVisit> findByOwner(
            List<VetVisit> visits,
            String owner) {

        Objects.requireNonNull(
                owner,
                "Власник не може бути null");

        String normalizedOwner = owner.trim();

        if (normalizedOwner.isEmpty()) {
            throw new IllegalArgumentException(
                    "Власник не може бути порожнім");
        }

        return streamOf(visits)
                .filter(
                        visit ->
                                visit.getOwner()
                                        .equals(normalizedOwner))
                .findFirst();
    }

    /**
     * Перевіряє список і створює новий Stream.
     *
     * @param visits список візитів
     * @return потік елементів
     */
    private static Stream<VetVisit> streamOf(
            List<VetVisit> visits) {

        return Objects.requireNonNull(
                        visits,
                        "Список візитів не може бути null")
                .stream();
    }
}