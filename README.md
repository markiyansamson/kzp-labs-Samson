# Кросплатформні засоби програмування

## Лабораторна робота №1

**Варіант 21 — Ветклініка**

Консольна Java-програма для читання та перевірки даних про візити до ветеринарної клініки, обчислення статистичних показників і формування текстового звіту.

## Формат вхідних даних

Вхідні дані зберігаються у файлі `data/input.csv`.

Один рядок містить п'ять полів, розділених символом `;`:

```text
animal;species;visitType;price;urgent
```

Поля:

- `animal` — ім'я тварини;
- `species` — вид тварини;
- `visitType` — тип візиту;
- `price` — вартість візиту (`double`);
- `urgent` — ознака ургентного візиту (`true` або `false`).

Приклад:

```text
Мурка;кіт;огляд;350.00;false
Рекс;собака;невідкладна допомога;1200.00;true
```

## Валідація

Програма перевіряє:

- наявність рівно п'яти полів;
- непорожні `animal`, `species` і `visitType`;
- коректний числовий формат `price`;
- невід'ємне та скінченне значення `price`;
- значення `urgent` тільки `true` або `false`.

Помилка одного рядка не завершує всю програму. Для хибного запису виводиться номер рядка та причина помилки.

## Обчислювані показники

Програма визначає:

1. кількість коректних записів;
2. середню вартість візиту;
3. загальний виторг;
4. кількість ургентних візитів.

## Структура проєкту

```text
kzp-labs-samson/
├── data/
│   └── input.csv
├── src/
│   └── main/
│       └── java/
│           └── ua/lpnu/kzp/vetclinic/
│               └── Main.java
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
└── REPORT.md
```

## Вимоги

- Java 21
- Maven Wrapper

## Компіляція

У Windows:

```powershell
.\mvnw.cmd compile
```

## Запуск

Зі стандартним файлом `data/input.csv`:

```powershell
java -cp target\classes ua.lpnu.kzp.vetclinic.Main
```

## Довідка

```powershell
java -cp target\classes ua.lpnu.kzp.vetclinic.Main --help
```

## Власний вхідний файл

```powershell
java -cp target\classes ua.lpnu.kzp.vetclinic.Main --input data\input.csv
```

## Власний вихідний файл

```powershell
java -cp target\classes ua.lpnu.kzp.vetclinic.Main --output out\report.txt
```

За замовчуванням звіт записується у:

```text
out/report.txt
```

## Приклад результату

Для контрольного набору:

```text
Мурка;кіт;огляд;350.00;false
Бім;собака;вакцинація;500.00;false
Рекс;собака;невідкладна допомога;1200.00;true
Луна;кіт;аналізи;-100.00;false
Боня;папуга;огляд;abc;true
```

очікувані показники:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

## Автор

Маркіян Самсон
## Автоматизоване тестування

У проєкті використовується JUnit 5.

Для запуску тестів:

```powershell
.\mvnw.cmd test
```

Результат:

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Тести перевіряють:

- обробку коректних і некоректних записів;
- ситуацію, коли немає жодного коректного запису;
- неправильне значення поля `urgent`.

## Статичний аналіз

Для статичного аналізу використовується SpotBugs.

Запуск:

```powershell
.\mvnw.cmd verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

## Створення виконуваного JAR

Для створення JAR:

```powershell
.\mvnw.cmd package
```

Після успішної збірки створюється:

```text
target/lab01-1.0.0.jar
```

Запуск на Java 21:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-1.0.0.jar
```

Довідка:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-1.0.0.jar --help
```