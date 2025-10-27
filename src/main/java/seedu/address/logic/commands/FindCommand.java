package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Hashtable;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Category;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        StringBuffer output = new StringBuffer(String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        Map<String, Integer> depts = new Hashtable<>();
        for (Person p : model.getFilteredPersonList()) {
            String dept = identifyDept(p);
            depts.put(dept, depts.getOrDefault(dept, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : depts.entrySet()) {
            output.append(String.format("\n %s: %d persons", entry.getKey(), entry.getValue()));
        }
        return new CommandResult(output.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    private String identifyDept(Person p) {
        for (Category c: p.getCategories()) {
            if (c.getCategory().equals("Department")) {
                return c.getValue();
            }
        }
        return "N/A";
    }
}
