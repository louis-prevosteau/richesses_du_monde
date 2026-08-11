package core.commands;

import java.util.Stack;

public class CommandInvoker {

    private final Stack<ICommand> history = new Stack<>();

    public Stack<ICommand> getHistory() {
        return history;
    }

    public void executeCommand(ICommand command) {}

    public void showHistory() {};

    public void clear() {}
}
