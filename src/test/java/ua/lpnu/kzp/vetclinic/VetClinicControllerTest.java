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
                        "Мурка;кіт;Іван Петренко;"
                                + "2026-10-01;огляд;350.00;false",
                        "Бім;собака;Олена Коваль;"
                                + "2026-10-02;вакцинація;500.00;false",
                        "Рекс;собака;Андрій Мельник;"
                                + "2026-10-05;травма лапи;1200.00;true",
                        "Том;кіт;Марія Бондар;"
                                + "2026-10-04;огляд;-10;false",
                        "Луна;кіт;Ірина Савчук;"
                                + "2026-10-06;аналізи;abc;false"),
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        VetClinicController controller =
                new VetClinicController();

        String report =
                controller.run(arguments);

        assertTrue(
                report.contains(
                        "Коректних записів: 3"));

        assertTrue(
                report.contains(
                        "Середня вартість: 683.33"));

        assertTrue(
                report.contains(
                        "Загальний виторг: 2050.00"));

        assertTrue(
                report.contains(
                        "Ургентних візитів: 1"));

        assertTrue(
                report.contains(
                        "Помилок: 2"));

        assertTrue(
                report.contains(
                        "Рядок 4: price має бути "
                                + "скінченним невід'ємним числом"));

        assertTrue(
                report.contains(
                        "Рядок 5: поле price має бути числом"));

        assertEquals(
                report,
                Files.readString(
                        output,
                        StandardCharsets.UTF_8));
    }

    @Test
    void usesPolymorphicFinalCostInRealProcessing()
            throws IOException {

        Path input =
                tempDir.resolve("polymorphic.csv");

        Path output =
                tempDir.resolve("polymorphic-report.txt");

        Files.writeString(
                input,
                String.join(
                        System.lineSeparator(),
                        "Мурка;кіт;Іван Петренко;"
                                + "2026-10-01;огляд;500.00;false",
                        "Рекс;собака;Олена Коваль;"
                                + "2026-10-02;"
                                + "невідкладна допомога;800.00;true"),
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        VetClinicController controller =
                new VetClinicController();

        String report =
                controller.run(arguments);

        /*
         * RoutineVisit:
         * 500.00
         *
         * UrgentVisit:
         * base price = 800.00
         * finalCost() = 1000.00
         *
         * total = 1500.00
         * average = 750.00
         */
        assertTrue(
                report.contains(
                        "Коректних записів: 2"));

        assertTrue(
                report.contains(
                        "Середня вартість: 750.00"));

        assertTrue(
                report.contains(
                        "Загальний виторг: 1500.00"));

        assertTrue(
                report.contains(
                        "Ургентних візитів: 1"));

        assertTrue(
                report.contains(
                        "Помилок: 0"));
    }

    @Test
    void handlesInputWithoutValidRecords()
            throws IOException {

        Path input =
                tempDir.resolve("invalid.csv");

        Path output =
                tempDir.resolve("invalid-report.txt");

        Files.writeString(
                input,
                String.join(
                        System.lineSeparator(),
                        "Мурка;кіт;Іван Петренко;"
                                + "2026-10-01;огляд;-1.00;false",
                        "Бім;собака;Олена Коваль;"
                                + "2026-10-02;вакцинація;abc;false"),
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        String report =
                new VetClinicController()
                        .run(arguments);

        assertTrue(
                report.contains(
                        "Коректних записів: 0"));

        assertTrue(
                report.contains(
                        "Середня вартість: 0.00"));

        assertTrue(
                report.contains(
                        "Загальний виторг: 0.00"));

        assertTrue(
                report.contains(
                        "Ургентних візитів: 0"));

        assertTrue(
                report.contains(
                        "Помилок: 2"));
    }

    @Test
    void reportsInvalidDateWithLineNumber()
            throws IOException {

        Path input =
                tempDir.resolve("invalid-date.csv");

        Path output =
                tempDir.resolve("invalid-date-report.txt");

        Files.writeString(
                input,
                "Боня;кіт;Іван Петренко;"
                        + "15-10-2026;"
                        + "огляд;500.00;false",
                StandardCharsets.UTF_8);

        AppArguments arguments =
                new AppArguments(
                        input,
                        output,
                        false,
                        false);

        String report =
                new VetClinicController()
                        .run(arguments);

        assertTrue(
                report.contains(
                        "Коректних записів: 0"));

        assertTrue(
                report.contains(
                        "Помилок: 1"));

        assertTrue(
                report.contains(
                        "Рядок 1: поле date має бути датою "
                                + "у форматі yyyy-MM-dd"));
    }
}