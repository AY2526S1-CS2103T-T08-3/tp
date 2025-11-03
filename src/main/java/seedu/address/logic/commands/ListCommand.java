package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Throws an error if a requested skill does not exist.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows persons, optionally filtered by skills.\n"
            + "Parameters: [s/SKILL]...\n"
            + "Examples:\n"
            + "  list\n"
            + "  list s/java\n"
            + "  list s/java s/python";

    private final PersonHasTagPredicate predicate;
    private final List<String> skills;

    /** Creates a list command that shows all persons. */
    public ListCommand() {
        this.predicate = null;
        this.skills = List.of();
    }

    /** Creates a list command filtered by skill(s). */
    public ListCommand(PersonHasTagPredicate predicate) {
        this.predicate = requireNonNull(predicate);
        // Extract raw skill words from predicate string for validation
        this.skills = List.of(predicate.toString().split("\\s+"));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Case 1: No skills specified → show all persons
        if (skills.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(
                    Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    model.getFilteredPersonList().size()
            ));
        }

        // Case 2: Skills provided → validate that they exist
        Set<String> existingSkills = model.getAddressBook().getPersonList().stream()
                .map(Person::getSkills)
                .flatMap(Set::stream)
                .map(Skill::toString)
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(HashSet::new));

        List<String> missing = skills.stream()
                .map(s -> s.toLowerCase(Locale.ROOT))
                .filter(s -> !existingSkills.contains(s))
                .collect(Collectors.toList());

        if (!missing.isEmpty()) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_SKILL_NOT_FOUND,
                    String.join(", ", missing)
            ));
        }

        // Case 3: All requested skills exist → apply filter
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return String.valueOf(predicate).equals(String.valueOf(otherCommand.predicate))
                && skills.equals(otherCommand.skills);
    }
}
