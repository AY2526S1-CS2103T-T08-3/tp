package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ASSIGN_CATEGORY + "CATEGORY" + PREFIX_ASSIGN_CATEGORY_VALUE + "VALUE] "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an EditCommand to edit the person at the specified {@code index}.
     *
     * @param index Index of the person to edit.
     * @param editPersonDescriptor Details of the edits to apply.
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);
        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Executes the edit command.
     *
     * @param model The model containing the address book data.
     * @return A {@code CommandResult} describing the outcome.
     * @throws CommandException If the index is invalid or the edit would create a duplicate person.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /** Creates and returns a {@code Person} with edited fields. */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor d) {
        assert personToEdit != null;

        Name updatedName = d.getName().orElse(personToEdit.getName());
        Phone updatedPhone = d.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = d.getEmail().orElse(personToEdit.getEmail());
        Set<Category> updatedCategories = d.getCategories().orElse(personToEdit.getCategories());
        Set<Skill> updatedSkills = d.getSkills().orElse(personToEdit.getSkills());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedCategories, updatedSkills);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditCommand)) {
            return false;
        }
        EditCommand o = (EditCommand) other;
        return index.equals(o.index) && editPersonDescriptor.equals(o.editPersonDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /** Descriptor of editable fields. */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Category> categories;
        private Set<Skill> skills;

        public EditPersonDescriptor() {
        }

        /** Copy constructor with defensive copies. */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            requireNonNull(toCopy);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCategories(toCopy.categories);
            setSkills(toCopy.skills);
        }

        /** Returns true if at least one field is edited. */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, categories, skills);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /** Sets {@code categories}. Defensive copy internally. */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /** Returns unmodifiable set if present. */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        /** Sets {@code skills}. Defensive copy internally. */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /** Returns unmodifiable set if present. */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }
            EditPersonDescriptor o = (EditPersonDescriptor) other;
            return Objects.equals(name, o.name)
                    && Objects.equals(phone, o.phone)
                    && Objects.equals(email, o.email)
                    && Objects.equals(categories, o.categories)
                    && Objects.equals(skills, o.skills);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, phone, email, categories, skills);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("categories", categories)
                    .add("skills", skills)
                    .toString();
        }
    }
}

