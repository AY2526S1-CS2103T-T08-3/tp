package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicate.PersonHasTagPredicate;

/**
 * Parses input arguments and creates a new ListCommand object.
 * Supports:
 *   list
 *   list tag/TAG_NAME
 * Also accepts legacy "t/TAG_NAME" for convenience, but UG documents "tag/".
 */
public class ListCommandParser implements Parser<ListCommand> {

    // New prefix for this command to avoid side-effects on existing "t/" usage.
    private static final Prefix PREFIX_TAG_LONG = new Prefix("tag/");
    // Optional backward-compat for teams that prefer AB3's "t/":
    private static final Prefix PREFIX_TAG_SHORT = new Prefix("t/");

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // Tokenize with both; prefer tag/
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_TAG_LONG, PREFIX_TAG_SHORT);

        String tag = map.getValue(PREFIX_TAG_LONG).orElse(map.getValue(PREFIX_TAG_SHORT).orElse("")).trim();

        if (tag.isEmpty()) {
            // vanilla "list"
            return new ListCommand();
        }

        // list tag/NAME
        return new ListCommand(new PersonHasTagPredicate(tag));
    }
}

