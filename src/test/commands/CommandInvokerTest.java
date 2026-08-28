package test.commands;

import core.commands.CommandInvoker;
import core.commands.ICommand;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommandInvokerTest {

    @Test
    @DisplayName("executeCommand() doit exécuter une commande valide")
    void testExecuteCommand() {

        CommandInvoker invoker = new CommandInvoker();

        TestCommand command = new TestCommand(true);

        invoker.executeCommand(command);

        assertTrue(command.executed);
    }

    @Test
    @DisplayName("executeCommand() doit ajouter la commande à l'historique")
    void testExecuteCommandAddsToHistory() {

        CommandInvoker invoker = new CommandInvoker();

        TestCommand command = new TestCommand(true);

        invoker.executeCommand(command);

        assertEquals(
                1,
                invoker.getHistory().size()
        );

        assertSame(
                command,
                invoker.getHistory().peek()
        );
    }

    @Test
    @DisplayName("executeCommand() doit lever une exception si la commande n'est pas exécutable")
    void testExecuteCommandThrowsException() {

        CommandInvoker invoker = new CommandInvoker();

        TestCommand command = new TestCommand(false);

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> invoker.executeCommand(command)
                );

        assertEquals(
                "Commande non exécutable",
                exception.getMessage()
        );

        assertTrue(
                invoker.getHistory().isEmpty()
        );
    }

    @Test
    @DisplayName("getHistory() doit retourner les commandes exécutées")
    void testGetHistory() {

        CommandInvoker invoker = new CommandInvoker();

        TestCommand command1 = new TestCommand(true);
        TestCommand command2 = new TestCommand(true);

        invoker.executeCommand(command1);
        invoker.executeCommand(command2);

        assertEquals(
                2,
                invoker.getHistory().size()
        );

        assertSame(
                command2,
                invoker.getHistory().peek()
        );
    }

    @Test
    @DisplayName("showHistory() doit afficher les descriptions des commandes")
    void testShowHistory() {

        CommandInvoker invoker = new CommandInvoker();

        invoker.executeCommand(
                new TestCommand(true, "Commande 1")
        );

        invoker.executeCommand(
                new TestCommand(true, "Commande 2")
        );

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        try {
            invoker.showHistory();
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(
                result.contains("- Commande 1")
        );

        assertTrue(
                result.contains("- Commande 2")
        );
    }

    private static class TestCommand implements ICommand {

        private final boolean executable;
        private final String description;

        private boolean executed = false;

        TestCommand(boolean executable) {
            this(executable, "Test Command");
        }

        TestCommand(
                boolean executable,
                String description
        ) {
            this.executable = executable;
            this.description = description;
        }

        @Override
        public void execute() {
            executed = true;
        }

        @Override
        public boolean canExecute() {
            return executable;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
