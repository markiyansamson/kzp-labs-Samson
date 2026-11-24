from pathlib import Path


def format_report(summary: dict[str, object]) -> str:
    """Формує детермінований текстовий звіт ветклініки."""
    lines = [
        "Звіт: Ветклініка",
        f"Усього записів: {summary['total_count']}",
        f"Поріг вартості: {float(summary['threshold']):.2f}",
        f"Записів дорожче порогу: {summary['selected_count']}",
    ]

    species_counts = summary["species_counts"]
    if isinstance(species_counts, dict):
        lines.append("")
        lines.append("Кількість візитів за видом:")

        for species, count in sorted(species_counts.items()):
            lines.append(f"{species}: {count}")

    owners = summary["owners"]
    if isinstance(owners, set):
        lines.append("")
        lines.append("Власники:")

        for owner in sorted(owners):
            lines.append(owner)

    average = summary.get("Середня вартість")
    if isinstance(average, (int, float)):
        lines.append("")
        lines.append(f"Середня вартість: {float(average):.2f}")

    most_expensive = summary.get("Найдорожчий візит")
    if isinstance(most_expensive, dict):
        lines.append(
            f"Найдорожчий візит: {most_expensive['animal']} — {float(most_expensive['cost']):.2f}"
        )
    elif most_expensive is None:
        lines.append("Найдорожчий візит: немає даних")

    return "\n".join(lines) + "\n"


def write_report(path: Path, text: str) -> None:
    """Створює каталог і записує звіт у UTF-8."""
    path.parent.mkdir(parents=True, exist_ok=True)

    with path.open(
        "w",
        encoding="utf-8",
        newline="\n",
    ) as target:
        target.write(text)
