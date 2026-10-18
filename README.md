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
## Рівень 3 — GitHub Actions

Для автоматичної перевірки проєкту використовується GitHub Actions.

CI запускається на трьох операційних системах:

- Ubuntu;
- Windows;
- macOS.

На кожній операційній системі використовується Java 21 та виконується Maven-перевірка `verify`.

Під час CI перевіряються:

- компіляція проєкту;
- JUnit 5 тести;
- статичний аналіз SpotBugs;
- створення виконуваного JAR.

Результати CI:

- Ubuntu — SUCCESS
- Windows — SUCCESS
- macOS — SUCCESS

Сторінка GitHub Actions:

https://github.com/markiyansamson/kzp-labs-Samson/actions

Успішний запуск CI:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/37108140099

JAR-артефакт:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/37108140099/artifacts/11268970570

## Версія програми

Поточна версія:

```text
1.0.0
```

Перевірка версії:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-1.0.0.jar
```

Результат:

```text
1.0.0
```

Git-тег версії: v1.0.0

# Лабораторна робота №2

## Класи, record та інкапсуляція

Лабораторна робота №2 є продовженням лабораторної роботи №1
для того самого консольного Java-застосунку предметної області
«Ветклініка».

Фінальна версія лабораторної роботи №1 збережена у Git-тезі:

```text
v1.0.0
```

У лабораторній роботі №2 основну версію продукту оновлено до:

```text
2.0.0
```

Рядкове представлення записів перебудовано на об'єктну модель.
Для одного візиту використовується клас `VetVisit`, а для
допоміжного результату — record `PriceUrgency`.

## Клас VetVisit

Для представлення одного візиту до ветеринарної клініки
створено клас `VetVisit`.

Клас містить поля:

- `animal` — кличка або назва тварини;
- `species` — вид тварини;
- `visitType` — тип візиту;
- `price` — вартість візиту;
- `urgent` — ознака термінового візиту.

Усі поля оголошені як `private final`.

Сеттери відсутні, тому після успішного створення стан
об'єкта не змінюється.

## Валідація VetVisit

Конструктор `VetVisit` перевіряє предметні інваріанти:

- `animal` не може бути `null`, порожнім або blank;
- `species` не може бути `null`, порожнім або blank;
- `visitType` не може бути `null`, порожнім або blank;
- `price` не може бути від'ємним;
- `price` повинен бути скінченним числом;
- `NaN`, `Infinity` і `-Infinity` не допускаються.

Якщо значення порушує інваріант, генерується
`IllegalArgumentException` і некоректний об'єкт не створюється.

## Фабричний метод fromCsv

Для перетворення CSV-рядка на об'єкт предметної області
використовується:

```java
VetVisit.fromCsv(line)
```

Метод `fromCsv`:

- перевіряє наявність рівно п'яти полів;
- виконує розбір `price`;
- перевіряє значення `urgent`;
- викликає основний конструктор `VetVisit`;
- не дублює предметну валідацію конструктора.

Номер рядка CSV не є частиною `VetVisit`, тому додається
на рівні циклу обробки у `Main`.

## Інтеграція предметної моделі у Main

У фінальній реалізації LAB 02 `Main` більше не виконує
ручний розбір полів CSV.

Коректні записи зберігаються у:

```java
List<VetVisit>
```

Фактичний шлях обробки одного коректного рядка:

```text
CSV-рядок
    ↓
VetVisit.fromCsv()
    ↓
конструктор VetVisit
    ↓
VetVisit
    ↓
List<VetVisit>
```

У `Main` використовується:

```java
VetVisit visit = VetVisit.fromCsv(line);
visits.add(visit);
```

Після формування списку коректних об'єктів викликається:

```java
PriceUrgency summary = calculateSummary(visits);
```

Таким чином предметна модель LAB 02 використовується
безпосередньо у фактичній обробці даних.

## Record PriceUrgency

Для представлення допоміжного підсумку створено:

```java
public record PriceUrgency(
        double totalPrice,
        int urgentCount)
