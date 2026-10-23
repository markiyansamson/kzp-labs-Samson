package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class AppVersionTest {

    @Test
    void readsVersionFromFilteredMavenResource() throws IOException {
        Properties properties = new Properties();

        try (InputStream input =
                     AppVersionTest.class.getResourceAsStream(
                             "/version.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Не знайдено version.properties");
            }

            properties.load(input);
        }

        String expected =
                properties.getProperty("app.version");

        assertFalse(expected.isBlank());
        assertEquals(expected, AppVersion.get());
    }
}