package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists persons in the address book. If constructed with a predicate, lists only those that match it.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    // Keep AB3 stock text for compatibility with existing tests/UI
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows persons.\n"
            + "Parameters: [tag/TAG_NAME]\n"
            + "Examples: list | list tag/clients";

    private final Optional<Predicate<Person>> predicate;

    /** Vanilla list (no filter). */
    public ListCommand() {
        this.predicate = Optional.empty();
    }

    /** Filtered list with a predicate. */
    public ListCommand(Predicate<Person> predicate) {
        this.predicate = Optional.of(requireNonNull(predicate));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (predicate.isPresent()) {
            model.updateFilteredPersonList(predicate.get());
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListCommand
                && predicate.equals(((ListCommand) other).predicate));
    }
}

