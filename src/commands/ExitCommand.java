// Команда для выхода из программы. Данные автоматически не сохраняются

package commands;

/**
 * Команда завершения приложения без сохранения коллекции.
 */
public class ExitCommand implements Command{

    /**
     * @return имя команды
     */
    @Override
    public String getName(){
        return "exit";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription(){
        return "Завершить программу (без сохранения в файл)";
    }

    /**
     * Завершает работу процесса приложения.
     *
     * @param args аргументы команды
     */
    @Override
    public void execute(String[] args){
        if (args != null & args.length >0 && !args[0].isBlank()){ // проверка есть ли аргумент. Данная команда не должна принимать аргументы
            System.out.println("Ошибка : Команда exit не принимает аргументы.");
            System.out.println("Пример: exit");
            return;
        }
        System.out.println("Завершение программы...");
        System.exit(0); // точка выхода из программы
    }
}
