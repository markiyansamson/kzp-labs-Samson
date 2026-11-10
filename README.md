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

# Лабораторна робота №3

## Спадкування, інтерфейси й поліморфізм у Java-проєкті

### Варіант

- номер варіанта: **21**;
- предметна область: **Ветклініка**;
- версія продукту LAB 03: **3.0.0**;
- фінальний Git-тег LAB 03: **v3.0.0**.

LAB 03 є продовженням того самого програмного продукту:

```text
LAB 01 → 1.0.0 → v1.0.0
LAB 02 → 2.0.0 → v2.0.0
LAB 03 → 3.0.0 → v3.0.0
```

Попередні версії та Git-теги не змінюються.

---

## Мета LAB 03

У LAB 03 класову модель ветеринарної клініки перебудовано на поліморфну ієрархію.

Основні зміни:

- `VetVisit` перетворено на абстрактний базовий тип;
- створено підтипи `RoutineVisit` і `UrgentVisit`;
- додано `VisitKind`;
- додано поліморфний метод `finalCost()`;
- об'єкти різних підтипів зберігаються через `List<VetVisit>`;
- реалізовано `equals()` і `hashCode()`;
- рівність перевіряється через `HashSet`;
- CSV-фабрика створює конкретний підтип;
- фактична обробка використовує поліморфний метод;
- `Main` скорочено до точки запуску;
- розбір аргументів винесено до `CliParser`;
- керування обробкою винесено до `VetClinicController`;
- `pom.xml` є єдиним джерелом версії продукту.

---

## Архітектура застосунку

Після LAB 03 запуск програми організовано так:

```text
Main
 │
 ├── CliParser
 │      └── розбір аргументів командного рядка
 │
 ├── AppVersion
 │      └── читання версії, сформованої Maven
 │
 └── VetClinicController
        ├── читання CSV
        ├── VetVisit.fromCsv()
        ├── List<VetVisit>
        ├── calculateSummary()
        ├── finalCost()
        ├── формування звіту
        └── запис report.txt
```

`Main` більше не містить предметної логіки. Він лише:

1. запускає `CliParser`;
2. обробляє `--help` і `--version`;
3. створює `VetClinicController`;
4. запускає основний процес;
5. обробляє верхньорівневі помилки.

---

## Версіювання

Починаючи з LAB 03, `pom.xml` є єдиним джерелом версії.

Версія проєкту:

```xml
<version>3.0.0</version>
```

У Java-коді немає константи:

```text
VERSION = "3.0.0"
```

Maven фільтрує ресурс:

```text
src/main/resources/version.properties
```

Вихідний файл містить:

```properties
app.version=${project.version}
```

Під час Maven-збірки значення автоматично перетворюється на:

```properties
app.version=3.0.0
```

Клас `AppVersion` читає це значення під час виконання програми.

Таким чином:

```text
pom.xml
   ↓
Maven resource filtering
   ↓
version.properties
   ↓
AppVersion
   ↓
--version
```

Перевірка:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-3.0.0.jar --version
```

Очікуваний результат:

```text
3.0.0
```

---

## Предметна ієрархія

### Базовий тип VetVisit

`VetVisit` є абстрактним базовим класом.

Він містить спільний стан усіх візитів:

```text
animal
species
visitType
price
kind
```

Спільні інваріанти також перевіряються в базовому типі:

- `animal` не може бути `null` або порожнім;
- `species` не може бути `null` або порожнім;
- `visitType` не може бути `null` або порожнім;
- `price` має бути скінченним невід'ємним числом;
- `kind` не може бути `null`.

Базовий тип оголошує поліморфний метод:

```java
public abstract double finalCost();
```

---

## RoutineVisit

`RoutineVisit` є конкретним підтипом `VetVisit`.

```text
RoutineVisit is-a VetVisit
```

Для звичайного візиту підсумкова вартість дорівнює базовій:

```text
finalCost = price
```

Метод реалізовано через `@Override`.

---

## UrgentVisit

`UrgentVisit` є конкретним підтипом `VetVisit`.

```text
UrgentVisit is-a VetVisit
```

Для термінового візиту діє мінімальна підсумкова вартість:

```text
1000.00 грн
```

Тому:

```text
finalCost = max(price, 1000.00)
```

Наприклад:

```text
price = 800.00
finalCost = 1000.00
```

але:

```text
price = 1200.00
finalCost = 1200.00
```

Таке правило забезпечує різну поведінку підтипів і водночас не змінює контрольний результат LAB 02.

---

## VisitKind

Для фіксованої множини категорій використовується `enum`:

```java
VisitKind.ROUTINE
VisitKind.URGENT
```

Кожна категорія має текстове подання:

```text
ROUTINE → звичайний
URGENT  → терміновий
```

`boolean urgent` із зовнішнього CSV-формату зберігається для сумісності, але після створення об'єкта категорія представлена через `VisitKind`.

---

## CSV-фабрика

Формат даних LAB 02 збережено:

```text
animal;species;visitType;price;urgent
```

Приклад:

```text
Мурка;кіт;огляд;350.00;false
Рекс;собака;невідкладна допомога;1200.00;true
```

`VetVisit.fromCsv()` аналізує поле `urgent` та створює правильний конкретний підтип:

```text
urgent=false
     ↓
