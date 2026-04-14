/**
 * Класс для запуска приложения и обработки пользовательского ввода в консоли.
 */

package app;

import commands.Command;
import managers.*;
import java.util.Scanner;

public class ConsoleApp {

    /**
     * Менеджер команд для выполнения пользовательских команд.
     */

    private final CommandsManager commandsManager;

    /**
     * Менеджер ввода для обработки пользовательского ввода.
     */

    private final InputManager inputManager;

    /**
     * Сканер для чтения пользовательского ввода из консоли.
     */

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор класса ConsoleApp.
     *
     * @param commandsManager Менеджер команд.
     * @param inputManager Менеджер ввода.
     */

    public ConsoleApp(CommandsManager commandsManager, InputManager inputManager) {
        this.commandsManager = commandsManager;
        this.inputManager = inputManager;
    }

    /**
     * Основной цикл приложения для обработки пользовательского ввода и выполнения команд.
     */

    public void run(){
        System.out.println("Приложение запущенно. Введите help для получения списка доступнхы команд");
        while(true){
            System.out.println(">");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) { // проверка на конец ввода (например, при закрытии потока ввода)
                System.out.println("Ввод закончился");
                break;
            }
            // Разделение команды и аргументов
            input = input.trim();
            if (input.isEmpty()){
                continue; // если пользователь ввёл только пробелы, пропускаем итерацию цикла и ждем следующего ввода
            }
            String[] parts = input.trim().split("\\s+", 2); // разделение на две части: имя команды и остальная строка с аргументами
            String commandName = parts[0]; // первая часть - это имя команды
            String[] args = parts.length > 1 ? parts[1].split("\\s+") : new String[0]; // разделение аргументов по пробелу

            Command command = commandsManager.getCommandByName(commandName);
            if (command == null){
                System.err.println("Команда " + commandName + " не найдена. Введите help для получения списка доступных команд.");
                continue; // если команда не найдена, выводим сообщение и продолжаем цикл
            }
            try {
                command.execute(args);
            } catch (Exception e) {
                System.err.println("Произошла ошибка при выполнении команды: " + e.getMessage()); // обработка исключений, которые могут возникнуть при выполнении команды
            }
        }
    }
}
