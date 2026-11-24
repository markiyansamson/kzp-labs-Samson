from datetime import date

import pytest

from kzp_lab06.analysis import (
    average_cost,
    count_by_species,
    filter_by_cost,
    make_summary,
    most_expensive_visit,
    unique_owners,
)


def sample_records() -> list[dict[str, object]]:
    """Повертає тестовий набір візитів ветклініки."""
    return [
        {
            "animal": "Мурка",
            "species": "кіт",
            "owner": "Іван Петренко",
            "date": date(2026, 10, 1),
            "diagnosis": "огляд",
            "cost": 350.0,
        },
        {
            "animal": "Бім",
            "species": "собака",
            "owner": "Олена Коваль",
            "date": date(2026, 10, 2),
            "diagnosis": "вакцинація",
            "cost": 500.0,
        },
        {
            "animal": "Рекс",
            "species": "собака",
            "owner": "Андрій Мельник",
            "date": date(2026, 10, 5),
            "diagnosis": "травма лапи",
            "cost": 1200.0,
        },
        {
            "animal": "Луна",
            "species": "кіт",
            "owner": "Ірина Савчук",
            "date": date(2026, 10, 4),
            "diagnosis": "аналізи, повторний контроль",
            "cost": 700.0,
        },
        {
            "animal": "Барсик",
            "species": "кіт",
            "owner": "Іван Петренко",
            "date": date(2026, 10, 6),
            "diagnosis": "контроль",
            "cost": 450.0,
        },
        {
            "animal": "Граф",
            "species": "кінь",
            "owner": "Марія Бойко",
            "date": date(2026, 10, 7),
            "diagnosis": "огляд",
            "cost": 900.0,
        },
    ]


def test_filter_by_cost_uses_strict_threshold() -> None:
    """Перевіряє умову cost > threshold та межу cost == 500."""
    selected = filter_by_cost(sample_records(), 500.0)

    assert [record["animal"] for record in selected] == [
        "Рекс",
        "Луна",
        "Граф",
    ]


def test_filter_can_return_empty_result() -> None:
    """Перевіряє порожній результат відбору."""
    assert filter_by_cost(sample_records(), 5000.0) == []


def test_count_by_species_is_deterministic() -> None:
    """Перевіряє кількість візитів за species."""
    assert count_by_species(sample_records()) == {
        "кінь": 1,
        "кіт": 3,
        "собака": 2,
    }


def test_unique_owners_removes_duplicates() -> None:
    """Перевіряє множину унікальних власників."""
    assert unique_owners(sample_records()) == {
        "Іван Петренко",
        "Олена Коваль",
        "Андрій Мельник",
        "Ірина Савчук",
        "Марія Бойко",
    }


def test_average_cost() -> None:
    """Перевіряє середню вартість візитів."""
    assert average_cost(sample_records()) == pytest.approx(683.3333333333334)


def test_average_cost_for_empty_input_is_zero() -> None:
    """Перевіряє середню вартість для порожнього списку."""
    assert average_cost([]) == 0.0


def test_most_expensive_visit() -> None:
    """Перевіряє найдорожчий візит."""
    result = most_expensive_visit(sample_records())

    assert result is not None
    assert result["animal"] == "Рекс"
    assert result["cost"] == 1200.0


def test_most_expensive_visit_for_empty_input_is_none() -> None:
    """Перевіряє порожній список для пошуку максимуму."""
    assert most_expensive_visit([]) is None


def test_make_summary_supports_default_args_and_kwargs() -> None:
    """Перевіряє default parameter, *args і **kwargs."""
    result = make_summary(
        sample_records(),
        "average_cost",
        "most_expensive",
        average_cost="Середня вартість",
        most_expensive="Найдорожчий візит",
    )

    assert result["threshold"] == 500.0
    assert result["selected_count"] == 3
    assert result["Середня вартість"] == pytest.approx(683.3333333333334)

    most_expensive = result["Найдорожчий візит"]
    assert isinstance(most_expensive, dict)
    assert most_expensive["animal"] == "Рекс"


def test_make_summary_accepts_custom_threshold() -> None:
    """Перевіряє власне значення threshold."""
    result = make_summary(sample_records(), threshold=1000.0)

    assert result["selected_count"] == 1


def test_make_summary_rejects_unknown_metric() -> None:
    """Перевіряє помилку для невідомої метрики."""
    with pytest.raises(ValueError, match="Невідома метрика"):
        make_summary(sample_records(), "unknown")
