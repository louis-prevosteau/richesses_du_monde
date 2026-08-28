package core.commands;

import java.util.Stack;

public class CommandInvoker {

    private final Stack<ICommand> history = new Stack<>();

    public Stack<ICommand> getHistory() {
        return history;
    }

    public void executeCommand(ICommand command) {
        if (!command.canExecute())
            throw new IllegalStateException("Commande non exécutable");
        command.execute();
        history.push(command);
    }

    public void showHistory() {
        for (ICommand command : history) System.out.println("- " + command.getDescription());
    }
}
