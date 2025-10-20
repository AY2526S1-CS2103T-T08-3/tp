package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
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
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update_employee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT] "
            + "[" + PREFIX_TEAM + "TEAM] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMAIL + "johndoe@example.com"
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "No fields specified to update. At least one field"
            + "(e.g., n/NAME, d/DEPARTMENT, s/SKILLS) must be included.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final UpdatePersonDescriptor UpdatePersonDescriptor;

    /**
     * @param index index of the person in the filtered person list to edit
     * @param UpdatePersonDescriptor details to edit the person with
     */
    public UpdateCommand(Index index, UpdatePersonDescriptor UpdatePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(UpdatePersonDescriptor);

        this.index = index;
        this.UpdatePersonDescriptor = new UpdatePersonDescriptor(UpdatePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INDEX_NOT_FOUND, index.getOneBased()));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, UpdatePersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code UpdatePersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, UpdatePersonDescriptor UpdatePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = UpdatePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = UpdatePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = UpdatePersonDescriptor.getEmail().orElse(personToEdit.getEmail());

        Set<Category> updatedCategories = UpdatePersonDescriptor.getCategories().orElse(personToEdit.getCategories());
        Set<Skill> updatedSkills = UpdatePersonDescriptor.getSkills().orElse(personToEdit.getSkills());

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedCategories, updatedSkills);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return index.equals(otherUpdateCommand.index)
                && UpdatePersonDescriptor.equals(otherUpdateCommand.UpdatePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("UpdatePersonDescriptor", UpdatePersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdatePersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Category> categories;
        private Set<Skill> skills;

        public UpdatePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} is used internally.
         */
        public UpdatePersonDescriptor(UpdatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCategories(toCopy.categories);
            setSkills(toCopy.skills);
        }

        /**
         * Returns true if at least one field is edited.
         */
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

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UpdatePersonDescriptor)) {
                return false;
            }

            UpdatePersonDescriptor otherUpdatePersonDescriptor = (UpdatePersonDescriptor) other;
            return Objects.equals(name, otherUpdatePersonDescriptor.name)
                    && Objects.equals(phone, otherUpdatePersonDescriptor.phone)
                    && Objects.equals(email, otherUpdatePersonDescriptor.email)
                    && Objects.equals(categories, otherUpdatePersonDescriptor.categories)
                    && Objects.equals(skills, otherUpdatePersonDescriptor.skills);
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

