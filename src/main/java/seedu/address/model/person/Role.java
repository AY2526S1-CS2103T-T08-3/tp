package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's Role
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Role can take any values, and it should not be blank";

    /*
     * The first character of the role must not be a whitespace,
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a Role.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
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
