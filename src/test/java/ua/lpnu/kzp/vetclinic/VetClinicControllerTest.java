package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class VetClinicControllerTest {

    @TempDir
    Path tempDir;

    @Test
    void preservesLab02RegressionReport() throws IOException {
        Path input = tempDir.resolve("input.csv");
        Path output = tempDir.resolve("report.txt");

        Files.writeString(
                input,
                String.join(
                        System.lineSeparator(),
                        "Мурка;кіт;огляд;350.00;false",
                        "Бім;собака;вакцинація;500.00;false",
                        "Рекс;собака;невідкладна допомога;1200.00;true",
                        "Том;кіт;огляд;-10;false",
                        "Луна;кіт;огляд;abc;false"),
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        VetClinicController controller =
                new VetClinicController();

        String report = controller.run(arguments);

        assertTrue(report.contains(
                "Коректних записів: 3"));

        assertTrue(report.contains(
                "Середня вартість: 683.33"));

        assertTrue(report.contains(
                "Загальний виторг: 2050.00"));

        assertTrue(report.contains(
                "Ургентних візитів: 1"));

        assertTrue(report.contains(
                "Помилок: 2"));

        assertEquals(
                report,
                Files.readString(
                        output,
                        StandardCharsets.UTF_8));
    }

    @Test
    void usesPolymorphicFinalCostInRealProcessing() throws IOException {
        Path input = tempDir.resolve("polymorphic.csv");
        Path output = tempDir.resolve("polymorphic-report.txt");

        Files.writeString(
                input,
                String.join(
                        System.lineSeparator(),
                        "Мурка;кіт;огляд;500.00;false",
                        "Рекс;собака;невідкладна допомога;800.00;true"),
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        VetClinicController controller =
                new VetClinicController();

        String report = controller.run(arguments);

        assertTrue(report.contains(
                "Коректних записів: 2"));

        assertTrue(report.contains(
                "Середня вартість: 750.00"));

        assertTrue(report.contains(
                "Загальний виторг: 1500.00"));

        assertTrue(report.contains(
                "Ургентних візитів: 1"));

        assertTrue(report.contains(
                "Помилок: 0"));
    }
}