package ua.lpnu.kzp.vetclinic;

import java.nio.file.Path;

/**
 * Виконує розбір аргументів командного рядка.
 */
public final class CliParser {

    private static final Path DEFAULT_INPUT =
            Path.of("data", "input.csv");

    private static final Path DEFAULT_OUTPUT =
            Path.of("out", "report.txt");

    private CliParser() {
    }

    /**
     * Перетворює аргументи командного рядка на параметри застосунку.
     *
     * @param args аргументи командного рядка
     * @return перевірені параметри запуску
     */
    public static AppArguments parse(String[] args) {
        Path inputPath = DEFAULT_INPUT;
        Path outputPath = DEFAULT_OUTPUT;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--help" -> {
                    return new AppArguments(
                            inputPath,
                            outputPath,
                            true,
                            false);
                }

                case "--version" -> {
                    return new AppArguments(
                            inputPath,
                            outputPath,
                            false,
                            true);
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

        return new AppArguments(
                inputPath,
                outputPath,
                false,
                false);
    }

    /**
     * Повертає текст довідки.
     *
     * @return довідка про параметри запуску
     */
    public static String usage() {
        return "Використання: "
                + "java Main "
                + "[--help] "
                + "[--version] "
                + "[--input <файл>] "
                + "[--output <файл>]";
    }
}