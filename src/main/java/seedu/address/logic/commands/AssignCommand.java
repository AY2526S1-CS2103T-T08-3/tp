package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Category;

/**
 *  Assigns the department, role or team of an existing employee in the directory.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign_category";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the category of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing category will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGN_CATEGORY + "CATEGORY_TYPE "
            + PREFIX_ASSIGN_CATEGORY_VALUE + "[VALUE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGN_CATEGORY + "Role "
            + PREFIX_ASSIGN_CATEGORY_VALUE + "Manager";
    public static final String MESSAGE_ADD_CATEGORY_SUCCESS = "Added category to Person: %1$s";
    public static final String MESSAGE_DELETE_CATEGORY_SUCCESS = "Removed category from Person: %1$s";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Assign command not implemented yet.";

    private final Index index;
    private final Category categoryObject;

    /**
     * @param index of the person in the filtered person list to update their department/role/team
     * @param category of the person to be updated to
     */
    public AssignCommand(Index index, Category category) {
        requireAllNonNull(index, category);

        this.index = index;
        this.categoryObject = category;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Category> updatedCategories = new HashSet<>(personToEdit.getCategories());
        updatedCategories.add(categoryObject);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                updatedCategories, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the category is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = (!this.categoryObject.category.isEmpty() && !categoryObject.value.isEmpty())
                ? MESSAGE_ADD_CATEGORY_SUCCESS : MESSAGE_DELETE_CATEGORY_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand e = (AssignCommand) other;
        return index.equals(e.index)
                && categoryObject.equals(e.categoryObject);
    }
}
