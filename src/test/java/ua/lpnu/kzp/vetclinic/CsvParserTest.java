package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class CsvParserTest {

    @Test
    void parsesSimpleCsvRow() throws Exception {
        assertEquals(
                List.of("Мурка", "350.0", "2026-10-01"),
                CsvParser.parse(
                        "Мурка,350.0,2026-10-01"));
    }

    @Test
    void parsesCommaInsideQuotedField() throws Exception {
        assertEquals(
                List.of("Мурка", "огляд, вакцинація"),
                CsvParser.parse(
                        "Мурка,\"огляд, вакцинація\""));
    }

    @Test
    void parsesEscapedQuote() throws Exception {
        assertEquals(
                List.of("Мурка", "діагноз \"A\""),
                CsvParser.parse(
                        "Мурка,\"діагноз \"\"A\"\"\""));
    }

    @Test
    void parsesMultilineQuotedField() throws Exception {
        String csv =
                "Мурка,\"перший рядок\nдругий рядок\"";

        assertEquals(
                List.of(
                        "Мурка",
                        "перший рядок\nдругий рядок"),
                CsvParser.parse(csv));
    }

    @Test
    void rejectsUnclosedQuotes() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parse(
                        "Мурка,\"незакритий текст"));
    }
}