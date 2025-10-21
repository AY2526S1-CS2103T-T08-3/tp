package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Utility class for parsing user input strings into model objects.
 */
public class ParserUtil {

    /** Message displayed when the index is invalid. */
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /** Message when category lists have different lengths. */
    private static final String MESSAGE_CATEGORY_COUNT_MISMATCH =
            "Each category must have a corresponding value (counts must match).";

    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        final String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        final String trimmed = name.trim();
        if (!Name.isValidName(trimmed)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmed);
    }

    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        final String trimmed = phone.trim();
        if (!Phone.isValidPhone(trimmed)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmed);
    }

    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        final String trimmed = email.trim();
        if (!Email.isValidEmail(trimmed)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmed);
    }

    /** Retained for components that still accept addresses. */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        final String trimmed = address.trim();
        if (!Address.isValidAddress(trimmed)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmed);
    }

    // ===== Category parsing =====

    public static Category parseCategory(String category, String value) throws ParseException {
        requireAllNonNull(category, value);
        String c = category.trim();
        String v = value.trim();
        if (!Category.isValidData(c) || !Category.isValidData(v)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(c, v);
    }

    public static Set<Category> parseCategories(Collection<String> categories, Collection<String> values)
            throws ParseException {
        requireAllNonNull(categories, values);

        String[] ca = categories.toArray(new String[0]);
        String[] va = values.toArray(new String[0]);

        if (ca.length != va.length) {
            throw new ParseException(MESSAGE_CATEGORY_COUNT_MISMATCH);
        }

        final Set<Category> set = new HashSet<>();
        for (int i = 0; i < ca.length; i++) {
            set.add(parseCategory(ca[i], va[i]));
        }
        return set;
    }

    // ===== Skill parsing =====

    public static Skill parseSkill(String skill) throws ParseException {
        requireNonNull(skill);
        final String trimmed = skill.trim();
        if (!Skill.isValidSkillName(trimmed)) {
            throw new ParseException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(trimmed);
    }

    /**
     * Parses a collection of skills into a {@code Set<Skill>}.
     *
     * Behavior:
     * - If the collection is empty, returns an empty set.
     * - Blank entries (e.g., "") are ignored (this supports cases like "s/ s/friend").
     * - Any non-blank invalid entry causes a {@link ParseException}.
     */
    public static Set<Skill> parseSkills(Collection<String> skills) throws ParseException {
        requireNonNull(skills);
        final Set<Skill> set = new HashSet<>();
        for (String s : skills) {
            if (s == null) {
                continue;
            }
            String trimmed = s.trim();
            if (trimmed.isEmpty()) {
                // ignore blanks; reset behavior is handled by the parser when ALL are blank
                continue;
            }
            set.add(parseSkill(trimmed));
        }
        return set;
    }
}

