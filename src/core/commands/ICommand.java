package core.commands;

public interface ICommand {

    String getDescription();

    void execute();

    boolean canExecute();
}
