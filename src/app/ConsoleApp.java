// класс для запуска приложения и обработки пользовательского ввода в консоли

package app;

import commands.Command;
import managers.*;
import java.util.Scanner;

public class ConsoleApp {

    private final CommandsManager commandsManager;
    private final InputManager inputManager;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(CommandsManager commandsManager, InputManager inputManager) {
        this.commandsManager = commandsManager;
        this.inputManager = inputManager;
    }

    // основной цикл run() для обработки пользовательского ввода и выполнения команд
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
                System.out.println("Команда" + commandName + " не найдена. Введите help для получения списка доступных команд.");
                continue; // если команда не найдена, выводим сообщение и продолжаем цикл
            }
            try {
                command.execute(args);
            } catch (Exception e) {
                System.out.println("Произошла ошибка при выполнении команды: " + e.getMessage()); // обработка исключений, которые могут возникнуть при выполнении команды
            }
        }
    }
}
