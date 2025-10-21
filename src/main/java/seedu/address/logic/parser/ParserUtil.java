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

    /**
     * Parses {@code oneBasedIndex} into an {@link Index}.
     *
     * @param oneBasedIndex String representing a 1-based index.
     * @return Parsed {@link Index}.
     * @throws ParseException If the input is not a non-zero unsigned integer.
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        final String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String} into a {@link Name}.
     *
     * @param name Raw name string.
     * @return Parsed {@link Name}.
     * @throws ParseException If the name violates constraints.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        final String trimmed = name.trim();
        if (!Name.isValidName(trimmed)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmed);
    }

    /**
     * Parses a {@code String} into a {@link Phone}.
     *
     * @param phone Raw phone string.
     * @return Parsed {@link Phone}.
     * @throws ParseException If the phone violates constraints.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        final String trimmed = phone.trim();
        if (!Phone.isValidPhone(trimmed)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmed);
    }

    /**
     * Parses a {@code String} into an {@link Email}.
     *
     * @param email Raw email string.
     * @return Parsed {@link Email}.
     * @throws ParseException If the email violates constraints.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        final String trimmed = email.trim();
        if (!Email.isValidEmail(trimmed)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmed);
    }

    /**
     * Parses a {@code String} into an {@link Address}.
     * Retained for components that still accept addresses.
     *
     * @param address Raw address string.
     * @return Parsed {@link Address}.
     * @throws ParseException If the address violates constraints.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        final String trimmed = address.trim();
        if (!Address.isValidAddress(trimmed)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmed);
    }

    // ===== Category parsing =====

    /**
     * Parses a category/value pair into a {@link Category}.
     *
     * @param category Category name.
     * @param value Category value.
     * @return Parsed {@link Category}.
     * @throws ParseException If either entry violates constraints.
     */
    public static Category parseCategory(String category, String value) throws ParseException {
        requireAllNonNull(category, value);
        String c = category.trim();
        String v = value.trim();
        if (!Category.isValidData(c) || !Category.isValidData(v)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(c, v);
    }

    /**
     * Parses aligned collections of category names and values into a {@code Set<Category>}.
     * The two collections must be the same size; each name is paired with the value at the
     * same index.
     *
     * @param categories Collection of category names.
     * @param values Collection of category values.
     * @return Set of parsed {@link Category}.
     * @throws ParseException If counts mismatch or any entry violates constraints.
     */
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

    /**
     * Parses a {@code String} into a {@link Skill}.
     *
     * @param skill Raw skill string.
     * @return Parsed {@link Skill}.
     * @throws ParseException If the skill violates constraints.
     */
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
     * <p>Behavior:</p>
     * <ul>
     *   <li>If the collection is empty, returns an empty set.</li>
     *   <li>Blank entries (e.g., {@code ""}) are ignored; this supports inputs like {@code s/ s/friend}.</li>
     *   <li>Any non-blank invalid entry causes a {@link ParseException}.</li>
     * </ul>
     *
     * @param skills Collection of raw skill strings.
     * @return Set of parsed {@link Skill}.
     * @throws ParseException If any non-blank entry violates constraints.
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

