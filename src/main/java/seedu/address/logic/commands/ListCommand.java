package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;

/**
 * Lists persons in the address book.
 * <p>
 * Behaviour:
 * <ul>
 *   <li><b>Unfiltered</b>: {@code list} shows all persons (AB3-compatible message).</li>
 *   <li><b>Legacy filtered</b>: {@code new ListCommand(Predicate<Person>)} uses a predicate provided by tests.</li>
 *   <li><b>New filtered</b>: {@code list s/... c/...} filters by skills and categories.</li>
 * </ul>
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    // Keep AB3 stock message so existing tests continue to pass for unfiltered/legacy usage.
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_FILTERED_SUCCESS = "Listed matching persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows persons, optionally filtered by skills and/or "
            + "category.\n"
            + "Parameters: [s/SKILL]... [c/CATEGORY]...\n"
            + "Examples:\n"
            + "  list\n"
            + "  list s/java\n"
            + "  list c/Engineering\n"
            + "  list s/java s/spring c/Platform";

    // --- State ---
    private final List<String> skills; // for new s/ filtering
    private final List<String> categories; // for new c/ filtering
    private final boolean unfiltered; // true when plain "list"
    private final Predicate<Person> legacyPredicate; // for AB3-style tests & backward compatibility

    // --- Constructors must appear before factory methods (Checkstyle: DeclarationOrder) ---

    /** AB3-compatible: plain list (no filter). */
    public ListCommand() {
        this.skills = List.of();
        this.categories = List.of();
        this.unfiltered = true;
        this.legacyPredicate = null;
    }

    /** AB3-compatible: filtered list with a caller-provided predicate (e.g., PersonHasTagPredicate in tests). */
    public ListCommand(Predicate<Person> predicate) {
        this.skills = List.of();
        this.categories = List.of();
        this.unfiltered = false;
        this.legacyPredicate = Objects.requireNonNull(predicate);
    }

    /** Internal main constructor for new s/ and c/ filtering path. */
    private ListCommand(List<String> skills, List<String> categories, boolean unfiltered) {
        this.skills = skills == null ? List.of() : List.copyOf(skills);
        this.categories = categories == null ? List.of() : List.copyOf(categories);
        this.unfiltered = unfiltered;
        this.legacyPredicate = null;
    }

    // --- Factory methods for the new path ---

    /** Factory for unfiltered behaviour (backwards compatible). */
    public static ListCommand unfiltered() {
        return new ListCommand(List.of(), List.of(), true);
    }

    /** Factory for filtered behaviour (new s/ and c/ flags). */
    public static ListCommand filtered(List<String> skills, List<String> categories) {
        return new ListCommand(skills, categories, false);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (unfiltered) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        if (legacyPredicate != null) {
            // Maintain original semantics & message for tests using the legacy constructor.
            model.updateFilteredPersonList(legacyPredicate);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        // New s/ and c/ filtering path
        var predicate = new PersonMatchesListFiltersPredicate(skills, categories);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_FILTERED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand o = (ListCommand) other;
        return this.unfiltered == o.unfiltered
                && Objects.equals(this.skills, o.skills)
                && Objects.equals(this.categories, o.categories)
                && Objects.equals(this.legacyPredicate, o.legacyPredicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unfiltered, skills, categories, legacyPredicate);
    }

    @Override
    public String toString() {
        return "ListCommand{unfiltered=" + unfiltered
                + ", skills=" + skills
                + ", categories=" + categories
                + ", legacyPredicate=" + legacyPredicate + "}";
    }
}

