package ua.lpnu.kzp.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CsvColumnTest {

    private static final class SampleRecord {

        @CsvColumn("Назва")
        private final String name = "test";
    }

    @Test
    void hasRuntimeRetention() {
        Retention retention = CsvColumn.class.getAnnotation(Retention.class);

        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }

    @Test
    void targetsFields() {
        Target target = CsvColumn.class.getAnnotation(Target.class);

        assertTrue(Arrays.asList(target.value()).contains(ElementType.FIELD));
    }

    @Test
    void annotationIsReadableThroughReflection() throws Exception {
        Field field = SampleRecord.class.getDeclaredField("name");

        CsvColumn annotation = field.getAnnotation(CsvColumn.class);

        assertEquals("Назва", annotation.value());
    }
}