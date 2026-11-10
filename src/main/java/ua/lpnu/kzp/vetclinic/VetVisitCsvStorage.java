package ua.lpnu.kzp.vetclinic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Виконує предметне читання VetVisitRecord із CSV.
 */
public final class VetVisitCsvStorage {

    private static final List<String> EXPECTED_HEADER = List.of(
            "тварина",
            "вартість",
            "дата",
            "діагноз",
            "власник",
            "вид");

    private VetVisitCsvStorage() {
    }

    /**
     * Читає CSV-файл та відновлює ветеринарні записи.
     *
     * @param path шлях до CSV
     * @return відновлені записи
     * @throws DataStorageException якщо читання або формат некоректні
     */
    public static List<VetVisitRecord> read(Path path)
            throws DataStorageException {

        String csv;

        try {
            csv = Files.readString(
                    path,
                    StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new DataStorageException(
                    "Не вдалося прочитати CSV: %s"
                            .formatted(path),
                    exception);
        }

        List<List<String>> rows =
                CsvParser.parseDocument(csv);

        if (rows.isEmpty()) {
            throw new DataStorageException(
                    "CSV не містить заголовка");
        }

        if (!EXPECTED_HEADER.equals(rows.get(0))) {
            throw new DataStorageException(
                    "Некоректний заголовок CSV");
        }

        List<VetVisitRecord> restored =
                new ArrayList<>();

        for (int index = 1; index < rows.size(); index++) {
            restored.add(
                    VetVisitRecord.fromFields(
                            rows.get(index)));
        }

        return List.copyOf(restored);
    }
}