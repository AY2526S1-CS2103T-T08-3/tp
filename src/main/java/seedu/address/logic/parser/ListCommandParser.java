package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicate.PersonHasTagPredicate;

/**
 * Parses input arguments and creates a new ListCommand object.
 * Supports:
 *   list
 *   list skills/SKILL_NAME
 * Also accepts legacy "s/SKILL_NAME" for convenience.
 */
public class ListCommandParser implements Parser<ListCommand> {

    // New prefix for this command to avoid side-effects on existing "t/" usage.
    private static final Prefix PREFIX_SKILLS_LONG = new Prefix("skills/");
    // Optional backward-compat for teams that prefer AB3's "t/":
    private static final Prefix PREFIX_SKILLS_SHORT = new Prefix("s/");

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // Tokenize with both; prefer tag/
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_SKILLS_LONG, PREFIX_SKILLS_SHORT);

        String skill = map.getValue(PREFIX_SKILLS_LONG).orElse(map.getValue(PREFIX_SKILLS_SHORT).orElse("")).trim();

        if (skill.isEmpty()) {
            // vanilla "list"
            return new ListCommand();
        }

        // list tag/NAME
        return new ListCommand(new PersonHasTagPredicate(skill));
    }
}

