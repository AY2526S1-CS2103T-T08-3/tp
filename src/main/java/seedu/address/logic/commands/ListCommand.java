package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;
import seedu.address.model.person.predicate.PersonHasTagPredicate;

/**
 * Lists persons in the address book, optionally filtered by skills.
 * If constructed with an explicit skills list (via parser), it will validate that each skill exists.
 * If constructed with only a predicate (e.g., in tests), it will skip validation and just filter.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    /** Message expected by existing tests. */
    public static final String MESSAGE_SUCCESS = "Listed all persons";

    /** Usage text with c/ removed. */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows persons, optionally filtered by skills.\n"
            + "Parameters: [s/SKILL]...\n"
            + "Examples:\n"
            + "  list\n"
            + "  list s/java\n"
            + "  list s/java s/python";

    /** When null, we skip existence validation (maintains legacy/test behaviour). */
    private final List<String> requestedSkillsOrNull;

    /** Predicate used to filter when any skills are supplied. May be null for the plain 'list' command. */
    private final PersonHasTagPredicate predicate;

    /** Creates a list command that shows all persons. */
    public ListCommand() {
        this.predicate = null;
        this.requestedSkillsOrNull = new ArrayList<>(); // empty => show all
    }

    /**
     * Creates a list command filtered by skill(s) without validation.
     * Used by existing tests that call {@code new ListCommand(predicate)}.
     */
    public ListCommand(PersonHasTagPredicate predicate) {
        this.predicate = requireNonNull(predicate);
        this.requestedSkillsOrNull = null; // null => skip validation, just filter
    }

    /**
     * Creates a list command filtered by skill(s) with validation.
     * Used by the parser to enforce "non-existing skill" errors.
     */
    public ListCommand(PersonHasTagPredicate predicate, List<String> requestedSkills) {
        this.predicate = requireNonNull(predicate);
        this.requestedSkillsOrNull = new ArrayList<>(requireNonNull(requestedSkills));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // No predicate means plain "list" -> show all persons.
        if (predicate == null) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        // If requestedSkillsOrNull is null, we are in legacy/test path: skip validation, just filter.
        if (requestedSkillsOrNull == null) {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        // Parser path with validation:
        if (requestedSkillsOrNull.isEmpty()) {
            // Shouldn't normally happen (parser passes skills when a predicate is provided),
            // but handle gracefully: show all.
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        // Validate that each requested skill exists at least once in the address book.
        Set<String> existingSkillsLower = model.getAddressBook().getPersonList().stream()
                .map(Person::getSkills)
                .flatMap(Set::stream)
                .map(Skill::toString) // or s -> s.value if you have a value field
                .map(s -> s.toLowerCase(Locale.ROOT).trim())
                .collect(Collectors.toCollection(HashSet::new));

        List<String> missing = requestedSkillsOrNull.stream()
                .map(s -> s.toLowerCase(Locale.ROOT).trim())
                .filter(s -> !existingSkillsLower.contains(s))
                .collect(Collectors.toList());

        if (!missing.isEmpty()) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_SKILL_NOT_FOUND,
                    String.join(", ", missing)
            ));
        }

        // All requested skills exist -> apply filter.
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand that = (ListCommand) other;
        // Compare predicates by string form to avoid depending on predicate internals
        boolean samePredicate = String.valueOf(this.predicate).equals(String.valueOf(that.predicate));
        // Handle null-vs-null and list equality
        boolean sameSkills;
        if (this.requestedSkillsOrNull == null && that.requestedSkillsOrNull == null) {
            sameSkills = true;
        } else if (this.requestedSkillsOrNull == null || that.requestedSkillsOrNull == null) {
            sameSkills = false;
        } else {
            sameSkills = this.requestedSkillsOrNull.equals(that.requestedSkillsOrNull);
        }
        return samePredicate && sameSkills;
    }
}
