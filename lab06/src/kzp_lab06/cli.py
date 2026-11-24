import argparse
import sys
from pathlib import Path

from .analysis import make_summary
from .errors import DataError
from .loader import load_records
from .reporting import format_report, write_report


def _configure_stderr_utf8() -> None:
    """Налаштовує UTF-8 для stderr, якщо потік це підтримує."""
    try:
        sys.stderr.reconfigure(encoding="utf-8")
    except (AttributeError, ValueError):
        pass


def build_parser() -> argparse.ArgumentParser:
    """Створює parser аргументів командного рядка."""
    parser = argparse.ArgumentParser(description="LAB 06 — обробка CSV ветклініки засобами Python.")
    parser.add_argument(
        "input_csv",
        type=Path,
        help="Шлях до вхідного CSV-файлу.",
    )
    parser.add_argument(
        "output_report",
        type=Path,
        help="Шлях до вихідного текстового звіту.",
    )
    return parser


def main(argv: list[str] | None = None) -> int:
    """Читає CSV, виконує аналіз і записує текстовий звіт."""
    _configure_stderr_utf8()

    parser = build_parser()
    args = parser.parse_args(argv)

    try:
        records = load_records(args.input_csv)

        summary = make_summary(
            records,
            "average_cost",
            "most_expensive",
            threshold=500.0,
            average_cost="Середня вартість",
            most_expensive="Найдорожчий візит",
        )

        report = format_report(summary)
        write_report(args.output_report, report)

    except DataError as error:
        print(f"Помилка даних: {error}", file=sys.stderr)
        return 2
    except OSError as error:
        print(f"Помилка файлу: {error}", file=sys.stderr)
        return 3

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