RoutineVisit

urgent=true
     ↓
UrgentVisit
```

Зовні результат повертається через спільний тип:

```java
VetVisit visit = VetVisit.fromCsv(line);
```

Таким чином формат CSV не змінився.

---

## Поліморфізм

Усі коректні записи зберігаються у:

```java
List<VetVisit>
```

Підсумкова вартість обчислюється так:

```java
for (VetVisit visit : visits) {
    totalPrice += visit.finalCost();
}
```

Код не перевіряє:

```text
instanceof RoutineVisit
instanceof UrgentVisit
```

і не використовує центральний `switch` для вибору поведінки.

Java автоматично викликає реалізацію `finalCost()` фактичного підтипу:

```text
VetVisit
   ↓
finalCost()
   ├── RoutineVisit.finalCost()
   └── UrgentVisit.finalCost()
```

---

## equals і hashCode

`VetVisit` реалізує узгоджені:

```java
equals(Object)
hashCode()
```

Логічна рівність визначається за:

- конкретним підтипом;
- `animal`;
- `species`;
- `visitType`;
- `price`.

Два об'єкти різних підтипів не вважаються рівними навіть за однакових спільних полів.

Наприклад:

```text
RoutineVisit("Боня", "кіт", "огляд", 500)
!=
UrgentVisit("Боня", "кіт", "огляд", 500)
```

Для перевірки контракту використовується `HashSet<VetVisit>`.

Два логічно рівні об'єкти одного підтипу займають один запис у множині.

---

## Збереження поведінки LAB 02

До перебудови контрольний результат LAB 02 був:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

Після переходу на:

```text
VetVisit
├── RoutineVisit
└── UrgentVisit
```

контрольний результат залишається:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

Отже зовнішній формат і попередні показники продукту збережено.

---

## Тестування

Після LAB 03 виконується:

```text
37 JUnit 5 тестів
```

Тестування охоплює:

- попередні тести LAB 01 і LAB 02;
- конструктори й інваріанти `VetVisit`;
- `RoutineVisit`;
- `UrgentVisit`;
- `VisitKind`;
- `VetVisit.fromCsv()`;
- правильний вибір підтипу;
- поліморфний `finalCost()`;
- поліморфну колекцію `List<VetVisit>`;
- `equals()`;
- `hashCode()`;
- `HashSet`;
- нерівність різних підтипів;
- `CliParser`;
- стандартні й користувацькі шляхи;
- неправильні CLI-аргументи;
- `AppVersion`;
- `VetClinicController`;
- контрольний результат LAB 02;
- використання `finalCost()` у реальному процесі.

Локальний запуск:

```powershell
.\mvnw.cmd test
```

Результат:

```text
Tests run: 37
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

---

## Статичний аналіз

Повна перевірка:

```powershell
.\mvnw.cmd clean verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

---

## Javadoc

Javadoc генерується командою:

```powershell
.\mvnw.cmd javadoc:javadoc
```

Після доповнення документації публічних класів, методів і `VisitKind` команда завершується:

```text
BUILD SUCCESS
```

без Javadoc warnings.

---

## Виконуваний JAR

Збірка:

```powershell
.\mvnw.cmd package
```

Створюється:

```text
target/lab01-3.0.0.jar
```

Перевірка версії:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-3.0.0.jar --version
```

Результат:

```text
3.0.0
```

Звичайний запуск:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-3.0.0.jar
```

---

## GitHub Actions

CI успадковано з попередніх лабораторних робіт.

Matrix-перевірка виконується на:

```text
ubuntu-latest
windows-latest
macos-latest
```

Для кожної ОС виконується Maven-перевірка.

Після успішної перевірки публікується:

```text
target/lab01-3.0.0.jar
```

як GitHub Actions artifact.

Фінальні URL для LAB 03 будуть додані після завершення Pull Request і створення тега `v3.0.0`.

---

## GitHub Issues LAB 03

Роботу розділено на окремі задачі:

```text
#17 — Refactor application bootstrap and versioning
#18 — Create VetVisit hierarchy and polymorphic cost
#19 — Add VisitKind and subtype-aware CSV factory
#20 — Implement VetVisit equality and HashSet behavior
#21 — Add polymorphism, equality and regression tests
#22 — Update README, REPORT and Javadoc
```

Усі Issues закриваються фінальним Pull Request через `Closes #N`.

