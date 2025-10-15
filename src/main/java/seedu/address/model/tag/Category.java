package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Employee's category(Role/Department/Team) in the directory.
 * Guarantees: immutable;
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric and spaces";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    private final String category;
    private final String value;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category.
     * @param value A valid category value.
     */
    public Category(String category, String value) {
        requireNonNull(category);
        requireNonNull(value);

        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
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
