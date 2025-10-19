package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ConfirmCancelCommand;
import seedu.address.logic.commands.ConfirmDeleteCommand;

public class ConfirmationParserTest {

    private final ConfirmationParser parser = new ConfirmationParser();

    @Test
    public void parseCommand_confirm() throws Exception {
        ConfirmDeleteCommand command = (ConfirmDeleteCommand) parser.parseCommand(ConfirmDeleteCommand.COMMAND_WORD);
        assertEquals(new ConfirmDeleteCommand(), command);
    }

    @Test
    public void parseCommand_cancel() throws Exception {
        assertTrue(parser.parseCommand("") instanceof ConfirmCancelCommand);
        assertTrue(parser.parseCommand("wq3eqfaew") instanceof ConfirmCancelCommand);
    }
}
