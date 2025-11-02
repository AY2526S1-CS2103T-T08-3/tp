package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Skill in a Person's profile.
 * Guarantees: immutable; valid according to defined constraints.
 */
public class Skill {

    /** Alphanumeric + spaces only; not blank. */
    public static final String MESSAGE_CONSTRAINTS =
            "Skills should only contain alphanumeric characters and spaces,"
                    + "and should not be blank.(maximum 30 characters)";

    /** One or more alphanumeric words separated by single spaces. */
    private static final String VALIDATION_REGEX = "^[\\p{Alnum} ]{1,30}$";

    /** The name of this skill (immutable). */
    public final String skillName;

    /**
     * Constructs a {@code Skill} with the given skill name.
     *
     * @param skillName The name of the skill.
     * @throws IllegalArgumentException If {@code skillName} is invalid.
     */
    public Skill(String skillName) {
        requireNonNull(skillName);
        String trimmed = skillName.trim();
        if (!isValidSkillName(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.skillName = trimmed;
    }

    /**
     * Returns true if the given string is a valid skill name.
     * A valid skill name:
     * <ul>
     *     <li>Is not null</li>
     *     <li>Contains only alphanumeric characters and spaces</li>
     *     <li>Is not blank</li>
     * </ul>
     *
     * @param test String to test.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidSkillName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the skill name as a string.
     *
     * @return The skill name.
     */
    @Override
    public String toString() {
        return skillName;
    }

    /**
     * Returns true if both skills have the same name.
     * Comparison is case-sensitive to match AB-3 behavior for tags.
     *
     * @param other Other object to compare.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Skill
                && skillName.equals(((Skill) other).skillName)); // case-sensitive (matches AB-3 Tag)
    }

    /**
     * Returns the hash code of the skill name.
     * Case-sensitive.
     *
     * @return Hash code of the skill name.
     */
    @Override
    public int hashCode() {
        return skillName.hashCode(); // case-sensitive
    }
}

