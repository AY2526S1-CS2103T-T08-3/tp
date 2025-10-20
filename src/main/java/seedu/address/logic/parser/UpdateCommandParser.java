package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DEPARTMENT, PREFIX_TEAM, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SKILL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        UpdatePersonDescriptor UpdatePersonDescriptor = new UpdatePersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            UpdatePersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            UpdatePersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            UpdatePersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        Set<Category> categories = new HashSet<>();
        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            categories.add(ParserUtil.parseCategory(
                    "Department",
                    argMultimap.getValue(PREFIX_DEPARTMENT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_TEAM).isPresent()) {
            categories.add(ParserUtil.parseCategory(
                    "Team",
                    argMultimap.getValue(PREFIX_TEAM).get())
            );
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            categories.add(ParserUtil.parseCategory(
                    "Role",
                    argMultimap.getValue(PREFIX_ROLE).get())
            );
        }
        if (!categories.isEmpty()) {
            UpdatePersonDescriptor.setCategories(categories);
        } else {
            UpdatePersonDescriptor.setCategories(null);
        }

        parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(UpdatePersonDescriptor::setSkills);

        if (!UpdatePersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }

        return new UpdateCommand(index, UpdatePersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        assert skills != null;

        if (skills.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> skillSet = skills.size() == 1 && skills.contains("")
                ? Collections.emptySet()
                : skills;
        return Optional.of(ParserUtil.parseSkills(skillSet));
    }
}

