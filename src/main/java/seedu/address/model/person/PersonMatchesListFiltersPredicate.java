package seedu.address.model.person;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Predicate that checks whether a {@link Person} matches the provided
 * skill and/or category keyword lists.
 *
 * <p>Matching rules:
 * <ul>
 *   <li>Case-insensitive</li>
 *   <li>Partial substring match (e.g. "jav" matches "Java")</li>
 *   <li>AND semantics across dimensions:
 *       if both skills and categories are provided, the person must match
 *       at least one skill AND at least one category.</li>
 *   <li>OR semantics within a dimension:
 *       if multiple skill keywords are provided, matching any one is sufficient
 *       (same for categories).</li>
 * </ul>
 *
 * <p>If a dimension's list is empty, that dimension is treated as "no filter".</p>
 */
public class PersonMatchesListFiltersPredicate implements Predicate<Person> {

    private final List<String> skillNeedles; // already lower-cased
    private final List<String> categoryNeedles; // already lower-cased

    /**
     * @param skillKeywords list of skill keywords (may be empty)
     * @param categoryKeywords list of category keywords (may be empty)
     */
    public PersonMatchesListFiltersPredicate(List<String> skillKeywords, List<String> categoryKeywords) {
        this.skillNeedles = toLower(skillKeywords);
        this.categoryNeedles = toLower(categoryKeywords);
    }

    @Override
    public boolean test(Person person) {
        boolean skillOk = skillNeedles.isEmpty()
                || collectionToLowerStrings(person.getSkills())
                    .stream().anyMatch(val -> containsAny(val, skillNeedles));

        boolean categoryOk = categoryNeedles.isEmpty()
                || collectionToLowerStrings(person.getCategories())
                    .stream().anyMatch(val -> containsAny(val, categoryNeedles));

        return skillOk && categoryOk;
    }

    // --- Helpers ---

    private static boolean containsAny(String haystackLower, List<String> needlesLower) {
        for (String n : needlesLower) {
            if (haystackLower.contains(n)) {
                return true;
            }
        }
        return false;
    }

    /** Convert a collection of domain objects (e.g., Skill/Category) into lower-cased strings. */
    private static List<String> collectionToLowerStrings(Collection<?> values) {
        if (values == null) {
            return List.of();
        }
        return values.stream()
                .map(o -> o == null ? "" : o.toString())
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
    }

    private static List<String> toLower(List<String> xs) {
        if (xs == null) {
            return List.of();
        }
        return xs.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
    }

    // --- Equality & debug ---

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesListFiltersPredicate)) {
            return false;
        }
        PersonMatchesListFiltersPredicate o = (PersonMatchesListFiltersPredicate) other;
        return Objects.equals(skillNeedles, o.skillNeedles)
                && Objects.equals(categoryNeedles, o.categoryNeedles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillNeedles, categoryNeedles);
    }

    @Override
    public String toString() {
        return "PersonMatchesListFiltersPredicate{skills=" + skillNeedles
                + ", categories=" + categoryNeedles + "}";
    }
}

