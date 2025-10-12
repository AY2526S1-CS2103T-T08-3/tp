package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's skills
 */
public class Skills {

    public static final String MESSAGE_CONSTRAINTS = "Skills can take any value";

    /*
     * The first character of the skills must not be a whitespace,
     */
    public static final String VALIDATION_REGEX = "(^$)|[^\\s].*";

    public final String value;

    /**
     * Constructs a Skills.
     *
     * @param skills A valid list of skills.
     */
    public Skills(String skills) {
        requireNonNull(skills);
        checkArgument(isValidSkills(skills), MESSAGE_CONSTRAINTS);
        value = skills;
    }

    /**
     * Returns true if a given string is a valid skills.
     */
    public static boolean isValidSkills(String test) {
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
