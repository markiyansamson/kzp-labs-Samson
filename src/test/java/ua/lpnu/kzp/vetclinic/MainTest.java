package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тести для програми ветеринарної клініки.
 */
class MainTest {

    @TempDir
    Path tempDir;

    @Test
    void processesValidAndInvalidRecords() throws IOException {
        Path input = tempDir.resolve("input.csv");
        Path output = tempDir.resolve("report.txt");

        String data = """
                Мурка;кіт;огляд;350.00;false
                Бім;собака;вакцинація;500.00;false
                Рекс;собака;невідкладна допомога;1200.00;true
                Луна;кіт;аналізи;-100.00;false
                Боня;папуга;огляд;abc;true
                """;

        Files.writeString(input, data, StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report = Files.readString(output, StandardCharsets.UTF_8);

        assertTrue(report.contains("Коректних записів: 3"));
        assertTrue(report.contains("Середня вартість: 683.33"));
        assertTrue(report.contains("Загальний виторг: 2050.00"));
        assertTrue(report.contains("Ургентних візитів: 1"));
        assertTrue(report.contains("Помилок: 2"));
    }

    @Test
    void handlesInputWithoutValidRecords() throws IOException {
        Path input = tempDir.resolve("invalid.csv");
        Path output = tempDir.resolve("report.txt");

        String data = """
                Луна;кіт;аналізи;-100.00;false
                Боня;папуга;огляд;abc;true
                """;

        Files.writeString(input, data, StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report = Files.readString(output, StandardCharsets.UTF_8);

        assertTrue(report.contains("Коректних записів: 0"));
        assertTrue(report.contains("Середня вартість: 0.00"));
        assertTrue(report.contains("Загальний виторг: 0.00"));
        assertTrue(report.contains("Ургентних візитів: 0"));
        assertTrue(report.contains("Помилок: 2"));
    }

    @Test
    void rejectsInvalidBooleanValue() throws IOException {
        Path input = tempDir.resolve("invalid-boolean.csv");
        Path output = tempDir.resolve("report.txt");

        String data = "Мурка;кіт;огляд;300.00;yes";

        Files.writeString(input, data, StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report = Files.readString(output, StandardCharsets.UTF_8);

        assertTrue(report.contains("Коректних записів: 0"));
        assertTrue(report.contains(
                "поле urgent має бути true або false"));
        assertTrue(report.contains("Помилок: 1"));
    }
}