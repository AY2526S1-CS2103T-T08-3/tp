package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Category;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AssignCommandParser}.
 */
public class AssignCommandParserIntegrationTest {

    private AssignCommandParser parser;
    private Model model;

    @BeforeEach
    public void setUp() {
        parser = new AssignCommandParser();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void parseAndExecute_validInput_success() throws Exception {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson =
                new PersonBuilder(secondPerson).withCategories(Arrays.asList(
                        new Category("Department", "Finance"))).build();

        String input = "2 " + PREFIX_ASSIGN_CATEGORY + "Department " + PREFIX_ASSIGN_CATEGORY_VALUE + "Finance";
        AssignCommand command = parser.parse(input);
        command.execute(model);

        assertEquals(editedPerson,
                model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
    }

    @Test
    public void parse_missingCategoryPrefix_throwsParseException() {
        String input = "3 " + PREFIX_ASSIGN_CATEGORY_VALUE + "Developer";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingValuePrefix_throwsParseException() {
        String input = "4 " + PREFIX_ASSIGN_CATEGORY + "Team";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingBothPrefixes_throwsParseException() {
        String input = "5";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = "x " + PREFIX_ASSIGN_CATEGORY + "Role " + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
