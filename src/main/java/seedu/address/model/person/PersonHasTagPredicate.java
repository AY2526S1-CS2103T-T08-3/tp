package seedu.address.model.person.predicate;

import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class PersonHasTagPredicate implements Predicate<Person> {
    private final String tagNameLower;

    public PersonHasTagPredicate(String tagName) {
        this.tagNameLower = tagName.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(t -> t.tagName.toLowerCase(Locale.ROOT).equals(tagNameLower));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonHasTagPredicate
                && ((PersonHasTagPredicate) other).tagNameLower.equals(this.tagNameLower));
    }

    @Override
    public String toString() {
        return "tag/" + tagNameLower;
    }
}

