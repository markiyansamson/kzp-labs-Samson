package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class VetVisitRecordTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void roundTripPreservesRecords() throws Exception {
        List<VetVisitRecord> original = List.of(
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
                        500.0));

        Path path = temporaryDirectory.resolve("visits.csv");

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

        assertEquals(original, restored);
    }

    @Test
    void exportedHeaderUsesVariant21Columns()
            throws Exception {

        Path path = temporaryDirectory.resolve("header.csv");

        CsvExporter.write(
                path,
                VetVisitRecord.class,
                List.of());

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        assertEquals(
                "тварина,вартість,дата,діагноз,власник,вид"
                        + System.lineSeparator(),
                csv);
    }

    @Test
    void roundTripPreservesCommaQuoteAndMultiline()
            throws Exception {

        VetVisitRecord original = new VetVisitRecord(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "огляд, \"контроль\"\nповторно",
                350.0);

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

        assertEquals(original, restored);
        assertTrue(csv.contains(
                "\"огляд, \"\"контроль\"\"\nповторно\""));
    }

    @Test
    void roundTripKeyIsOwnerAndDate() {
        VetVisitRecord record = new VetVisitRecord(
                "Мурка",
                "кіт",
                "Іван Петренко",
                LocalDate.of(2026, 10, 1),
                "огляд",
                350.0);

        assertEquals("Іван Петренко", record.getOwner());
        assertEquals(
                LocalDate.of(2026, 10, 1),
                record.getDate());
    }

    @Test
    void rejectsWrongFieldCount() {
        assertThrows(
                DataStorageException.class,
                () -> VetVisitRecord.fromCsv(
                        "Мурка,350.0,2026-10-01"));
    }

    @Test
    void rejectsInvalidCostAndPreservesCause() {
        DataStorageException exception =
                assertThrows(
                        DataStorageException.class,
                        () -> VetVisitRecord.fromFields(
                                List.of(
                                        "Мурка",
                                        "abc",
                                        "2026-10-01",
                                        "огляд",
                                        "Іван Петренко",
                                        "кіт")));

        assertInstanceOf(
                NumberFormatException.class,
                exception.getCause());
    }

    @Test
    void rejectsInvalidDateAndPreservesCause() {
        DataStorageException exception =
                assertThrows(
                        DataStorageException.class,
                        () -> VetVisitRecord.fromFields(
                                List.of(
                                        "Мурка",
                                        "350.0",
                                        "not-a-date",
                                        "огляд",
                                        "Іван Петренко",
                                        "кіт")));

        assertInstanceOf(
                java.time.format.DateTimeParseException.class,
                exception.getCause());
    }
}