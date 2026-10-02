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

            int validCount = 0;
            double totalRevenue = 0.0;
            int urgentVisits = 0;

            List<String> errors = new ArrayList<>();

            for (int index = 0; index < lines.size(); index++) {
                int lineNumber = index + 1;
                String line = lines.get(index);

                if (line.isBlank()) {
                    errors.add("Рядок " + lineNumber + ": порожній рядок");
                    continue;
                }

                String[] fields = line.split(";", -1);

                if (fields.length != 5) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": очікується 5 полів, отримано "
                                    + fields.length);
                    continue;
                }

                String animal = fields[0].trim();
                String species = fields[1].trim();
                String visitType = fields[2].trim();

                if (animal.isBlank()) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": поле animal не може бути порожнім");
                    continue;
                }

                if (species.isBlank()) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": поле species не може бути порожнім");
                    continue;
                }

                if (visitType.isBlank()) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": поле visitType не може бути порожнім");
                    continue;
                }

                double price;

                try {
                    price = Double.parseDouble(fields[3].trim());
                } catch (NumberFormatException exception) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": поле price має бути числом");
                    continue;
                }

                if (price < 0 || !Double.isFinite(price)) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": price має бути скінченним"
                                    + " невід'ємним числом");
                    continue;
                }

                String urgentText = fields[4].trim();

                if (!urgentText.equalsIgnoreCase("true")
                        && !urgentText.equalsIgnoreCase("false")) {
                    errors.add(
                            "Рядок " + lineNumber
                                    + ": поле urgent має бути true або false");
                    continue;
                }

                boolean urgent = Boolean.parseBoolean(urgentText);

                validCount++;
                totalRevenue += price;

                if (urgent) {
                    urgentVisits++;
                }
            }

            double averagePrice =
                    validCount == 0 ? 0.0 : totalRevenue / validCount;

            String report = buildReport(
                    validCount,
                    averagePrice,
                    totalRevenue,
                    urgentVisits,
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

        report.append("Звіт: Ветклініка\n");

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
            report.append("\nПомилки:\n");

            for (String error : errors) {
                report.append(error).append('\n');
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
                        + "[--input <файл>] "
                        + "[--output <файл>]");
    }
}