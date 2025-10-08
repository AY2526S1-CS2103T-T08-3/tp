package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.predicate.PersonHasTagPredicate; 

class ListCommandParserTest {
    private final ListCommandParser parser = new ListCommandParser();

    @Test
    void parse_noArgs_returnsVanilla() throws Exception {
        assertEquals(new ListCommand(), parser.parse(""));
        assertEquals(new ListCommand(), parser.parse("   "));
    }

    @Test
    void parse_tagLong_returnsFiltered() throws Exception {
        ListCommand cmd = parser.parse(" tag/clients ");
        assertNotEquals(new ListCommand(), cmd);
    }

    @Test
    void parse_tagShort_legacy_returnsFiltered() throws Exception {
        ListCommand cmd = parser.parse(" t/colleagues ");
        assertNotEquals(new ListCommand(), cmd);
    }
}

