package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvExporterTest {

    @TempDir
    Path temporaryDirectory;

    private static final class SampleRecord {

        @CsvColumn("Назва")
        private final String title;

        @CsvColumn("Код")
        private final String code;

        @CsvColumn("Опис")
        private final String description;

        SampleRecord(String code, String title, String description) {
            this.code = code;
            this.title = title;
            this.description = description;
        }
    }

    private static final class NoCsvFields {

        private final String value = "test";
    }

    @Test
    void writesHeaderAndValuesInDeterministicFieldOrder()
            throws Exception {

        Path path = temporaryDirectory.resolve("records.csv");

        CsvExporter.write(
                path,
                SampleRecord.class,
                List.of(new SampleRecord(
                        "A-01",
                        "Запис",
                        "Опис")));

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        String expected = String.join(
                System.lineSeparator(),
                "Код,Опис,Назва",
                "A-01,Опис,Запис",
                "");

        assertEquals(expected, csv);
    }

    @Test
    void escapesCommaAndQuotes() throws Exception {
        Path path = temporaryDirectory.resolve("quoted.csv");

        CsvExporter.write(
                path,
                SampleRecord.class,
                List.of(new SampleRecord(
                        "A-02",
                        "Карти, \"що говорять\"",
                        "Тест")));

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        assertTrue(csv.contains(
                "\"Карти, \"\"що говорять\"\"\""));
    }

    @Test
    void escapesMultilineValue() throws Exception {
        Path path = temporaryDirectory.resolve("multiline.csv");

        CsvExporter.write(
                path,
                SampleRecord.class,
                List.of(new SampleRecord(
                        "A-03",
                        "Запис",
                        "Перший рядок\nДругий рядок")));

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        assertTrue(csv.contains(
                "\"Перший рядок\nДругий рядок\""));
    }

    @Test
    void emptyListStillProducesHeader() throws Exception {
        Path path = temporaryDirectory.resolve("empty.csv");

        CsvExporter.write(
                path,
                SampleRecord.class,
                List.of());

        String csv = Files.readString(
                path,
                StandardCharsets.UTF_8);

        assertEquals(
                "Код,Опис,Назва" + System.lineSeparator(),
                csv);
    }

    @Test
    void rejectsClassWithoutAnnotatedFields() {
        Path path = temporaryDirectory.resolve("invalid.csv");

        DataStorageException exception =
                assertThrows(
                        DataStorageException.class,
                        () -> CsvExporter.write(
                                path,
                                NoCsvFields.class,
                                List.of()));

        assertEquals(
                "Клас не має CSV-полів",
                exception.getMessage());
    }
}