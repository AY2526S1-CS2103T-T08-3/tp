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
 *   list
 *   list s/SKILL_NAME
 *   list s/SKILL_NAME s/ANOTHER_SKILL
 */
public class ListCommandParser implements Parser<ListCommand> {

    // Local copy of the usual AB-3 format string to avoid dependency on Messages.
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Only accept the skill prefix. Unknown prefixes (e.g., c/) or stray text will be preamble → error.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SKILL);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // Collect all s/ occurrences (e.g., "list s/java s/spring")
        List<String> skills = new ArrayList<>(argMultimap.getAllValues(PREFIX_SKILL));

        // No prefixes → plain "list"
        if (skills.isEmpty()) {
            return new ListCommand();
        }

        // Validate non-empty values
        for (String s : skills) {
            if (s == null || s.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        }

        // Join multiple skills with a single space (legacy OR semantics in PersonHasTagPredicate).
        String combined = String.join(" ", skills).trim();
        return new ListCommand(new PersonHasTagPredicate(combined));
    }
}
