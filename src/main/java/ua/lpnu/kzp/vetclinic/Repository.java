package ua.lpnu.kzp.vetclinic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Типобезпечне параметризоване сховище об'єктів у пам'яті.
 *
 * @param <T> тип записів у сховищі
 */
public final class Repository<T> {

    private final List<T> items = new ArrayList<>();

    /**
     * Створює порожнє параметризоване сховище.
     */
    public Repository() {
    }

    /**
     * Додає непорожній запис до сховища.
     *
     * @param item запис
     * @throws NullPointerException якщо запис {@code null}
     */
    public void add(T item) {
        items.add(Objects.requireNonNull(
                item,
                "Запис не може бути null"));
    }

    /**
     * Повертає незмінний знімок усіх записів.
     *
     * @return незмінний список записів
     */
    public List<T> all() {
        return List.copyOf(items);
    }

    /**
     * Повертає записи, які задовольняють умову.
     *
     * @param condition умова пошуку
     * @return список знайдених записів
     * @throws NullPointerException якщо умова {@code null}
     */
    public List<T> find(Predicate<? super T> condition) {
        Objects.requireNonNull(
                condition,
                "Умова не може бути null");

        return items.stream()
                .filter(condition)
                .toList();
    }
}