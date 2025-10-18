package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Represents a Skill in the address book.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS =
            "Skills names should be alphanumeric and should not be blank";

    public final String skillName;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skillName A valid skill name.
     */
    public Skill(String skillName) {
        requireNonNull(skillName);
        AppUtil.checkArgument(isValidSkillName(skillName), MESSAGE_CONSTRAINTS);
        this.skillName = skillName;
    }

    /**
     * Returns true if a given string is a valid skill name.
     *
     * @param test The string to test.
     * @return true if the string is a valid skill name, false otherwise.
     */
    public static boolean isValidSkillName(String test) {
        return test.matches("[\\p{Alnum}]+");
    }

    @Override
    public String toString() {
        return skillName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Skill
                && skillName.equals(((Skill) other).skillName));
    }

    @Override
    public int hashCode() {
        return skillName.hashCode();
    }
}

