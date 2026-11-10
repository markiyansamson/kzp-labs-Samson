package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class RepositoryTest {

    @Test
    void addsAndReturnsRecords() {
        Repository<String> repository = new Repository<>();

        repository.add("first");
        repository.add("second");

        assertEquals(List.of("first", "second"), repository.all());
    }

    @Test
    void findsRecordsByPredicate() {
        Repository<String> repository = new Repository<>();

        repository.add("cat");
        repository.add("dog");
        repository.add("horse");

        assertEquals(
                List.of("cat", "dog"),
                repository.find(value -> value.length() == 3));
    }

    @Test
    void allReturnsImmutableSnapshot() {
        Repository<String> repository = new Repository<>();
        repository.add("first");

        List<String> snapshot = repository.all();

        assertThrows(
                UnsupportedOperationException.class,
                () -> snapshot.add("second"));
    }

    @Test
    void snapshotDoesNotChangeAfterRepositoryModification() {
        Repository<String> repository = new Repository<>();
        repository.add("first");

        List<String> snapshot = repository.all();

        repository.add("second");

        assertEquals(List.of("first"), snapshot);
        assertEquals(List.of("first", "second"), repository.all());
    }

    @Test
    void rejectsNullItem() {
        Repository<String> repository = new Repository<>();

        assertThrows(
                NullPointerException.class,
                () -> repository.add(null));
    }

    @Test
    void rejectsNullPredicate() {
        Repository<String> repository = new Repository<>();
        repository.add("first");

        assertThrows(
                NullPointerException.class,
                () -> repository.find(null));
    }
}