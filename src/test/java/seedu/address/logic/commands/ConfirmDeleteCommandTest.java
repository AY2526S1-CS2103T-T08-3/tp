package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ConfirmDeleteCommand}.
 */
public class ConfirmDeleteCommandTest {

    @Test
    public void execute_validPersonToDelete_success() throws Exception {
        ModelStub modelStub = new ModelStub();

        CommandResult commandResult = new ConfirmDeleteCommand().execute(modelStub);

        assertEquals(String.format(ConfirmDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                        Messages.formatShort(modelStub.getPersonToDelete())),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ConfirmDeleteCommand confirmDeleteCommand = new ConfirmDeleteCommand();

        // same object -> returns true
        assertTrue(confirmDeleteCommand.equals(confirmDeleteCommand));

        // same types -> returns true
        assertTrue(confirmDeleteCommand.equals(new ConfirmDeleteCommand()));

        // different types -> returns false
        assertFalse(confirmDeleteCommand.equals(new ConfirmCancelCommand()));

        // null -> returns false
        assertFalse(confirmDeleteCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        ConfirmDeleteCommand confirmDeleteCommand = new ConfirmDeleteCommand();
        String expected = ConfirmDeleteCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, confirmDeleteCommand.toString());
    }

    /**
     * A default model stub that has all the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPhoneNumber(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmail(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPersonToDelete(Person target) {
            return;
        }

        @Override
        public Person getPersonToDelete() {
            return new PersonBuilder().build();
        }

        @Override
        public boolean hasPersonToDelete() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            return;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
