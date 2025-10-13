package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Category;

public class AssignCommandParserTest {

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgs_returnsAssignCommand() throws Exception {
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        AssignCommand expectedCommand = new AssignCommand(
                Index.fromOneBased(1), new Category("Role", "Manager"));

        AssignCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingCategoryPrefix_usesEmptyCategory() throws Exception {
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        AssignCommand expectedCommand = new AssignCommand(
                Index.fromOneBased(1), new Category("", "Manager"));

        AssignCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingValuePrefix_usesEmptyValue() throws Exception {
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + "Role";
        AssignCommand expectedCommand = new AssignCommand(
                Index.fromOneBased(1), new Category("Role", ""));

        AssignCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingBothPrefixes_usesEmptyCategoryAndValue() throws Exception {
        String userInput = "1";
        AssignCommand expectedCommand = new AssignCommand(
                Index.fromOneBased(1), new Category("", ""));

        AssignCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
