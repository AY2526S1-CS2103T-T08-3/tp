package seedu.address.model.person.predicate;

import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s skills contain the given skill name.
 * Example: list skills/java
 */


public class PersonHasTagPredicate implements Predicate<Person> {
    private final String skillName;

    public PersonHasTagPredicate(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public boolean test(Person person) {
        return person.getSkills().stream()
                .anyMatch(skill -> skill.skillName.equalsIgnoreCase(skillName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonHasTagPredicate
                && skillName.equalsIgnoreCase(((PersonHasTagPredicate) other).skillName));
    }

    @Override
    public String toString() {
        return "skills/" + skillName;
    }
}