---

## Основні зміни LAB 02 → LAB 03

| Ознака | LAB 02 | LAB 03 |
|---|---|---|
| Версія | `2.0.0` | `3.0.0` |
| Модель | один `VetVisit` | абстрактний `VetVisit` + підтипи |
| Категорія | `boolean urgent` | `VisitKind` у моделі |
| Підтипи | відсутні | `RoutineVisit`, `UrgentVisit` |
| Поведінка | спільний `getPrice()` | поліморфний `finalCost()` |
| Колекція | `List<VetVisit>` | `List<VetVisit>` із різними підтипами |
| Рівність | стандартна Object | `equals/hashCode` |
| Перевірка рівності | відсутня | `HashSet` |
| Main | містив обробку | лише точка запуску |
| CLI | у `Main` | `CliParser` |
| Керування процесом | `Main` | `VetClinicController` |
| Джерело версії | `pom.xml` + Java-константа | тільки `pom.xml` |
| Тести | 16 | 37 |
| JAR | `lab01-2.0.0.jar` | `lab01-3.0.0.jar` |

---

## Поточний стан LAB 03

Реалізовано:

- абстрактний `VetVisit`;
- `RoutineVisit`;
- `UrgentVisit`;
- `VisitKind`;
- `finalCost()`;
- поліморфну обробку в реальному controller;
- `equals/hashCode`;
- `HashSet`;
- CSV-фабрику підтипів;
- малий `Main`;
- `CliParser`;
- `VetClinicController`;
- Maven-based versioning;
- `AppVersion`;
- 37 JUnit 5 тестів;
- SpotBugs без дефектів;
- Javadoc без warnings;
- виконуваний JAR `3.0.0`;
- CI-конфігурацію для `lab01-3.0.0.jar`.

Фінальні GitHub Actions, artifact, Pull Request і Git-тег `v3.0.0` додаються після завершення документації та merge.

# Лабораторна робота №4

## Потокова обробка даних у Java-проєкті

### Варіант

- номер варіанта: **21**;
- предметна область: **Ветклініка**;
- версія продукту LAB 04: **4.0.0**;
- фінальний Git-тег LAB 04: **v4.0.0**.

LAB 04 продовжує той самий програмний продукт:

```text
LAB 01 → 1.0.0 → v1.0.0
LAB 02 → 2.0.0 → v2.0.0
LAB 03 → 3.0.0 → v3.0.0
LAB 04 → 4.0.0 → v4.0.0
```

Попередні версії та Git-теги не змінюються.

---

## Мета LAB 04

Мета роботи — замінити основні циклові обчислення над предметною колекцією на декларативні запити Stream API без зміни зовнішньої поведінки програми.

У LAB 04 реалізовано:

- `filter`;
- `map`;
- `Collectors.groupingBy`;
- `Collectors.summarizingDouble`;
- складений `Comparator`;
- `sorted`;
- `limit`;
- `Optional`;
- регресійне порівняння циклової та потокової реалізації;
- перевірку відсутності мутації вхідної колекції.

---

## Збереження архітектури LAB 03

LAB 04 не створює новий продукт і не змінює предметну модель LAB 03.

Зберігаються:

```text
VetVisit
├── RoutineVisit
└── UrgentVisit
```

Також зберігаються:

- `VisitKind`;
- `finalCost()`;
- `equals()` і `hashCode()`;
- `CliParser`;
- `AppVersion`;
- `VetClinicController`;
- мінімальний `Main`;
- Maven-based versioning.

`pom.xml` залишається єдиним джерелом версії.

---

## Версіювання

У `pom.xml`:

```xml
<version>4.0.0</version>
```

У Java-коді немає константи:

```text
VERSION = "4.0.0"
```

Maven автоматично формує:

```properties
app.version=4.0.0
```

у:

```text
target/classes/version.properties
```

