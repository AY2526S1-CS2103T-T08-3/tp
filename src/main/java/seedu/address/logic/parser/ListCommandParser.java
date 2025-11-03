package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicate.PersonHasTagPredicate;

/**
 * Parses input arguments and creates a new {@code ListCommand} object.
 *
 * Supports:
 *   - list
 *   - list s/SKILL_NAME [s/ANOTHER_SKILL ...]
 *   - list skills/SKILL_NAME [skills/ANOTHER_SKILL ...]   (long form kept for backward-compat)
 *
 * Any other prefixes or stray text will cause a parse error.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /** Long-form skills prefix to support "skills/java" in addition to "s/java". */
    private static final Prefix PREFIX_SKILLS_LONG = new Prefix("skills/");

    /** Local copy of the usual format string to avoid dependency on seedu.address.commons.core.Messages. */
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Only accept skill prefixes; unknown prefixes (e.g., c/) will end up in preamble -> error.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SKILL, PREFIX_SKILLS_LONG);

        // Reject any preamble or stray text (e.g., "c/" or plain words)
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // Collect all occurrences: e.g., "list s/java s/spring skills/backend"
        List<String> skills = new ArrayList<>();
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILLS_LONG));
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILL));

        // No prefixes -> plain "list"
        if (skills.isEmpty()) {
            return new ListCommand();
        }

        // Validate non-empty values
        for (String s : skills) {
            if (s == null || s.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        }

        // Join multiple skills with a single space (legacy OR semantics for PersonHasTagPredicate)
        String combined = String.join(" ", skills).trim();
        return new ListCommand(new PersonHasTagPredicate(combined));
    }
}
