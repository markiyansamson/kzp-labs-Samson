import csv
from datetime import date
from pathlib import Path

from .errors import DataError


REQUIRED_FIELDS = [
    "animal",
    "species",
    "owner",
    "date",
    "diagnosis",
    "cost",
]


def load_records(path: Path) -> list[dict[str, object]]:
    """Читає CSV ветклініки, перевіряє поля та перетворює типи."""
    try:
        with path.open("r", encoding="utf-8", newline="") as source:
            reader = csv.DictReader(source)

            if reader.fieldnames is None:
                raise DataError("Порожній CSV: відсутній заголовок")

            if reader.fieldnames != REQUIRED_FIELDS:
                raise DataError(
                    f"Некоректний заголовок CSV. Очікується: {','.join(REQUIRED_FIELDS)}"
                )

            records: list[dict[str, object]] = []

            for row_number, row in enumerate(reader, start=2):
                try:
                    if None in row:
                        raise DataError(f"Рядок {row_number}: неправильна кількість полів")

                    animal = _required_text(row, "animal", row_number)
                    species = _required_text(row, "species", row_number)
                    owner = _required_text(row, "owner", row_number)
                    diagnosis = _required_text(row, "diagnosis", row_number)

                    raw_date = _required_text(row, "date", row_number)
                    raw_cost = _required_text(row, "cost", row_number)

                    visit_date = date.fromisoformat(raw_date)
                    cost = float(raw_cost)

                    if cost < 0:
                        raise DataError(f"Рядок {row_number}: cost не може бути від'ємним")

                except DataError:
                    raise
                except (KeyError, TypeError, ValueError) as error:
                    raise DataError(f"Рядок {row_number}: некоректне значення поля") from error

                records.append(
                    {
                        "animal": animal,
                        "species": species,
                        "owner": owner,
                        "date": visit_date,
                        "diagnosis": diagnosis,
                        "cost": cost,
                    }
                )

            return records

    except DataError:
        raise
    except OSError as error:
        raise DataError(f"Не вдалося прочитати файл: {path}") from error


def _required_text(
    row: dict[str | None, str | list[str] | None],
    field: str,
    row_number: int,
) -> str:
    """Повертає нормалізоване обов'язкове текстове поле."""
    value = row.get(field)

    if not isinstance(value, str) or not value.strip():
        raise DataError(f"Рядок {row_number}: поле {field} не може бути порожнім")

    return value.strip()
