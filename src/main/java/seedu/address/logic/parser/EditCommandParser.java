package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CATEGORY_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG; // legacy alias

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Parses input arguments and creates a new EditCommand object.
 * NOTE: Address is not part of the model; we do not parse or set it here.
 */
public class EditCommandParser implements Parser<EditCommand> {

    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Accept both s/ (skills) and legacy t/ (tags).
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ASSIGN_CATEGORY, PREFIX_ASSIGN_CATEGORY_VALUE,
                PREFIX_SKILL, PREFIX_TAG
        );

        final Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        // Duplicate checks in one call, in the exact order many AB-3 tests expect: name, phone, email
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditPersonDescriptor descriptor = new EditPersonDescriptor();

        // Non-repeatables (surface ParseException directly)
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        // Categories (optional; allow reset if both lists are all blank)
        parseCategoriesForEdit(
                argMultimap.getAllValues(PREFIX_ASSIGN_CATEGORY),
                argMultimap.getAllValues(PREFIX_ASSIGN_CATEGORY_VALUE)
        ).ifPresent(descriptor::setCategories);

        // Skills (repeatable; allow reset if all blanks) — merge s/ and legacy t/
        List<String> rawSkills = new ArrayList<>();
        rawSkills.addAll(argMultimap.getAllValues(PREFIX_SKILL));
        rawSkills.addAll(argMultimap.getAllValues(PREFIX_TAG));
        parseSkillsForEdit(rawSkills).ifPresent(descriptor::setSkills);

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, descriptor);
    }

    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories,
                                                           Collection<String> values) throws ParseException {
        if (categories == null || values == null) {
            return Optional.empty();
        }
        if (categories.isEmpty() || values.isEmpty()) {
            return Optional.empty();
        }

        boolean allCatsBlank = categories.stream().allMatch(s -> s == null || s.trim().isEmpty());
        boolean allValsBlank = values.stream().allMatch(s -> s == null || s.trim().isEmpty());

        if (allCatsBlank && allValsBlank) {
            return Optional.of(Collections.emptySet());
        }

        return Optional.of(ParserUtil.parseCategories(categories, values));
    }

    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        if (skills == null || skills.isEmpty()) {
            return Optional.empty();
        }

        boolean allBlank = skills.stream().allMatch(s -> s == null || s.trim().isEmpty());
        if (allBlank) {
            return Optional.of(Collections.emptySet());
        }

        return Optional.of(ParserUtil.parseSkills(skills));
    }
}

