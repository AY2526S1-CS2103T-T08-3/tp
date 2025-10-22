package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Category;

/**
 * Lists persons grouped by a given category (role, team, department).
 */
public class ListByCategoryCommand extends Command {

    public static final String COMMAND_WORD = "listbycategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists persons grouped by the specified category.\n"
            + "Parameters: c/CATEGORY\n"
            + "Valid categories: role, team, department\n"
            + "Example: " + COMMAND_WORD + " c/role";

    public static final String MESSAGE_SUCCESS_PREFIX = "Listing by ";
    public static final String CATEGORY_ROLE = "role";
    public static final String CATEGORY_TEAM = "team";
    public static final String CATEGORY_DEPARTMENT = "department";

    private static final String UNSPECIFIED = "(unspecified)";

    private final String category; // lower-case: "role" | "team" | "department"

    public ListByCategoryCommand(String category) {
        this.category = category.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> persons = new ArrayList<>(model.getFilteredPersonList());

        // Count by group value; keep output stable with a case-insensitive, sorted map
        Map<String, Integer> groups = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Person p : persons) {
            String key = extractGroupKey(p, category);
            groups.put(key, groups.getOrDefault(key, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS_PREFIX).append(category).append(":\n");
        for (Map.Entry<String, Integer> e : groups.entrySet()) {
            sb.append("- ").append(e.getKey()).append(": ").append(e.getValue()).append(" member(s)\n");
        }

        return new CommandResult(sb.toString());
    }

    /**
     * Extracts the grouping key from a Person's categories.
     * We look for a Category whose getCategory() equals one of "Role" | "Team" | "Department"
     * (case-insensitive), and use its getValue(). If none, returns "(unspecified)".
     */
    private String extractGroupKey(Person person, String categoryKeyLower) {
        if (person == null) {
            return UNSPECIFIED;
        }

        final String label = toCanonicalLabel(categoryKeyLower); // "Role"/"Team"/"Department"
        if (label == null) {
            return UNSPECIFIED;
        }

        Collection<Category> cats = person.getCategories();
        if (cats == null || cats.isEmpty()) {
            return UNSPECIFIED;
        }

        for (Category c : cats) {
            if (c == null) {
                continue;
            }
            String catName = c.getCategory();
            if (catName != null && catName.equalsIgnoreCase(label)) {
                String value = c.getValue();
                return normalize(value);
            }
        }

        return UNSPECIFIED;
    }

    private String toCanonicalLabel(String keyLower) {
        switch (keyLower) {
        case CATEGORY_ROLE:
            return "Role";
        case CATEGORY_TEAM:
            return "Team";
        case CATEGORY_DEPARTMENT:
            return "Department";
        default:
            return null;
        }
    }

    private String normalize(String s) {
        if (s == null) {
            return UNSPECIFIED;
        }
        String t = s.trim();
        return t.isEmpty() ? UNSPECIFIED : t;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListByCategoryCommand
                && this.category.equals(((ListByCategoryCommand) other).category));
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }
}