Перевірка:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-4.0.0.jar --version
```

Результат:

```text
4.0.0
```

---

## Компонент потокових запитів

Для LAB 04 створено окремий клас:

```text
VetVisitQueries
```

Він містить чисті запити над:

```java
List<VetVisit>
```

Запити не змінюють вхідну колекцію.

Для кожного незалежного запиту створюється новий:

```java
stream()
```

---

## Адаптація варіанта до наскрізного продукту

Методичний варіант №21 передбачає ветеринарний візит і запити над його параметрами.

У наскрізному продукті після LAB 03 фактичний контракт `VetVisit` уже містить:

```text
animal
species
visitType
price
kind
```

та підтипи:

```text
RoutineVisit
UrgentVisit
```

Після перевірки LAB 04 модель `VetVisit` приведено у повну відповідність до варіанта №21.

Предметна модель тепер містить:

- `animal` — тварина;
- `species` — вид;
- `owner` — власник;
- `date` — дата візиту типу `LocalDate`;
- `diagnosis` — діагноз;
- `price` — вартість;
- `kind` — категорія візиту.

CSV-формат:

```text
animal;species;owner;date;diagnosis;price;urgent

Потокові запити реалізовано так:

```text
filter       → price > threshold
map          → species
groupingBy   → кількість за species
statistics   → price
top-N        → price ↓, animal ↑
Optional     → пошук за owner
```

Ця адаптація зберігає наскрізну модель LAB 03 та дозволяє перевірити всі обов’язкові Stream API операції.

---

## 1. Filter — відбір за вартістю

Метод:

```java
visitsAbovePrice(
        List<VetVisit> visits,
        double threshold)
```

Конвеєр:

```text
List<VetVisit>
    ↓
stream()
    ↓
filter(price > threshold)
    ↓
toList()
```

Реалізація використовує строгу умову:

```java
.filter(visit -> visit.getPrice() > threshold)
```

Запис із ціною рівно порогу не потрапляє до результату.

Наприклад:

```text
threshold = 500.00
```

Для цін:

```text
350
500
700
1200
```

результат містить:

```text
700
1200
```

---

## 2. Map — перетворення у види тварин

Метод:

```java
species(List<VetVisit> visits)
```

Конвеєр:

```text
List<VetVisit>
    ↓
stream()
    ↓
map(VetVisit::getSpecies)
    ↓
List<String>
```

Метод повертає види тварин у початковому порядку.

Наприклад:

```text
кіт
собака
собака
кінь
кіт
```

Повторення не видаляються, оскільки `distinct()` не є частиною контракту цього запиту.

---

## 3. groupingBy — кількість візитів за видом

Метод:

```java
countBySpecies(List<VetVisit> visits)
```

Конвеєр:

```text
List<VetVisit>
    ↓
stream()
    ↓
Collectors.groupingBy(
    VetVisit::getSpecies,
    TreeMap::new,
    Collectors.counting())
    ↓
Map<String, Long>
```

Приклад результату:

```text
кінь    → 1
кіт     → 3
собака  → 2
```

`TreeMap` використовується для стабільного порядку ключів.

---

## 4. Зведена статистика вартості

Метод:

```java
priceStatistics(List<VetVisit> visits)
```

Використовується:

```java
Collectors.summarizingDouble(
        VetVisit::getPrice)
```

Результат:

```java
DoubleSummaryStatistics
```

Він містить:

```text
count
sum
min
max
average
```

Для тестового набору з шести записів:

```text
count = 6
sum = 4400.00
min = 350.00
max = 1200.00
average ≈ 733.33
```

Для порожнього списку:

```text
count = 0
sum = 0.0
```

Порожній випадок у представленні звіту повинен оброблятися явно.

---

## 5. Top-N зі складеним comparator

Метод:

```java
topByPrice(
        List<VetVisit> visits,
        int limit)
```

Основний критерій:

```text
price — за спаданням
```

Другий критерій:

```text
date — за зростанням
```

Comparator:

```java
Comparator.comparingDouble(VetVisit::getPrice)
        .reversed()
        .thenComparing(VetVisit::getAnimal);
```

Конвеєр:

```text
List<VetVisit>
    ↓
stream()
    ↓
sorted(comparator)
    ↓
limit(N)
    ↓
toList()
```

Важливо:

```text
sorted(...)
↓
limit(N)
```

а не навпаки.

Для LAB 04 основний top-N:

```text
N = 5
```

Для перевірки використовується щонайменше 6 записів.

Граничні випадки:

```text
N < 0   → IllegalArgumentException
N = 0   → порожній список
N > size → усі доступні записи
```

Якщо дві ціни однакові, застосовується другий критерій.

Наприклад:

```text
Альфа → 1200
Рекс  → 1200
```

Результат:

```text
Альфа
Рекс
```

---

## 6. Optional-пошук

Метод:

```java
findByOwner(
        List<VetVisit> visits,
        String animal)
```

Конвеєр:

```text
List<VetVisit>
    ↓
stream()
    ↓
filter(...)
    ↓
findFirst()
    ↓
Optional<VetVisit>
```

