package seedu.address.model.tag;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Employee's category(Role/Department/Team) in the directory.
 * Guarantees: immutable; category and value is valid as declared in {@link #isValidData(String)}
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String category;
    public final String value;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category.
     * @param value A valid category value.
     */
    public Category(String category, String value) {
        requireNonNull(category, value);
        checkArgument(isValidData(category), MESSAGE_CONSTRAINTS);
        checkArgument(isValidData(value), MESSAGE_CONSTRAINTS);

        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid category or value.
     */
    public static boolean isValidData(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("[category='%s', value='%s']", category, value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Category)) {
            return false;
        }

        Category otherCategory = (Category) other;
        return category.equals(otherCategory.category) && value.equals(otherCategory.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, value);
    }
}
