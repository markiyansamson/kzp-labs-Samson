package ua.lpnu.kzp.vetclinic;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Містить перевірені параметри запуску застосунку.
 *
 * @param inputPath шлях до вхідного CSV-файла
 * @param outputPath шлях до вихідного звіту
 * @param helpRequested чи запитано довідку
 * @param versionRequested чи запитано версію
 */
public record AppArguments(
        Path inputPath,
        Path outputPath,
        boolean helpRequested,
        boolean versionRequested) {

    /**
     * Перевіряє обов'язкові шляхи.
     */
    public AppArguments {
        Objects.requireNonNull(inputPath, "Вхідний шлях не може бути null");
        Objects.requireNonNull(outputPath, "Вихідний шлях не може бути null");
    }
}