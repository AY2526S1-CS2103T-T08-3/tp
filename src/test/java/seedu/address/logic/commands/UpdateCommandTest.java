package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // --- Successes ---

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person updatedPerson = new PersonBuilder().build();
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(updatedPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(updatedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person lastPerson = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
        Person updatedPerson = new PersonBuilder(lastPerson).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(Index.fromOneBased(model.getFilteredPersonList().size()),
                descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(updatedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updateMultipleSkillsAndCategories_success() throws Exception {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Category> newCategories = new HashSet<>();
        newCategories.add(new Category("Department", "Finance"));
        newCategories.add(new Category("Role", "HR"));

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withCategories(newCategories)
                .withSkills("Java", "Python").build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        Person updatedPerson = new PersonBuilder(personToUpdate).withCategories(Arrays.asList(
                new Category("Department", "Finance"), new Category("Role", "HR")))
                .withSkills("Java", "Python").build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertEquals(String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson)),
                updateCommand.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
    }

    // --- Duplicate constraint checks ---

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(firstPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateEmail_failure() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withEmail(secondPerson.getEmail().value).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    public void execute_duplicatePhone_failure() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withPhone(secondPerson.getPhone().value).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PHONE);
    }

    // --- Invalid index ---

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_BOB).build());
        assertCommandFailure(updateCommand, model, String.format(Messages.MESSAGE_INDEX_NOT_FOUND,
                outOfBoundIndex.getOneBased()));
    }

    // --- Descriptor logic/equals/hashcode ---

    @Test
    public void updatePersonDescriptor_equalsAndHashCode() {
        UpdatePersonDescriptor descriptor1 = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        UpdatePersonDescriptor descriptor2 = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        UpdatePersonDescriptor descriptor3 = new UpdatePersonDescriptorBuilder().withName("Different Name").build();

        assertTrue(descriptor1.equals(descriptor2));
        assertEquals(descriptor1.hashCode(), descriptor2.hashCode());
        assertFalse(descriptor1.equals(descriptor3));
        assertFalse(descriptor1.equals(null));
        assertFalse(descriptor1.equals("string"));
        assertTrue(descriptor1.equals(descriptor1));
    }

    @Test
    public void updatePersonDescriptor_emptyDescriptor_isAnyFieldUpdatedFalse() {
        assertFalse(new UpdatePersonDescriptor().isAnyFieldUpdated());
    }

    @Test
    public void updatePersonDescriptor_setSkills_defensiveCopy() {
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("Java"));
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setSkills(skills);
        skills.add(new Skill("Python"));
        assertEquals(1, descriptor.getSkills().get().size());
    }

    @Test
    public void updatePersonDescriptor_getSkills_unmodifiable() {
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withSkills("Java").build();
        Set<Skill> skills = descriptor.getSkills().get();
        assertThrows(UnsupportedOperationException.class, () -> skills.add(new Skill("Python")));
    }
}