Метод не повертає `null`.

Знайдений результат:

```text
Optional<VetVisit>
```

Відсутній результат:

```text
Optional.empty()
```

Якщо ключ дублюється, `findFirst()` повертає перший запис у початковому порядку.

---

## Stream API у реальному процесі

LAB 04 не обмежується окремим `VetVisitQueries`.

Основне обчислення `VetClinicController.calculateSummary()` також переведено з ручного циклу на Stream API.

Було:

```java
double totalPrice = 0.0;
int urgentCount = 0;

for (VetVisit visit : visits) {
    totalPrice += visit.finalCost();

    if (visit.isUrgent()) {
        urgentCount++;
    }
}
```

Стало:

```java
double totalPrice = visits.stream()
        .mapToDouble(VetVisit::finalCost)
        .sum();

int urgentCount = Math.toIntExact(
        visits.stream()
                .filter(VetVisit::isUrgent)
                .count());
```

Поліморфізм LAB 03 збережено, оскільки використовується:

```java
VetVisit::finalCost
```

---

## Порівняння цикл → Stream API

| Операція | Циклова реалізація | Stream API |
|---|---|---|
| Відбір | `if` у циклі | `filter` |
| Перетворення | ручне додавання | `map` |
| Групування | `Map` + ручне оновлення | `groupingBy` |
| Статистика | ручні accumulator variables | `summarizingDouble` |
| Сортування | ручне сортування списку | `sorted` |
| Top-N | сортування + ручне усікання | `sorted().limit()` |
| Пошук | цикл + службове значення | `findFirst()` + `Optional` |
| Сума | accumulator у `for` | `mapToDouble().sum()` |
| Кількість urgent | `if` + counter | `filter().count()` |

---

## Регресійна перевірка

Створено окремий:

```text
VetClinicStreamRegressionTest
```

У тесті попередня циклова реалізація використовується як еталон.

Порівнюються:

```text
legacy loop
        ↓
totalPrice
urgentCount
```

та:

```text
Stream API
        ↓
VetClinicController.calculateSummary()
```

Результати повинні бути однаковими.

---

## Збереження поведінки LAB 03

Контрольний результат до LAB 04:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

Після переходу на Stream API результат:

```text
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2
```

Отже:

```text
LAB 03 output == LAB 04 regression output
```

---

## Тестування

Після LAB 03:

```text
37 тестів
```

Після додавання Stream API:

```text
61 JUnit 5 тестів
```

Нові тести перевіряють:

- filter;
- значення на межі порога;
- неправильний поріг;
- map;
- дублікати;
- groupingBy;
- statistics;
- top-5;
- складений comparator;
- tie-breaker;
- `N = 0`;
- `N > size`;
- `N < 0`;
- знайдений Optional;
- `Optional.empty()`;
- дубльований ключ;
- порожній список;
- відсутність мутації;
- еквівалентність циклу та Stream API.

Команда:

```powershell
.\mvnw.cmd test
```

Результат:

```text
Tests run: 50
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

---

## SpotBugs

Повна перевірка:

```powershell
.\mvnw.cmd clean verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

---

## Javadoc

Команда:

```powershell
.\mvnw.cmd javadoc:javadoc
```

Результат:

```text
BUILD SUCCESS
```

Javadoc генерується без warnings.

---

## Виконуваний JAR

Команда:

```powershell
.\mvnw.cmd clean package
```

Створюється:

```text
target/lab01-4.0.0.jar
```

