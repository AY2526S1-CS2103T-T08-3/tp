package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Skill in the address book.
 * Constraints mirror Tag-style constraints: alphanumeric only, no spaces, non-empty.
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS =
            "Skills should be alphanumeric and non-empty, with no spaces.";

    // Alphanumeric (Unicode letters/digits), at least 1 char, no spaces or symbols
    private static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    /**
     * Constructs a {@code Skill}.
     *
     * @param value A valid skill string.
     */
    public Skill(String value) {
        requireNonNull(value);
        String trimmed = value.trim();
        if (!isValidSkillName(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = trimmed;
    }

    /**
     * Returns true if a given string is a valid skill name.
     * Alphanumeric only, no spaces/symbols, non-empty.
     */
    public static boolean isValidSkillName(String s) {
        return s != null && s.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Skill && value.equals(((Skill) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

