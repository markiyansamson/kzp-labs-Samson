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
 * Містить чисті Stream API запити до колекції ветеринарних візитів.
 *
 * <p>Методи не змінюють вхідну колекцію та для кожного незалежного
 * запиту створюють новий потік.
 */
public final class VetVisitQueries {

    /**
     * Забороняє створення екземплярів службового класу.
     */
    private VetVisitQueries() {
    }

    /**
     * Повертає візити, базова вартість яких строго перевищує заданий поріг.
     *
     * @param visits вхідна колекція візитів
     * @param threshold поріг вартості
     * @return візити з ціною, більшою за поріг
     * @throws IllegalArgumentException якщо поріг некоректний
     */
    public static List<VetVisit> visitsAbovePrice(
            List<VetVisit> visits,
            double threshold) {

        if (threshold < 0 || !Double.isFinite(threshold)) {
            throw new IllegalArgumentException(
                    "Поріг вартості має бути скінченним невід'ємним числом");
        }

        return streamOf(visits)
                .filter(visit -> visit.getPrice() > threshold)
                .toList();
    }

    /**
     * Повертає види тварин для всіх візитів у початковому порядку.
     *
     * @param visits вхідна колекція візитів
     * @return список видів тварин
     */
    public static List<String> species(
            List<VetVisit> visits) {

        return streamOf(visits)
                .map(VetVisit::getSpecies)
                .toList();
    }

    /**
     * Групує візити за видом тварини та підраховує кількість у кожній групі.
     *
     * <p>Для стабільного порядку ключів використовується {@link TreeMap}.
     *
     * @param visits вхідна колекція візитів
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
     * Обчислює зведену статистику базової вартості візитів.
     *
     * @param visits вхідна колекція візитів
     * @return статистика count, sum, min, max та average
     */
    public static DoubleSummaryStatistics priceStatistics(
            List<VetVisit> visits) {

        return streamOf(visits)
                .collect(Collectors.summarizingDouble(
                        VetVisit::getPrice));
    }

    /**
     * Повертає перші N візитів після складеного впорядкування.
     *
     * <p>Основний критерій — вартість за спаданням.
     * Другий критерій — назва тварини за зростанням.
     *
     * @param visits вхідна колекція візитів
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

        Comparator<VetVisit> byPriceAndAnimal =
                Comparator.comparingDouble(VetVisit::getPrice)
                        .reversed()
                        .thenComparing(VetVisit::getAnimal);

        return streamOf(visits)
                .sorted(byPriceAndAnimal)
                .limit(limit)
                .toList();
    }

    /**
     * Шукає перший візит тварини із заданим ім'ям.
     *
     * <p>Якщо ім'я дублюється, повертається перший запис
     * у початковому порядку колекції.
     *
     * @param visits вхідна колекція візитів
     * @param animal назва або кличка тварини
     * @return знайдений візит або {@link Optional#empty()}
     */
    public static Optional<VetVisit> findByAnimal(
            List<VetVisit> visits,
            String animal) {

        Objects.requireNonNull(
                animal,
                "Назва тварини не може бути null");

        return streamOf(visits)
                .filter(visit -> visit.getAnimal().equals(animal))
                .findFirst();
    }

    /**
     * Перевіряє вхідну колекцію та створює новий потік.
     *
     * @param visits колекція візитів
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