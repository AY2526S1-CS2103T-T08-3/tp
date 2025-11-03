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
 *   list skills/SKILL_NAME
 */
public class ListCommandParser implements Parser<ListCommand> {

    // Long-form skills prefix to support "skills/java" in addition to "s/java"
    private static final Prefix PREFIX_SKILLS_LONG = new Prefix("skills/");

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Only accept the skill prefix. Unknown prefixes (e.g., c/) will end up in preamble ⇒ error.
         ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SKILL);
 
         // Reject any preamble or stray text (e.g., "c/" or plain words)
         if (!argMultimap.getPreamble().isEmpty()) {
             throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
         }

        // Collect all occurrences (e.g., list s/java s/spring)
        List<String> skills = new ArrayList<>();
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILLS_LONG));
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILL));

        // If no prefixes provided, return unfiltered "list"
         if (skills.isEmpty()) {
             return new ListCommand();
         }

        // Validate that each s/ value is non-empty after trim
         for (String s : skills) {
             if (s == null || s.trim().isEmpty()) {
                 throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
             }
         }

        // Legacy/UG behavior: multiple s/ → join with space (OR semantics/predicate as before)
         String combined = String.join(" ", skills).trim();
         return new ListCommand(new PersonHasTagPredicate(combined));
    }
}

