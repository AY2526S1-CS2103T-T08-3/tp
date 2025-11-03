package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Category;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGN_CATEGORY, PREFIX_ASSIGN_CATEGORY_VALUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), ive);
        }

        String category = capitalizeFirstLetterOnly(argMultimap.getValue(PREFIX_ASSIGN_CATEGORY).orElse(""));
        String value = argMultimap.getValue(PREFIX_ASSIGN_CATEGORY_VALUE).orElse("");

        if (category.isEmpty() || value.isEmpty()) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }

        if (!Category.isValidCategory(category)) {
            System.out.println(category);
            throw new ParseException(Category.CATEGORY_CONSTRAINTS);
        }

        if (!Category.isValidData(category) || !Category.isValidData(value)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }

        return new AssignCommand(index, new Category(category, value));
    }

    private String capitalizeFirstLetterOnly(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
