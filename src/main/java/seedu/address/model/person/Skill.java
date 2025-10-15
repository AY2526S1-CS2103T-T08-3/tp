package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Skill in a Person's profile.
 * Guarantees: immutable; name is non-null and non-empty after trimming.
 */
public class Skill {

    public final String skillName;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skillName a non-null, non-empty string after trimming
     */
    public Skill(String skillName) {
        requireNonNull(skillName);
        this.skillName = skillName.trim();
    }

    /**
     * Returns true if a given string is a valid skill name.
     */
    public static boolean isValidSkillName(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return skillName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Skill
                && skillName.equalsIgnoreCase(((Skill) other).skillName));
    }

    @Override
    public int hashCode() {
        return skillName.toLowerCase().hashCode();
    }
}

