package ua.lpnu.kzp.vetclinic;

import java.io.IOException;

/**
 * Точка входу консольного застосунку ветеринарної клініки.
 */
public final class Main {

    private Main() {
    }

    /**
     * Запускає застосунок.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        try {
            AppArguments arguments = CliParser.parse(args);

            if (arguments.helpRequested()) {
                System.out.println(CliParser.usage());
                return;
            }

            if (arguments.versionRequested()) {
                System.out.println(AppVersion.get());
                return;
            }

            VetClinicController controller =
                    new VetClinicController();

            String report = controller.run(arguments);

            System.out.print(report);

        } catch (IOException exception) {
            System.err.printf(
                    "Помилка роботи з файлом: %s%n",
                    exception.getMessage());

        } catch (IllegalArgumentException exception) {
            System.err.printf(
                    "Помилка: %s%n",
                    exception.getMessage());

            System.err.println(CliParser.usage());
        }
    }
}