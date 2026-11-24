from pathlib import Path

from kzp_lab06.cli import main


def write_csv(tmp_path: Path, content: str) -> Path:
    """Створює тимчасовий CSV-файл для CLI-тестів."""
    path = tmp_path / "input.csv"
    path.write_text(content, encoding="utf-8")
    return path


def test_cli_success_creates_report(tmp_path: Path) -> None:
    """Перевіряє успішний CLI-сценарій і код завершення 0."""
    input_csv = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n"
        "Мурка,кіт,Іван Петренко,2026-10-01,огляд,350.0\n"
        "Бім,собака,Олена Коваль,2026-10-02,вакцинація,500.0\n"
        "Рекс,собака,Андрій Мельник,2026-10-05,травма лапи,1200.0\n"
        'Луна,кіт,Ірина Савчук,2026-10-04,"аналізи, повторний контроль",700.0\n'
        "Барсик,кіт,Іван Петренко,2026-10-06,контроль,450.0\n"
        "Граф,кінь,Марія Бойко,2026-10-07,огляд,900.0\n",
    )
    output_report = tmp_path / "reports" / "summary.txt"

    exit_code = main(
        [
            str(input_csv),
            str(output_report),
        ]
    )

    assert exit_code == 0
    assert output_report.exists()

    report = output_report.read_text(encoding="utf-8")

    assert "Звіт: Ветклініка" in report
    assert "Усього записів: 6" in report
    assert "Поріг вартості: 500.00" in report
    assert "Записів дорожче порогу: 3" in report
    assert "Середня вартість: 683.33" in report
    assert "Найдорожчий візит: Рекс — 1200.00" in report


def test_cli_returns_nonzero_for_invalid_csv(
    tmp_path: Path,
    capsys,
) -> None:
    """Перевіряє ненульовий код для пошкоджених даних."""
    input_csv = write_csv(
        tmp_path,
        "animal,species,owner,date,diagnosis,cost\n"
        "Барсик,кіт,Іван Петренко,2026-10-06,контроль,abc\n",
    )
    output_report = tmp_path / "report.txt"

    exit_code = main(
        [
            str(input_csv),
            str(output_report),
        ]
    )

    captured = capsys.readouterr()

    assert exit_code == 2
    assert "Помилка даних:" in captured.err
    assert not output_report.exists()


def test_cli_returns_nonzero_for_missing_input(
    tmp_path: Path,
    capsys,
) -> None:
    """Перевіряє CLI для відсутнього вхідного файла."""
    input_csv = tmp_path / "missing.csv"
    output_report = tmp_path / "report.txt"

    exit_code = main(
        [
            str(input_csv),
            str(output_report),
        ]
    )

    captured = capsys.readouterr()

    assert exit_code == 2
    assert "Не вдалося прочитати файл" in captured.err
    assert not output_report.exists()
