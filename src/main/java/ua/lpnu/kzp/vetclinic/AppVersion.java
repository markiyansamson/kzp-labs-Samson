package ua.lpnu.kzp.vetclinic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Надає версію застосунку, сформовану Maven
 * із версії проєкту та номера білда.
 */
public final class AppVersion {

    private static final String RESOURCE =
            "/version.properties";

    private static final String VERSION_PROPERTY =
            "app.version";

    private static final String BUILD_PROPERTY =
            "app.build";

    private AppVersion() {
    }

    /**
     * Повертає повну версію застосунку.
     *
     * <p>Формат:
     * {@code <version>+build.<build-number>}.
     *
     * <p>Наприклад:
     * {@code 4.0.0+build.local} або
     * {@code 4.0.0+build.43}.
     *
     * @return повна версія застосунку
     */
    public static String get() {
        Properties properties = loadProperties();

        String version =
                requireProperty(
                        properties,
                        VERSION_PROPERTY);

        String build =
                requireProperty(
                        properties,
                        BUILD_PROPERTY);

        return version
                + "+build."
                + build;
    }

    /**
     * Завантажує version.properties.
     *
     * @return властивості версії
     */
    private static Properties loadProperties() {
        Properties properties =
                new Properties();

        try (InputStream input =
                     AppVersion.class
                             .getResourceAsStream(
                                     RESOURCE)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Не знайдено ресурс "
                                + "version.properties");
            }

            properties.load(input);

            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Не вдалося прочитати "
                            + "версію застосунку",
                    exception);
        }
    }

    /**
     * Повертає обов'язкову властивість.
     *
     * @param properties властивості
     * @param name назва властивості
     * @return значення властивості
     */
    private static String requireProperty(
            Properties properties,
            String name) {

        String value =
                properties.getProperty(name);

        if (value == null
                || value.isBlank()) {
            throw new IllegalStateException(
                    "Не визначено властивість "
                            + name);
        }

        return value.trim();
    }
}