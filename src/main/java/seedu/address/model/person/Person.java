package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Category;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final Set<Category> categories = new HashSet<>();
    private final Set<Skill> skills = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email,
            Set<Category> categories, Set<Skill> skills) {
        requireAllNonNull(name, phone, email, categories, skills);
        this.name = name;
        this.phone = phone;
        this.email = email;

        this.categories.addAll(categories);
        this.skills.addAll(skills);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns an immutable skills set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }
    
    /**
     * Returns a human-readable one-line summary of this person,
     * including their categories and skills, suitable for confirmation dialogs.
     */
    public String toSummaryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name.fullName).append(" (");

        // Join categories
        if (!categories.isEmpty()) {
            String categoryList = categories.stream()
                    .map(Category::toString)
                    .collect(java.util.stream.Collectors.joining(", "));
            sb.append("Categories: ").append(categoryList);
        }

        // Join skills
        if (!skills.isEmpty()) {
            if (!categories.isEmpty()) {
                sb.append("; ");
            }
            String skillList = skills.stream()
                    .map(Skill::toString)
                    .collect(java.util.stream.Collectors.joining(", "));
            sb.append("Skills: ").append(skillList);
        }

        sb.append(")");
        return sb.toString();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        boolean isSameName = otherPerson.getName().equals(getName());
        boolean isSameEmail = otherPerson.getEmail().equals(getEmail());
        boolean isSamePhoneNumber = otherPerson.getPhone().equals(getPhone());

        return isSameName && isSameEmail && isSamePhoneNumber;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && categories.equals(otherPerson.categories)
                && skills.equals(otherPerson.skills);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
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