Перевірка:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -jar .\target\lab01-4.0.0.jar --version
```

Результат:

```text
4.0.0
```

---

## GitHub Issues LAB 04

Роботу розділено на:

```text
#26 — Implement price filter with Stream API
#27 — Implement species mapping with Stream API
#28 — Group VetVisit records by species
#29 — Add visit price summary statistics
#30 — Implement top-N visits with compound comparator
#31 — Add Optional search for VetVisit
#32 — Add Stream API and regression tests
#33 — Update README REPORT and Javadoc
#34 — Update version CI and release infrastructure
```

---

## GitHub Actions

CI виконує перевірку на:

```text
ubuntu-latest
windows-latest
macos-latest
```

Artifact LAB 04:

```text
target/lab01-4.0.0.jar
```

Фінальні URL для:

```text
Pull Request
GitHub Actions
JAR artifact
Release v4.0.0
```

будуть додані після завершення merge та створення фінального Git-тега.

---

## Поточний стан LAB 04

Реалізовано:

- версію `4.0.0`;
- `VetVisitQueries`;
- filter;
- map;
- groupingBy;
- summarizingDouble;
- складений comparator;
- sorted + limit;
- Optional;
- Stream API у `VetClinicController`;
- regression cycle → Stream API;
- 61 JUnit 5 тестів;
- перевірку порожнього входу;
- перевірку дубльованого ключа;
- перевірку tie-breaker;
- перевірку граничного N;
- перевірку відсутності мутації;
- SpotBugs без дефектів;
- Javadoc без warnings;
- executable JAR `4.0.0`;
- CI artifact path `lab01-4.0.0.jar`.

Фінальний стан LAB 04 буде зафіксований тегом:

```text
v4.0.0
```
# Лабораторна робота №5

## Параметризоване сховище, винятки й CSV-експорт у Java-проєкті

### Варіант

**Варіант №21 — Ветклініка**

Предметний запис:

```text
VetVisitRecord:
animal, species, owner, date, diagnosis, cost
```

Типи полів:

```text
animal      String
species     String
owner       String
date        LocalDate
diagnosis   String
cost        double
```

Ключ запису:

```text
owner + date
```

Версія LAB 05:

```text
5.0.0
```

Git tag:

```text
v5.0.0
```

---

## Мета роботи

Мета LAB 05 — замінити безпосереднє накопичення даних у списку на типобезпечне параметризоване сховище, реалізувати власний checked exception, runtime-анотацію для CSV-полів, універсальний рефлексивний CSV exporter, коректний CSV parser та повний round-trip:

```text
object → CSV → object
```

При цьому функціональність попередніх LAB 01–04 повинна залишитися працездатною.

---

## Основні зміни

У LAB 05 реалізовано:

- параметризоване сховище `Repository<T>`;
- `Predicate<? super T>` для пошуку;
- незмінний snapshot через `List.copyOf`;
- checked exception `DataStorageException`;
- збереження першопричини помилки через `cause`;
- runtime-анотацію `@CsvColumn`;
- generic reflective `CsvExporter`;
- детермінований порядок CSV-полів;
- UTF-8 читання та запис;
- CSV escaping;
- підтримку ком, подвійних лапок та multiline-полів;
- документний `CsvParser`;
- `VetVisitRecord`;
- `VetVisitCsvStorage`;
- CSV round-trip;
- інтеграцію `Repository<VetVisit>` у `VetClinicController`;
- нові unit та integration tests;
- оновлення CI для версії `5.0.0`.

---

## Repository<T>

Створено універсальне типобезпечне сховище:

```java
Repository<T>
```

Основні операції:

```java
void add(T item)

List<T> all()

List<T> find(Predicate<? super T> condition)
```

`add()` не дозволяє додавати `null`.

Метод:

```java
all()
```

повертає незмінний snapshot через:

```java
List.copyOf(items)
```

Тому зовнішній код не отримує прямого доступу до внутрішньої mutable-колекції.

Пошук виконується через:

```java
Predicate<? super T>
```

без використання raw types.

У `VetClinicController` використовується:

```java
Repository<VetVisit> visitRepository = new Repository<>();
```

---

## DataStorageException

Для помилок роботи зі сховищем та CSV створено checked exception:

```java
DataStorageException
```

Підтримуються конструктори:

```java
DataStorageException(String message)

DataStorageException(String message, Throwable cause)
```

Другий конструктор дозволяє не втрачати технічну першопричину помилки.

Наприклад:

```text
DataStorageException
└── cause: IOException
```

або:

```text
DataStorageException
└── cause: NumberFormatException
```

---

## @CsvColumn

Для опису CSV-стовпців створено власну анотацію:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvColumn {
    String value();
}
```

`RetentionPolicy.RUNTIME` дозволяє читати анотацію під час виконання програми через reflection.

`ElementType.FIELD` обмежує її використання полями класу.

Приклад:

```java
@CsvColumn("тварина")
private final String animal;
```

---

## VetVisitRecord

Для CSV export/import створено окреме представлення:

```java
VetVisitRecord
```

Воно не замінює поліморфну модель `VetVisit`, а використовується як плоский запис для зберігання.

Перетворення:

```java
VetVisitRecord.fromVisit(VetVisit visit)
```

Для перевірки round-trip реалізовано:

```java
equals()
hashCode()
```

---

## Рефлексивний CsvExporter

Створено універсальний exporter:

```java
public static <T> void write(
        Path path,
        Class<T> type,
        List<? extends T> records)
```

Exporter:

1. отримує поля класу через reflection;
2. вибирає поля з `@CsvColumn`;
3. перевіряє доступ до полів;
4. сортує їх за Java-іменем;
5. формує CSV header;
6. читає значення полів;
7. виконує CSV escaping;
8. записує файл у UTF-8.

