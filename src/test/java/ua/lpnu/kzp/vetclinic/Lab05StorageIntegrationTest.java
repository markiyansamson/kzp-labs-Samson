package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class Lab05StorageIntegrationTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void roundTripPreservesFiveVariant21Records() throws Exception {
        List<VetVisitRecord> original = sampleRecords();

        Path path = temporaryDirectory.resolve("vet-visits.csv");

        CsvExporter.write(
                path,
                VetVisitRecord.class,
                original);

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        List<List<String>> rows =
                CsvParser.parseDocument(csv);

        List<VetVisitRecord> restored = rows.stream()
                .skip(1)
                .map(fields -> {
                    try {
                        return VetVisitRecord.fromFields(fields);
                    } catch (DataStorageException exception) {
                        throw new IllegalStateException(exception);
                    }
                })
                .toList();

        assertEquals(6, rows.size());
        assertEquals(5, restored.size());
        assertEquals(original, restored);
    }

    @Test
    void specialCsvCharactersAreEscapedAndRestored()
            throws Exception {

        VetVisitRecord original = new VetVisitRecord(
                "Луна",
                "кіт",
                "Ірина Савчук",
                LocalDate.of(2026, 10, 4),
                "аналізи, \"повторно\"\nконтроль",
                700.0);

        Path path = temporaryDirectory.resolve("special.csv");

        CsvExporter.write(
                path,
                VetVisitRecord.class,
                List.of(original));

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        List<List<String>> rows =
                CsvParser.parseDocument(csv);

        VetVisitRecord restored =
                VetVisitRecord.fromFields(rows.get(1));

        assertTrue(csv.contains(
                "\"аналізи, \"\"повторно\"\"\nконтроль\""));

        assertEquals(original, restored);
    }

    @Test
    void multilineFieldCreatesMorePhysicalThanLogicalRows()
            throws Exception {

        List<VetVisitRecord> original = sampleRecords();

        Path path = temporaryDirectory.resolve("multiline.csv");

        CsvExporter.write(
                path,
                VetVisitRecord.class,
                original);

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        List<String> physicalLines = Files.readAllLines(
                path,
                StandardCharsets.UTF_8);

        List<List<String>> logicalRecords =
                CsvParser.parseDocument(csv);

        assertTrue(
                physicalLines.size() > logicalRecords.size());

        assertEquals(6, logicalRecords.size());
    }

    @Test
    void emptyDatasetProducesHeaderWithoutDataRecords()
            throws Exception {

        Path path = temporaryDirectory.resolve("empty.csv");

        CsvExporter.write(
                path,
                VetVisitRecord.class,
                List.of());

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        List<List<String>> rows =
                CsvParser.parseDocument(csv);

        assertEquals(1, rows.size());

        assertEquals(
                List.of(
                        "тварина",
                        "вартість",
                        "дата",
                        "діагноз",
                        "власник",
                        "вид"),
                rows.get(0));
    }

    @Test
    void invalidCsvRowIsRejected() {
        DataStorageException exception =
                assertThrows(
                        DataStorageException.class,
                        () -> VetVisitRecord.fromCsv(
                                "Мурка,350.0,2026-10-01,огляд"));

        assertEquals(
                "Очікується шість CSV-полів",
                exception.getMessage());
    }

    @Test
    void fileSystemErrorIsWrappedWithCause() throws Exception {
        Path directory =
                temporaryDirectory.resolve("not-a-file");

        Files.createDirectory(directory);

        DataStorageException exception =
                assertThrows(
                        DataStorageException.class,
                        () -> CsvExporter.write(
                                directory,
                                VetVisitRecord.class,
                                List.of()));

        assertInstanceOf(
                IOException.class,
                exception.getCause());

        assertTrue(
                exception.getMessage()
                        .startsWith("Не вдалося записати CSV:"));
    }

    private static List<VetVisitRecord> sampleRecords() {
        return List.of(
                new VetVisitRecord(
                        "Мурка",
                        "кіт",
                        "Іван Петренко",
                        LocalDate.of(2026, 10, 1),
                        "профілактичний огляд",
                        350.0),

                new VetVisitRecord(
                        "Бім",
                        "собака",
                        "Олена Коваль",
                        LocalDate.of(2026, 10, 2),
                        "вакцинація",
                        500.0),

                new VetVisitRecord(
                        "Рекс",
                        "собака",
                        "Андрій Мельник",
                        LocalDate.of(2026, 10, 5),
                        "травма лапи",
                        1200.0),

                new VetVisitRecord(
                        "Луна",
                        "кіт",
                        "Ірина Савчук",
                        LocalDate.of(2026, 10, 4),
                        "аналізи, \"повторно\"\nконтроль",
                        700.0),

                new VetVisitRecord(
                        "Барсик",
                        "кіт",
                        "Іван Петренко",
                        LocalDate.of(2026, 10, 6),
                        "контрольний огляд",
                        450.0));
    }
}