```

Record містить:

- `totalPrice` — загальну вартість коректних візитів;
- `urgentCount` — кількість термінових візитів.

Перевіряється, що `totalPrice` є скінченним невід'ємним
числом, а `urgentCount` не є від'ємним.

## Обчислення статистики

Метод:

```java
Main.calculateSummary(visits)
```

приймає `List<VetVisit>` та формує `PriceUrgency`.

Після цього програма обчислює:

1. кількість коректних записів;
2. середню вартість;
3. загальний виторг;
4. кількість ургентних візитів.

Некоректні записи не додаються до `List<VetVisit>` і
не впливають на статистику.

## Порівняння LAB 01 і LAB 02

| Характеристика | LAB 01 | LAB 02 |
|---|---|---|
| Версія | `1.0.0` | `2.0.0` |
| Представлення запису | рядок та окремі значення | `VetVisit` |
| Розбір CSV | у `Main` | `VetVisit.fromCsv()` |
| Валідація | під час ручної обробки | конструктор `VetVisit` |
| Набір коректних записів | окремі значення | `List<VetVisit>` |
| Допоміжний результат | окремі змінні | `PriceUrgency` |
| Перевірка NaN/Infinity | базова | `Double.isFinite()` |
| Автоматизовані тести | 3 | 16 |

## Автоматизоване тестування

У LAB 02 виконується 16 JUnit 5 тестів:

- `MainTest` — 4 тести;
- `VetVisitTest` — 8 тестів;
- `PriceUrgencyTest` — 4 тести.

Запуск:

```powershell
.\mvnw.cmd test
```

Фінальний результат:

```text
Tests run: 16
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

Тест `processesVisitsThroughDomainModel()` окремо перевіряє
предметний шлях:

```text
VetVisit.fromCsv()
        ↓
List<VetVisit>
        ↓
Main.calculateSummary()
        ↓
PriceUrgency
```

Старі тести лабораторної роботи №1 продовжують проходити,
що підтверджує відсутність регресії.

## Повна Maven-перевірка

Для повної перевірки:

```powershell
.\mvnw.cmd clean verify
```

Під час `verify` запускаються JUnit 5 тести та SpotBugs.

Очікуваний результат:

```text
Tests run: 16, Failures: 0, Errors: 0, Skipped: 0

BugInstance size is 0
Error size is 0

BUILD SUCCESS
```

## Створення виконуваного JAR

Для створення JAR:

```powershell
.\mvnw.cmd package
```

Створюється:

```text
target/lab01-2.0.0.jar
```

Запуск:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-2.0.0.jar
```

Перевірка версії:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-2.0.0.jar --version
```

Результат:

```text
2.0.0
```

## GitHub Actions

Проєкт перевіряється у GitHub Actions на:

- Ubuntu;
- Windows;
- macOS.

На кожній операційній системі використовується Java 21
та виконується Maven-фаза `verify`.

CI перевіряє:

- компіляцію;
- 16 JUnit 5 тестів;
- SpotBugs;
- створення JAR;
- сумісність на трьох операційних системах.

Сторінка GitHub Actions:

https://github.com/markiyansamson/kzp-labs-Samson/actions

## Версії продукту

Лабораторна робота №1:

```text
1.0.0
```

Git-тег:

```text
v1.0.0
```

Лабораторна робота №2:

```text
2.0.0
```

Git-тег:

```text
v2.0.0
```

Release LAB 02:

https://github.com/markiyansamson/kzp-labs-Samson/releases/tag/v2.0.0

## Перевірка зовнішньої поведінки

Для того самого контрольного набору даних після рефакторингу
отримано:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

Ці показники збігаються з LAB 01.

Отже, лабораторна робота №2 змінила внутрішню архітектуру
програми, але не змінила її очікувану зовнішню поведінку.