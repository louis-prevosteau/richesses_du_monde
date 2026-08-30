package core.commands;

import core.cards.CardDeck;

import java.util.Stack;
import java.util.logging.Logger;

public class CommandInvoker {

    private final Stack<ICommand> history = new Stack<>();
    private static final Logger logger = Logger.getLogger(CommandInvoker.class.getName());

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
        for (ICommand command : history) logger.info("- " + command.getDescription());
    }
}
