// базовый интерфейс для реализации классов остальных команд. Содержат методы для получения имения, описания и логики выполнения команды.

package commands;

public interface Command {
    String getName(); // получение имени
    String getDescription(); // получение описания
    void execute(String[] args); // получение логики выполнение команды
}
