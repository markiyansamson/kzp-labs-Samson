from datetime import date
from pathlib import Path

import pytest

from kzp_lab06.errors import DataError
from kzp_lab06.loader import load_records


def write_csv(tmp_path: Path, content: str) -> Path:
    """Створює тимчасовий CSV-файл для тесту."""
    path = tmp_path / "input.csv"
    path.write_text(content, encoding="utf-8")
    return path


def test_loads_valid_record(tmp_path: Path) -> None:
    """Перевіряє читання та типізацію коректного запису."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n"
        "Мурка,кіт,Іван Петренко,2026-10-01,огляд,350.0\n",
    )

    records = load_records(path)

    assert records == [
        {
            "animal": "Мурка",
            "species": "кіт",
            "owner": "Іван Петренко",
            "date": date(2026, 10, 1),
            "diagnosis": "огляд",
            "cost": 350.0,
        }
    ]


def test_header_only_csv_returns_empty_list(tmp_path: Path) -> None:
    """Перевіряє CSV із заголовком без записів."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n",
    )

    assert load_records(path) == []


def test_comma_inside_quoted_field(tmp_path: Path) -> None:
    """Перевіряє кому всередині quoted CSV-поля."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n"
        'Луна,кіт,Ірина Савчук,2026-10-04,"аналізи, повторний контроль",700.0\n',
    )

    records = load_records(path)

    assert records[0]["diagnosis"] == "аналізи, повторний контроль"


def test_invalid_cost_preserves_cause(tmp_path: Path) -> None:
    """Перевіряє DataError та першопричину для некоректної вартості."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n"
        "Барсик,кіт,Іван Петренко,2026-10-06,контроль,abc\n",
    )

    with pytest.raises(DataError) as exc_info:
        load_records(path)

    assert isinstance(exc_info.value.__cause__, ValueError)


def test_missing_value_is_rejected(tmp_path: Path) -> None:
    """Перевіряє відсутнє обов'язкове значення."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\nМурка,кіт,,2026-10-01,огляд,350.0\n",
    )

    with pytest.raises(DataError, match="owner"):
        load_records(path)


def test_missing_column_is_rejected(tmp_path: Path) -> None:
    """Перевіряє неправильний CSV-заголовок."""
    path = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis\n",
    )

    with pytest.raises(DataError, match="Некоректний заголовок CSV"):
        load_records(path)


def test_completely_empty_csv_is_rejected(tmp_path: Path) -> None:
    """Перевіряє фізично порожній CSV-файл."""
    path = write_csv(tmp_path, "")

    with pytest.raises(DataError, match="Порожній CSV"):
        load_records(path)
