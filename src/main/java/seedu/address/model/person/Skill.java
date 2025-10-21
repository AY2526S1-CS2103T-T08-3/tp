package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Skill in a Person's profile.
 * Guarantees: immutable; valid according to defined constraints.
 */
public class Skill {

    /** Alphanumeric + spaces only; not blank. */
    public static final String MESSAGE_CONSTRAINTS =
            "Skills should only contain alphanumeric characters and spaces, and should not be blank.";

    // One or more alphanumeric words separated by single spaces.
    private static final String VALIDATION_REGEX = "[\\p{Alnum}]+( [\\p{Alnum}]+)*";

    public final String skillName;

    public Skill(String skillName) {
        requireNonNull(skillName);
        String trimmed = skillName.trim();
        if (!isValidSkillName(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.skillName = trimmed;
    }

    public static boolean isValidSkillName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return skillName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Skill
                && skillName.equals(((Skill) other).skillName)); // case-sensitive (matches AB-3 Tag)
    }

    @Override
    public int hashCode() {
        return skillName.hashCode(); // case-sensitive
    }
}

