from typing import Any


Record = dict[str, object]


def filter_by_cost(
    records: list[Record],
    threshold: float = 500.0,
) -> list[Record]:
    """Повертає візити з вартістю строго більшою за заданий поріг."""
    return [record for record in records if float(record["cost"]) > threshold]


def count_by_species(records: list[Record]) -> dict[str, int]:
    """Повертає кількість візитів за видами тварин у стабільному порядку."""
    species = {str(record["species"]) for record in records}

    return {
        item: sum(1 for record in records if record["species"] == item) for item in sorted(species)
    }


def unique_owners(records: list[Record]) -> set[str]:
    """Повертає множину унікальних власників."""
    return {str(record["owner"]) for record in records}


def average_cost(records: list[Record]) -> float:
    """Повертає середню вартість усіх візитів."""
    if not records:
        return 0.0

    return sum(float(record["cost"]) for record in records) / len(records)


def most_expensive_visit(records: list[Record]) -> Record | None:
    """Повертає найдорожчий візит або None для порожнього набору."""
    if not records:
        return None

    return max(
        records,
        key=lambda record: (
            float(record["cost"]),
            str(record["animal"]),
        ),
    )


def make_summary(
    records: list[Record],
    *metrics: str,
    threshold: float = 500.0,
    **labels: str,
) -> dict[str, Any]:
    """Формує підсумок і вибрані метрики для ветеринарних візитів."""
    selected = filter_by_cost(records, threshold)

    result: dict[str, Any] = {
        "total_count": len(records),
        "selected_count": len(selected),
        "threshold": threshold,
        "species_counts": count_by_species(records),
        "owners": unique_owners(records),
    }

    available_metrics = {
        "average_cost": average_cost(records),
        "most_expensive": most_expensive_visit(records),
    }

    for metric in metrics:
        if metric not in available_metrics:
            raise ValueError(f"Невідома метрика: {metric}")

        result[labels.get(metric, metric)] = available_metrics[metric]

    return result
