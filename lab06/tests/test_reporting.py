from pathlib import Path

from kzp_lab06.reporting import format_report, write_report


def sample_summary() -> dict[str, object]:
    """Повертає приклад підсумку для тестування звіту."""
    return {
        "total_count": 6,
        "selected_count": 3,
        "threshold": 500.0,
        "species_counts": {
            "кінь": 1,
            "кіт": 3,
            "собака": 2,
        },
        "owners": {
            "Іван Петренко",
            "Олена Коваль",
            "Андрій Мельник",
            "Ірина Савчук",
            "Марія Бойко",
        },
        "Середня вартість": 683.3333333333334,
        "Найдорожчий візит": {
            "animal": "Рекс",
            "cost": 1200.0,
        },
    }


def test_format_report_is_deterministic() -> None:
    """Перевіряє повний детермінований текст звіту."""
    report = format_report(sample_summary())

    assert report == (
        "Звіт: Ветклініка\n"
        "Усього записів: 6\n"
        "Поріг вартості: 500.00\n"
        "Записів дорожче порогу: 3\n"
        "\n"
        "Кількість візитів за видом:\n"
        "кінь: 1\n"
        "кіт: 3\n"
        "собака: 2\n"
        "\n"
        "Власники:\n"
        "Іван Петренко\n"
        "Ірина Савчук\n"
        "Андрій Мельник\n"
        "Марія Бойко\n"
        "Олена Коваль\n"
        "\n"
        "Середня вартість: 683.33\n"
        "Найдорожчий візит: Рекс — 1200.00\n"
    )


def test_format_report_for_empty_data() -> None:
    """Перевіряє звіт для порожнього набору."""
    summary = {
        "total_count": 0,
        "selected_count": 0,
        "threshold": 500.0,
        "species_counts": {},
        "owners": set(),
        "Середня вартість": 0.0,
        "Найдорожчий візит": None,
    }

    report = format_report(summary)

    assert "Усього записів: 0" in report
    assert "Середня вартість: 0.00" in report
    assert "Найдорожчий візит: немає даних" in report


def test_write_report_creates_utf8_file(tmp_path: Path) -> None:
    """Перевіряє створення та читання UTF-8 звіту."""
    output = tmp_path / "reports" / "summary.txt"
    text = "Звіт: Ветклініка\nГотово\n"

    write_report(output, text)

    assert output.exists()
    assert output.read_text(encoding="utf-8") == text


def test_write_report_creates_parent_directory(tmp_path: Path) -> None:
    """Перевіряє автоматичне створення каталогу для звіту."""
    output = tmp_path / "nested" / "reports" / "summary.txt"

    write_report(output, "Готово\n")

    assert output.is_file()
