package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
                Мурка;кіт;Іван Петренко;2026-10-01;огляд;350.00;false
                Бім;собака;Олена Коваль;2026-10-02;вакцинація;500.00;false
                Рекс;собака;Андрій Мельник;2026-10-05;травма лапи;1200.00;true
                Луна;кіт;Ірина Савчук;2026-10-04;аналізи;-100.00;false
                Боня;папуга;Марія Бондар;2026-10-06;огляд;abc;true
                """;

        Files.writeString(
                input,
                data,
                StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report =
                Files.readString(
                        output,
                        StandardCharsets.UTF_8);

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
    }

    @Test
    void handlesInputWithoutValidRecords() throws IOException {
        Path input = tempDir.resolve("invalid.csv");
        Path output = tempDir.resolve("report.txt");

        String data = """
                Луна;кіт;Ірина Савчук;2026-10-04;аналізи;-100.00;false
                Боня;папуга;Марія Бондар;2026-10-06;огляд;abc;true
                """;

        Files.writeString(
                input,
                data,
                StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report =
                Files.readString(
                        output,
                        StandardCharsets.UTF_8);

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
    void rejectsInvalidBooleanValue() throws IOException {
        Path input =
                tempDir.resolve(
                        "invalid-boolean.csv");

        Path output =
                tempDir.resolve(
                        "report.txt");

        String data =
                "Мурка;кіт;Іван Петренко;"
                        + "2026-10-01;"
                        + "огляд;300.00;yes";

        Files.writeString(
                input,
                data,
                StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report =
                Files.readString(
                        output,
                        StandardCharsets.UTF_8);

        assertTrue(
                report.contains(
                        "Коректних записів: 0"));

        assertTrue(
                report.contains(
                        "поле urgent має бути true або false"));

        assertTrue(
                report.contains(
                        "Помилок: 1"));
    }

    @Test
    void rejectsInvalidDateValue() throws IOException {
        Path input =
                tempDir.resolve(
                        "invalid-date.csv");

        Path output =
                tempDir.resolve(
                        "report.txt");

        String data =
                "Мурка;кіт;Іван Петренко;"
                        + "01-10-2026;"
                        + "огляд;300.00;false";

        Files.writeString(
                input,
                data,
                StandardCharsets.UTF_8);

        Main.main(new String[] {
                "--input", input.toString(),
                "--output", output.toString()
        });

        String report =
                Files.readString(
                        output,
                        StandardCharsets.UTF_8);

        assertTrue(
                report.contains(
                        "Коректних записів: 0"));

        assertTrue(
                report.contains(
                        "поле date має бути датою "
                                + "у форматі yyyy-MM-dd"));

        assertTrue(
                report.contains(
                        "Помилок: 1"));
    }

    /**
     * Перевіряє предметний шлях:
     * CSV -> VetVisit.fromCsv() -> List<VetVisit>
     * -> VetClinicController.calculateSummary()
     * -> PriceUrgency.
     */
    @Test
    void processesVisitsThroughDomainModel() {
        List<VetVisit> visits = List.of(
                VetVisit.fromCsv(
                        "Мурка;кіт;Іван Петренко;"
                                + "2026-10-01;"
                                + "огляд;350.00;false"),

                VetVisit.fromCsv(
                        "Бім;собака;Олена Коваль;"
                                + "2026-10-02;"
                                + "вакцинація;500.00;false"),

                VetVisit.fromCsv(
                        "Рекс;собака;Андрій Мельник;"
                                + "2026-10-05;"
                                + "травма лапи;1200.00;true"));

        PriceUrgency summary =
                VetClinicController.calculateSummary(
                        visits);

        assertEquals(
                2050.00,
                summary.totalPrice(),
                0.001);

        assertEquals(
                1,
                summary.urgentCount());

        assertEquals(
                "Іван Петренко",
                visits.get(0).getOwner());

        assertEquals(
                "огляд",
                visits.get(0).getDiagnosis());
    }
}