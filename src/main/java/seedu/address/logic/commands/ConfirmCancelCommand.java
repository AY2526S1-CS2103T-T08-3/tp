package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Cancels the deletion of a person previously marked for deletion from the address book.
 */
public class ConfirmCancelCommand extends Command {

    public static final String MESSAGE_DELETE_PERSON_CANCELLED = "Employee deletion cancelled: %1$s";

    public ConfirmCancelCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = model.getPersonToDelete();
        model.setPersonToDelete(null);
        String feedbackString = String.format(MESSAGE_DELETE_PERSON_CANCELLED, Messages.formatShort(personToDelete));
        return new CommandResult(feedbackString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConfirmCancelCommand)) {
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
