package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.predicate.PersonHasTagPredicate;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsVanilla() throws Exception {
        assertEquals(new ListCommand(), parser.parse(""));
        assertEquals(new ListCommand(), parser.parse("   "));
    }

    @Test
    public void parse_skillsLong_returnsFiltered() throws Exception {
        ListCommand expected = new ListCommand(new PersonHasTagPredicate("java"));
        assertEquals(expected, parser.parse(" skills/java "));
    }

    @Test
    public void parse_skillsShort_returnsFiltered() throws Exception {
        ListCommand expected = new ListCommand(new PersonHasTagPredicate("java"));
        assertEquals(expected, parser.parse(" s/java "));
    }
}
