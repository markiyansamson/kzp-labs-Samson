package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class AppVersionTest {

    @Test
    void readsVersionAndBuildFromFilteredMavenResource()
            throws IOException {

        Properties properties = new Properties();

        try (InputStream input =
                     AppVersionTest.class
                             .getResourceAsStream(
                                     "/version.properties")) {

            assertTrue(input != null);

            properties.load(input);
        }

        String version =
                properties.getProperty(
                        "app.version");

        String build =
                properties.getProperty(
                        "app.build");

        assertFalse(version.isBlank());
        assertFalse(build.isBlank());

        assertEquals(
                version + "+build." + build,
                AppVersion.get());
    }

    @Test
    void versionContainsBuildNumber() {
        String version =
                AppVersion.get();

        assertTrue(
                version.matches(
                        "\\d+\\.\\d+\\.\\d+"
                                + "\\+build\\."
                                + "[A-Za-z0-9._-]+"));
    }
}