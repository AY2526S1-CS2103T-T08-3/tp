package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Invalid INDEX";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INDEX_NOT_FOUND = "Employee with ID \"%1$d\" does not exist in the directory. "
            + "Please check the ID and try again.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Categories: ");
        person.getCategories().forEach(builder::append);
        builder.append("; Skills: ");
        String skillsString = person.getSkills().stream()
                .map(Skill::toString)
                .collect(Collectors.joining(", "));
        builder.append(skillsString);

        return builder.toString();
    }

    /**
     * Formats the short {@code person} for display to the user.
     */
    public static String formatShort(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(" (");
        person.getCategories().forEach(x -> builder.append(x.getCategory() + ": " + x.getValue() + "; "));
        builder.append(")").append(", Skills: [");;
        person.getSkills().forEach(x -> builder.append(x.toString().replaceAll("[\\[\\]]", "")));
        builder.append("]");
        return builder.toString();
    }
}
