package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Confirms the deletion of a person previously marked for deletion from the address book.
 */
public class ConfirmDeleteCommand extends Command {

    public static final String COMMAND_WORD = "yes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Confirms the deletion of the selected person.\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Employee deleted: %1$s";

    public ConfirmDeleteCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = model.getPersonToDelete();
        model.deletePerson(personToDelete);
        model.setPersonToDelete(null);
        String feedbackString = String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.formatShort(personToDelete));
        return new CommandResult(feedbackString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConfirmDeleteCommand)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
