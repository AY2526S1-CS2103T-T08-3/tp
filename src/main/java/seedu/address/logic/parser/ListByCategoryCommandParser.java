package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.address.logic.commands.ListByCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ListByCategoryCommand}.
 *
 * Expected format: {@code listbycategory c/<role|team|department>}
 */
public class ListByCategoryCommandParser implements Parser<ListByCategoryCommand> {

    @Override
    public ListByCategoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        String category = argMultimap.getValue(PREFIX_CATEGORY)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListByCategoryCommand.MESSAGE_USAGE)))
                .trim()
                .toLowerCase();

        if (!category.equals(ListByCategoryCommand.CATEGORY_ROLE)
                && !category.equals(ListByCategoryCommand.CATEGORY_TEAM)
                && !category.equals(ListByCategoryCommand.CATEGORY_DEPARTMENT)) {
            throw new ParseException("Category must be one of: role, team, department.");
        }

        return new ListByCategoryCommand(category);
    }
}

