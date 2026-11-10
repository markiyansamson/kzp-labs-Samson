package ua.lpnu.kzp.vetclinic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Керує основним процесом обробки даних ветеринарної клініки.
 */
public final class VetClinicController {

    /**
 * Створює контролер обробки даних ветеринарної клініки.
 */
public VetClinicController() {
}

    /**
     * Виконує повний цикл обробки вхідного файла.
     *
     * @param arguments параметри запуску
     * @return сформований звіт
     * @throws IOException якщо виникла помилка роботи з файлами
     */
    public String run(AppArguments arguments)
            throws IOException {

        List<String> lines = Files.readAllLines(
                arguments.inputPath(),
                StandardCharsets.UTF_8);

        Repository<VetVisit> visitRepository =
                new Repository<>();

        List<String> errors = new ArrayList<>();

        for (int index = 0; index < lines.size(); index++) {
            int lineNumber = index + 1;
            String line = lines.get(index);

            if (line.isBlank()) {
                errors.add(
                        "Рядок " + lineNumber
                                + ": порожній рядок");
                continue;
            }

            try {
                visitRepository.add(
                        VetVisit.fromCsv(line));
            } catch (IllegalArgumentException exception) {
                errors.add(
                        "Рядок " + lineNumber + ": "
                                + exception.getMessage());
            }
        }

        List<VetVisit> visits =
                visitRepository.all();

        performCsvRoundTrip(
                visits,
                arguments.outputPath());

        PriceUrgency summary =
                calculateSummary(visits);

        int validCount = visits.size();

        double averagePrice =
                validCount == 0
                        ? 0.0
                        : summary.totalPrice() / validCount;

        String report = buildReport(
                validCount,
                averagePrice,
                summary.totalPrice(),
                summary.urgentCount(),
                errors);

        writeReport(
                arguments.outputPath(),
                report);

        return report;
    }

    /**
     * Експортує коректні візити у CSV та перевіряє round-trip.
     *
     * @param visits коректні предметні візити
     * @param reportPath шлях до текстового звіту
     * @throws IOException якщо CSV round-trip завершився помилкою
     */
    private static void performCsvRoundTrip(
            List<VetVisit> visits,
            Path reportPath) throws IOException {

        List<VetVisitRecord> original = visits.stream()
                .map(VetVisitRecord::fromVisit)
                .toList();

        Path csvPath =
                reportPath.resolveSibling(
                        "vet-visits.csv");

        try {
            CsvExporter.write(
                    csvPath,
                    VetVisitRecord.class,
                    original);

            List<VetVisitRecord> restored =
                    VetVisitCsvStorage.read(csvPath);

            if (!original.equals(restored)) {
                throw new DataStorageException(
                        "Round-trip не відновив початкові записи");
            }

        } catch (DataStorageException exception) {
            throw new IOException(
                    "Помилка CSV round-trip",
                    exception);
        }
    }

    static PriceUrgency calculateSummary(
            List<VetVisit> visits) {

        double totalPrice = visits.stream()
                .mapToDouble(VetVisit::finalCost)
                .sum();

        int urgentCount = Math.toIntExact(
                visits.stream()
                        .filter(VetVisit::isUrgent)
                        .count());

        return new PriceUrgency(
                totalPrice,
                urgentCount);
    }

    private static void writeReport(
            Path outputPath,
            String report) throws IOException {

        Path outputDirectory =
                outputPath.getParent();

        if (outputDirectory != null) {
            Files.createDirectories(
                    outputDirectory);
        }

        Files.writeString(
                outputPath,
                report,
                StandardCharsets.UTF_8);
    }

    private static String buildReport(
            int validCount,
            double averagePrice,
            double totalRevenue,
            int urgentVisits,
            List<String> errors) {

        StringBuilder report =
                new StringBuilder();

        report.append(
                "Звіт: Ветклініка%n".formatted());

        report.append(String.format(
                Locale.ROOT,
                "Коректних записів: %d%n",
                validCount));

        report.append(String.format(
                Locale.ROOT,
                "Середня вартість: %.2f%n",
                averagePrice));

        report.append(String.format(
                Locale.ROOT,
                "Загальний виторг: %.2f%n",
                totalRevenue));

        report.append(String.format(
                Locale.ROOT,
                "Ургентних візитів: %d%n",
                urgentVisits));

        report.append(String.format(
                Locale.ROOT,
                "Помилок: %d%n",
                errors.size()));

        if (!errors.isEmpty()) {
            report.append(
                    "%nПомилки:%n".formatted());

            for (String error : errors) {
                report.append(error)
                        .append("%n".formatted());
            }
        }

        return report.toString();
    }
}