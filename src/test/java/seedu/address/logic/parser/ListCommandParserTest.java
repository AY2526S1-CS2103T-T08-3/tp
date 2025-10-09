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
    public void parse_tagLong_returnsFiltered() throws Exception {
        ListCommand expected = new ListCommand(new PersonHasTagPredicate("friends"));
        assertEquals(expected, parser.parse(" tag/friends "));
    }

    @Test
    public void parse_tagShort_returnsFiltered() throws Exception {
        ListCommand expected = new ListCommand(new PersonHasTagPredicate("friends"));
        assertEquals(expected, parser.parse(" t/friends "));
    }
}
