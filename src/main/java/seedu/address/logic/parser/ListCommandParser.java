package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
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
 *   list c/CATEGORY_NAME
 *   list s/SKILL_NAME c/CATEGORY_NAME
 *   list skills/SKILL_NAME c/CATEGORY_NAME
 *
 * Legacy behaviour (for existing tests):
 *   If only skills are provided (no categories), returns
 *   {@code new ListCommand(new PersonHasTagPredicate(skillString))}.
 */
public class ListCommandParser implements Parser<ListCommand> {

    // Long-form skills prefix to support "skills/java" in addition to "s/java"
    private static final Prefix PREFIX_SKILLS_LONG = new Prefix("skills/");

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize user input for both skill prefixes and the category prefix.
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SKILLS_LONG, PREFIX_SKILL, PREFIX_CATEGORY);

        // Collect all occurrences (e.g., list s/java s/spring skills/backend)
        List<String> skills = new ArrayList<>();
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILLS_LONG));
        skills.addAll(argMultimap.getAllValues(PREFIX_SKILL));

        // Collect categories
        List<String> categories = argMultimap.getAllValues(PREFIX_CATEGORY);

        // If no prefixes provided, return unfiltered "list" (AB3-compatible)
        if (skills.isEmpty() && categories.isEmpty()) {
            return new ListCommand();
        }

        // Legacy test compatibility: only skills → use PersonHasTagPredicate constructor
        if (!skills.isEmpty() && categories.isEmpty()) {
            // If multiple skills were provided, join them with a space (legacy find-style OR semantics).
            String combined = String.join(" ", skills).trim();
            return new ListCommand(new PersonHasTagPredicate(combined));
        }

        // New path: any presence of categories (or mixed usage) → multi-filter command
        return ListCommand.filtered(skills, categories);
    }
}

