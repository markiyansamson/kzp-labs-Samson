package ua.lpnu.kzp.vetclinic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Консольна програма для обробки даних ветеринарної клініки.
 */
public final class Main {

    private static final String VERSION = "1.0.0";
    private static final Path DEFAULT_INPUT = Path.of("data", "input.csv");
    private static final Path DEFAULT_OUTPUT = Path.of("out", "report.txt");

    /**
     * Забороняє створення екземплярів службового класу.
     */
    private Main() {
    }

    /**
     * Точка входу до програми.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        try {
            Path inputPath = DEFAULT_INPUT;
            Path outputPath = DEFAULT_OUTPUT;

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--help" -> {
                        printHelp();
                        return;
                    }

                    case "--version" -> {
                        System.out.println(VERSION);
                        return;
                    }

                    case "--input" -> {
                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException(
                                    "Після --input потрібно вказати шлях до файла");
                        }

                        inputPath = Path.of(args[++i]);
                    }

                    case "--output" -> {
                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException(
                                    "Після --output потрібно вказати шлях до файла");
                        }

                        outputPath = Path.of(args[++i]);
                    }

                    default -> throw new IllegalArgumentException(
                            "Невідомий аргумент: " + args[i]);
                }
            }

            List<String> lines =
                    Files.readAllLines(inputPath, StandardCharsets.UTF_8);

            List<VetVisit> visits = new ArrayList<>();
            List<String> errors = new ArrayList<>();

            for (int index = 0; index < lines.size(); index++) {
                int lineNumber = index + 1;
                String line = lines.get(index);

                if (line.isBlank()) {
                    errors.add(
                            "Рядок " + lineNumber + ": порожній рядок");
                    continue;
                }

                try {
                    VetVisit visit = VetVisit.fromCsv(line);
                    visits.add(visit);
                } catch (IllegalArgumentException exception) {
                    errors.add(
                            "Рядок " + lineNumber + ": "
                                    + exception.getMessage());
                }
            }

            PriceUrgency summary = calculateSummary(visits);

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

            System.out.print(report);

            Path outputDirectory = outputPath.getParent();

            if (outputDirectory != null) {
                Files.createDirectories(outputDirectory);
            }

            Files.writeString(
                    outputPath,
                    report,
                    StandardCharsets.UTF_8);

        } catch (IOException exception) {
            System.err.printf(
                    "Помилка роботи з файлом: %s%n",
                    exception.getMessage());

        } catch (IllegalArgumentException exception) {
            System.err.printf(
                    "Помилка: %s%n",
                    exception.getMessage());

            printHelp();
        }
    }

    /**
     * Обчислює загальний виторг і кількість термінових візитів.
     *
     * @param visits список коректних візитів
     * @return підсумкові значення
     */
    private static PriceUrgency calculateSummary(
            List<VetVisit> visits) {

        double totalPrice = 0.0;
        int urgentCount = 0;

        for (VetVisit visit : visits) {
            totalPrice += visit.getPrice();

            if (visit.isUrgent()) {
                urgentCount++;
            }
        }

        return new PriceUrgency(
                totalPrice,
                urgentCount);
    }

    /**
     * Формує текстовий звіт.
     *
     * @param validCount кількість коректних записів
     * @param averagePrice середня вартість
     * @param totalRevenue загальний виторг
     * @param urgentVisits кількість ургентних візитів
     * @param errors список помилок
     * @return готовий текст звіту
     */
    private static String buildReport(
            int validCount,
            double averagePrice,
            double totalRevenue,
            int urgentVisits,
            List<String> errors) {

        StringBuilder report = new StringBuilder();

        report.append("Звіт: Ветклініка%n".formatted());

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
            report.append("%nПомилки:%n".formatted());

            for (String error : errors) {
                report.append(error)
                        .append("%n".formatted());
            }
        }

        return report.toString();
    }

    /**
     * Виводить довідку про використання програми.
     */
    private static void printHelp() {
        System.out.println(
                "Використання: "
                        + "java Main "
                        + "[--help] "
                        + "[--version] "
                        + "[--input <файл>] "
                        + "[--output <файл>]");
    }
}