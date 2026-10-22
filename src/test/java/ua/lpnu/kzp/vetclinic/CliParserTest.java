package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class CliParserTest {

    @Test
    void usesDefaultPathsWithoutArguments() {
        AppArguments arguments =
                CliParser.parse(new String[0]);

        assertEquals(
                Path.of("data", "input.csv"),
                arguments.inputPath());

        assertEquals(
                Path.of("out", "report.txt"),
                arguments.outputPath());

        assertFalse(arguments.helpRequested());
        assertFalse(arguments.versionRequested());
    }

    @Test
    void parsesCustomInputAndOutputPaths() {
        AppArguments arguments = CliParser.parse(
                new String[] {
                    "--input",
                    "custom/input.csv",
                    "--output",
                    "custom/report.txt"
                });

        assertEquals(
                Path.of("custom/input.csv"),
                arguments.inputPath());

        assertEquals(
                Path.of("custom/report.txt"),
                arguments.outputPath());
    }

    @Test
    void parsesHelpArgument() {
        AppArguments arguments =
                CliParser.parse(
                        new String[] {"--help"});

        assertTrue(arguments.helpRequested());
        assertFalse(arguments.versionRequested());
    }

    @Test
    void parsesVersionArgument() {
        AppArguments arguments =
                CliParser.parse(
                        new String[] {"--version"});

        assertTrue(arguments.versionRequested());
        assertFalse(arguments.helpRequested());
    }

    @Test
    void rejectsMissingInputPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CliParser.parse(
                        new String[] {"--input"}));
    }

    @Test
    void rejectsMissingOutputPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CliParser.parse(
                        new String[] {"--output"}));
    }

    @Test
    void rejectsUnknownArgument() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CliParser.parse(
                        new String[] {"--unknown"}));
    }
}