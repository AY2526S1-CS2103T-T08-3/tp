package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Category;

/**
 * Contains integration tests (interaction with the Model) for {@code AssignCommandParser}.
 */
public class AssignCommandParserIntegrationTest {

    private AssignCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AssignCommandParser();
    }

    @Test
    public void parse_validInput_success() throws Exception {
        String input = "2 " + PREFIX_ASSIGN_CATEGORY + "Department " + PREFIX_ASSIGN_CATEGORY_VALUE + "Finance";
        AssignCommand expected = new AssignCommand(Index.fromOneBased(2),
                new Category("Department", "Finance"));
        Command actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_missingCategoryPrefix_emptyCategory() throws Exception {
        String input = "3 " + PREFIX_ASSIGN_CATEGORY_VALUE + "Developer";
        AssignCommand expected = new AssignCommand(Index.fromOneBased(3),
                new Category("", "Developer"));
        Command actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_missingValuePrefix_emptyValue() throws Exception {
        String input = "4 " + PREFIX_ASSIGN_CATEGORY + "Team";
        AssignCommand expected = new AssignCommand(Index.fromOneBased(4),
                new Category("Team", ""));
        Command actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_missingBothPrefixes_emptyCategoryAndValue() throws Exception {
        String input = "5";
        AssignCommand expected = new AssignCommand(Index.fromOneBased(5),
                new Category("", ""));
        AssignCommand result = parser.parse(input);
        assertEquals(expected, result);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = "x " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_extraWhitespace_success() throws Exception {
        String input = " 6   " + PREFIX_ASSIGN_CATEGORY + "  Location  " + PREFIX_ASSIGN_CATEGORY_VALUE + "  HQ ";
        AssignCommand expected = new AssignCommand(Index.fromOneBased(6),
                new Category("Location", "HQ"));
        Command actual = parser.parse(input);
        assertEquals(expected, actual);
    }
}
