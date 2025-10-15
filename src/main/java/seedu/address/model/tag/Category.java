package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Employee's category(Role/Department/Team) in the directory.
 * Guarantees: immutable;
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS = "Category/Value should be alphanumeric and spaces";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static final String CATEGORY_CONSTRAINTS = "Category must be one of Team/Role/Department";

    public enum CategoryType {
        DEPARTMENT,
        TEAM,
        ROLE;
    }

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
     * Returns true if a given string is a valid category/value.
     */
    public static boolean isValidData(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given category is in CategoryType.
     */
    public static boolean isValidCategory(String test) {
        for (CategoryType category : CategoryType.values()) {
            if (category.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
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
