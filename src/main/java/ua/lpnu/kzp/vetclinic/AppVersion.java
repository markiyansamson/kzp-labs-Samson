package ua.lpnu.kzp.vetclinic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Надає версію застосунку, сформовану Maven із версії проєкту в pom.xml.
 */
public final class AppVersion {

    private static final String RESOURCE = "/version.properties";
    private static final String VERSION_PROPERTY = "app.version";

    private AppVersion() {
    }

    /**
     * Повертає версію застосунку.
     *
     * @return версія проєкту з pom.xml
     */
    public static String get() {
        Properties properties = new Properties();

        try (InputStream input =
                     AppVersion.class.getResourceAsStream(RESOURCE)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Не знайдено ресурс version.properties");
            }

            properties.load(input);

            String version = properties.getProperty(VERSION_PROPERTY);

            if (version == null || version.isBlank()) {
                throw new IllegalStateException(
                        "Не визначено властивість app.version");
            }

            return version;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Не вдалося прочитати версію застосунку",
                    exception);
        }
    }
}