Exporter не містить предметних перевірок на зразок:

```text
if VetVisitRecord
if RoutineVisit
if UrgentVisit
```

Тому він може працювати з різними типами об'єктів, які використовують `@CsvColumn`.

---

## Детермінований порядок CSV

Поля `VetVisitRecord` сортуються за Java-іменами:

```text
animal
cost
date
diagnosis
owner
species
```

Тому фактичний CSV header:

```text
тварина,вартість,дата,діагноз,власник,вид
```

Порядок залишається стабільним між запусками.

---

## CSV escaping

Exporter підтримує значення, що містять:

- кому `,`;
- подвійну лапку `"`;
- `\n`;
- `\r`.

Значення зі спеціальними символами береться у подвійні лапки.

Подвійні лапки всередині значення подвоюються.

Наприклад значення:

```text
аналізи, "повторно"
контроль
```

записується як коректне quoted multiline CSV-поле.

---

## CsvParser

Для імпорту реалізовано:

```java
parse(String line)

parseDocument(String text)
```

CSV-документ не розбирається через:

```java
split(",")
```

оскільки такий підхід не працює для:

- ком усередині quoted-поля;
- escaped quotes;
- перенесень рядка всередині поля.

`CsvParser` використовує посимвольний state-machine parsing.

Підтримуються:

- quoted fields;
- escaped quotes;
- multiline fields;
- CR/LF;
- перевірка незакритих лапок;
- перевірка некоректного символу після закритої лапки.

---

## VetVisitCsvStorage

Для предметного читання CSV створено:

```java
VetVisitCsvStorage
```

Він:

- читає файл через `Files.readString`;
- явно використовує `StandardCharsets.UTF_8`;
- перевіряє CSV header;
- використовує `CsvParser.parseDocument`;
- відновлює `VetVisitRecord`;
- повертає незмінний список;
- загортає `IOException` у `DataStorageException` із збереженням `cause`.

---

## CSV round-trip

У LAB 05 реалізований повний цикл:

```text
input.csv
    ↓
VetVisit.fromCsv(...)
    ↓
Repository<VetVisit>
    ↓
VetVisitRecord
    ↓
CsvExporter
    ↓
out/vet-visits.csv
    ↓
VetVisitCsvStorage
    ↓
CsvParser
    ↓
VetVisitRecord
    ↓
equals()
```

Після експорту CSV одразу читається назад.

Відновлений список порівнюється з початковим.

Якщо:

```text
original != restored
```

операція завершується помилкою.

У коректному випадку:

```text
original == restored
```

Round-trip успішний.

---

## Фактичний CSV

Після запуску застосунку створюється:

```text
out/vet-visits.csv
```

Для наявного вхідного набору отримано:

```csv
тварина,вартість,дата,діагноз,власник,вид
Мурка,350.0,2026-10-01,огляд,Іван Петренко,кіт
Бім,500.0,2026-10-02,вакцинація,Олена Коваль,собака
Рекс,1200.0,2026-10-05,травма лапи,Андрій Мельник,собака
```

Файл записується та читається у:

```text
UTF-8
```

---

## Набір із 5 записів

Для integration-тестування LAB 05 використано п'ять записів варіанта №21.

Серед них є запис із полем:

```text
аналізи, "повторно"
контроль
```

яке одночасно містить:

- кому;
- лапки;
- перенесення рядка.

Це дозволяє перевірити не лише звичайний CSV, але й escaping та multiline parsing.

---

## Обробка помилок

Перевірено такі ситуації:

| Ситуація | Результат |
|---|---|
| `null` у `Repository` | помилка |
| `null` predicate | помилка |
| клас без `@CsvColumn` | `DataStorageException` |
| неправильна кількість полів | `DataStorageException` |
| неправильний `cost` | `DataStorageException` + `NumberFormatException` |
| неправильна `date` | `DataStorageException` + `DateTimeParseException` |
| незакриті CSV-лапки | `DataStorageException` |
| неправильний CSV header | `DataStorageException` |
| помилка файлової системи | `DataStorageException` + `IOException` |
| невідповідність round-trip | помилка зберігання |

---

## Збереження функціональності LAB 04

Після переходу на `Repository<T>` та додавання CSV round-trip попередня функціональність застосунку не змінилася.

Контрольний результат `out/report.txt`:

```text
Звіт: Ветклініка
Коректних записів: 3
Середня вартість: 683.33
Загальний виторг: 2050.00
Ургентних візитів: 1
Помилок: 2

Помилки:
Рядок 4: price має бути скінченним невід'ємним числом
Рядок 5: поле price має бути числом
```

