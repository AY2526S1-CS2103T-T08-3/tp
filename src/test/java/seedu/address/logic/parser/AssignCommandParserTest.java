package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                Index.fromOneBased(1),
                new Category("Role", "Manager")
        );

        AssignCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String userInput = PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(e.getMessage().contains("Invalid command format"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(e.getMessage().contains("Invalid command format"));
    }

    @Test
    public void parse_invalidCategoryValue_throwsParseException() {
        // Here "" is invalid (empty)
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(Category.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void parse_invalidCategory_throwsParseException() {
        // Category "NotACategory" is invalid
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + "NotACategory " + PREFIX_ASSIGN_CATEGORY_VALUE + "SomeValue";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(Category.CATEGORY_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void parse_emptyCategory_throwsParseException() {
        // Empty category is invalid
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + " " + PREFIX_ASSIGN_CATEGORY_VALUE + "Value";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(Category.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void parse_emptyValue_throwsParseException() {
        // Empty value is invalid
        String userInput = "1 " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + " ";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(Category.MESSAGE_CONSTRAINTS, e.getMessage());
    }
}
