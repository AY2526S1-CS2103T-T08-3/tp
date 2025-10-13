package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VALUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VALUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Category;
import seedu.address.testutil.PersonBuilder;

public class AssignCommandTest {
    private static final String ASSIGN_CATEGORY_STUB = "Role";
    private static final String ASSIGN_VALUE_STUB = "Manager";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB)));
    }

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(Index.fromZeroBased(1), null));
    }

    @Test
    public void execute_addCategoryUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson =
                new PersonBuilder(firstPerson).withCategories(Arrays.asList(
                        new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB))).build();

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB));
        String expectedMessage = String.format(AssignCommand.MESSAGE_ADD_CATEGORY_SUCCESS, "Alice Pauline; "
                + "Phone: 94351253; "
                + "Email: alice@example.com; "
                + "Tags: ; " // Refactor
                + "Categories: [category='Role', value='Manager'][friends]");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withCategories(Arrays.asList(new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB))).build();

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB));

        String expectedMessage = String.format(AssignCommand.MESSAGE_ADD_CATEGORY_SUCCESS, "Alice Pauline; "
                + "Phone: 94351253; "
                + "Email: alice@example.com; "
                + "Tags: ; " // Refactor
                + "Categories: [category='Role', value='Manager'][friends]");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex,
                new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB));

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignCommand assignCommand = new AssignCommand(outOfBoundIndex,
                new Category(ASSIGN_CATEGORY_STUB, ASSIGN_VALUE_STUB));

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY));

        // same values -> returns true
        AssignCommand commandWithSameValues = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_SECOND_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_BOB, VALID_VALUE_BOB))));
    }

}
