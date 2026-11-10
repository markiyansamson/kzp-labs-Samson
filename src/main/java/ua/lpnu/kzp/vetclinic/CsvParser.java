package ua.lpnu.kzp.vetclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Розбирає CSV-рядки та CSV-документи з підтримкою quoted multiline-полів.
 */
public final class CsvParser {

    private CsvParser() {
    }

    /**
     * Розбирає один CSV-запис.
     *
     * @param line CSV-рядок
     * @return список значень полів
     * @throws DataStorageException якщо рядок має некоректний CSV-формат
     */
    public static List<String> parse(String line)
            throws DataStorageException {

        List<List<String>> records = parseDocument(line);

        if (records.size() != 1) {
            throw new DataStorageException(
                    "Очікується один CSV-рядок");
        }

        return records.get(0);
    }

    /**
     * Розбирає повний CSV-документ.
     *
     * @param text текст CSV
     * @return список записів, де кожен запис є списком полів
     * @throws DataStorageException якщо CSV має некоректний формат
     */
    public static List<List<String>> parseDocument(String text)
            throws DataStorageException {

        if (text == null) {
            throw new DataStorageException(
                    "CSV-документ не може бути null");
        }

        if (text.isEmpty()) {
            return List.of();
        }

        List<List<String>> records = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        boolean quoted = false;
        boolean quoteClosed = false;

        for (int index = 0; index < text.length(); index++) {
            char symbol = text.charAt(index);

            if (symbol == '"'
                    && !quoted
                    && !quoteClosed
                    && current.length() == 0) {

                quoted = true;

            } else if (symbol == '"'
                    && quoted
                    && index + 1 < text.length()
                    && text.charAt(index + 1) == '"') {

                current.append('"');
                index++;

            } else if (symbol == '"' && quoted) {

                quoted = false;
                quoteClosed = true;

            } else if (symbol == ',' && !quoted) {

                fields.add(current.toString());
                current.setLength(0);
                quoteClosed = false;

            } else if ((symbol == '\r' || symbol == '\n')
                    && !quoted) {

                fields.add(current.toString());
                records.add(List.copyOf(fields));

                fields = new ArrayList<>();
                current.setLength(0);
                quoteClosed = false;

                if (symbol == '\r'
                        && index + 1 < text.length()
                        && text.charAt(index + 1) == '\n') {

                    index++;
                }

            } else {

                if (quoteClosed) {
                    throw new DataStorageException(
                            "Символ після закритих CSV-лапок");
                }

                current.append(symbol);
            }
        }

        if (quoted) {
            throw new DataStorageException(
                    "Незакриті CSV-лапки");
        }

        if (!fields.isEmpty() || current.length() > 0) {
            fields.add(current.toString());
            records.add(List.copyOf(fields));
        }

        return List.copyOf(records);
    }
}