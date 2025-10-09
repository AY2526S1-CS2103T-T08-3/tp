package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's Department
 */
public class Department {

    public static final String MESSAGE_CONSTRAINTS = "Department can take any values, and it should not be blank";

    /*
     * The first character of the department must not be a whitespace,
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a Department.
     *
     * @param dept A valid department.
     */
    public Department(String dept) {
        requireNonNull(dept);
        checkArgument(isValidDept(dept), MESSAGE_CONSTRAINTS);
        value = dept;
    }

    /**
     * Returns true if a given string is a valid department.
     */
    public static boolean isValidDept(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }
}