Отже попередня поведінка LAB 04 збережена.

---

## Тестування

До початку LAB 05:

```text
Tests run: 62
Failures: 0
Errors: 0
Skipped: 0
```

Після реалізації LAB 05:

```text
Tests run: 96
Failures: 0
Errors: 0
Skipped: 0
```

Усі попередні тести LAB 01–04 продовжують проходити.

Додатково перевірено:

- `Repository<T>`;
- immutable snapshot;
- пошук через predicate;
- `DataStorageException`;
- збереження `cause`;
- `@CsvColumn`;
- reflection;
- deterministic field order;
- empty dataset;
- CSV header;
- comma escaping;
- quote escaping;
- multiline fields;
- неправильну кількість полів;
- неправильне число;
- неправильну дату;
- file-system error;
- round-trip;
- набір із п'яти предметних записів.

---

## SpotBugs

Виконано:

```powershell
.\mvnw.cmd clean verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

---

## Javadoc

Виконано:

```powershell
.\mvnw.cmd javadoc:javadoc
```

Результат:

```text
BUILD SUCCESS
```

Javadoc warnings:

```text
0
```

Публічні класи, конструктори, методи та анотація LAB 05 задокументовані.

---

## JAR

Версія:

```text
5.0.0
```

Виконуваний JAR:

```text
target/lab01-5.0.0.jar
```

Локальна версія:

```text
5.0.0+build.local
```

Перевірка механізму CI build number:

```text
5.0.0+build.123
```

---

## GitHub Issues

Для LAB 05 використані Issues:

- [#41 — LAB 05: Add generic Repository<T>](https://github.com/markiyansamson/kzp-labs-Samson/issues/41)
- [#42 — LAB 05: Add DataStorageException](https://github.com/markiyansamson/kzp-labs-Samson/issues/42)
- [#43 — LAB 05: Add @CsvColumn annotation](https://github.com/markiyansamson/kzp-labs-Samson/issues/43)
- [#44 — LAB 05: Implement generic reflective CSV exporter](https://github.com/markiyansamson/kzp-labs-Samson/issues/44)
- [#45 — LAB 05: Implement CSV parser and VetVisitRecord round-trip](https://github.com/markiyansamson/kzp-labs-Samson/issues/45)
- [#46 — LAB 05: Add storage, CSV and round-trip tests](https://github.com/markiyansamson/kzp-labs-Samson/issues/46)
- [#47 — LAB 05: Update infrastructure and documentation](https://github.com/markiyansamson/kzp-labs-Samson/issues/47)

---

## Pull Request

Основний Pull Request LAB 05:

[#48 — LAB 05: Add generic storage and CSV round-trip](https://github.com/markiyansamson/kzp-labs-Samson/pull/48)

У PR успішно пройшли перевірки:

```text
Ubuntu  ✅
Windows ✅
macOS   ✅
```

Для подій `push` та `pull_request`.

Перевірена CI-версія:

```text
5.0.0+build.52
```

---

## CI після merge у main

Після merge PR #48 виконано CI на `main`.

CI run:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/42908558207

Результат:

```text
verify (ubuntu-latest)  ✅
verify (windows-latest) ✅
verify (macos-latest)   ✅
```

JAR artifact:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/42908558207/artifacts/13805193850

---

## Git tag v5.0.0

Фінальна версія LAB 05 позначена Git tag:

```text
v5.0.0
```

GitHub tag reference:

https://github.com/markiyansamson/kzp-labs-Samson/tree/v5.0.0

Для тегу також успішно виконано CI.

Tag CI:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/42908964168

Результат:

```text
verify (ubuntu-latest)  ✅
verify (windows-latest) ✅
verify (macos-latest)   ✅
```

Tag artifact:

https://github.com/markiyansamson/kzp-labs-Samson/actions/runs/42908964168/artifacts/13807095212

---

## Підсумковий стан LAB 05

```text
Repository<T>              ✅
DataStorageException       ✅
@CsvColumn                 ✅
Reflective CsvExporter     ✅
CsvParser                  ✅
VetVisitRecord             ✅
VetVisitCsvStorage         ✅
UTF-8                      ✅
CSV escaping               ✅
Multiline CSV              ✅
Round-trip                 ✅
Regression LAB 01–04       ✅
JUnit 5 tests: 96          ✅
Failures: 0                ✅
Errors: 0                  ✅
SpotBugs: 0                ✅
Javadoc warnings: 0        ✅
Ubuntu CI                  ✅
Windows CI                 ✅
macOS CI                   ✅
Version 5.0.0              ✅
Tag v5.0.0                 ✅
```