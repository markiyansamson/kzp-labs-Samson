package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class DataStorageExceptionTest {

    @Test
    void storesMessage() {
        DataStorageException exception =
                new DataStorageException("Помилка зберігання");

        assertEquals("Помилка зберігання", exception.getMessage());
    }

    @Test
    void storesMessageAndCause() {
        IOException cause = new IOException("Disk error");

        DataStorageException exception =
                new DataStorageException("Не вдалося записати CSV", cause);

        assertEquals("Не вдалося записати CSV", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}