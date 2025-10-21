package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Jackson-friendly version of {@link Person}.
 * NOTE: Address is not part of the current model. We accept "address" in JSON for legacy
 * files but ignore it during (de)serialization so legacy data still loads.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;

    // Accept (but ignore) legacy "address" from JSON
    @JsonProperty("address")
    private final String ignoredAddress;

    // Canonical serialized fields
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    // Legacy AB-3 alias for tags -> map to skills
    @JsonProperty("tagged")
    private final List<JsonAdaptedSkill> tagged = new ArrayList<>();

    /**
     * Jackson constructor. Accepts canonical fields plus legacy "tagged" and "address" (ignored).
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address, // accepted, ignored
                             @JsonProperty("skills") List<JsonAdaptedSkill> skills,
                             @JsonProperty("categories") List<JsonAdaptedCategory> categories,
                             // legacy
                             @JsonProperty("tagged") List<JsonAdaptedSkill> tagged) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ignoredAddress = address;

        if (skills != null) {
            this.skills.addAll(skills);
        }
        if (categories != null) {
            this.categories.addAll(categories);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Convenience constructor used by tests.
     */
    public JsonAdaptedPerson(String name,
                             String phone,
                             String email,
                             List<JsonAdaptedCategory> categories,
                             List<JsonAdaptedSkill> skills) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ignoredAddress = null;

        if (categories != null) {
            this.categories.addAll(categories);
        }
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }

    public JsonAdaptedPerson(Person source) {
        this.name = source.getName().fullName;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;

        this.skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
        this.categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));

        // no address in model; keep null for legacy compatibility
        this.ignoredAddress = null;
    }

    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        // Merge canonical skills + legacy tagged
        List<JsonAdaptedSkill> allSkillNodes = new ArrayList<>(skills);
        allSkillNodes.addAll(tagged);

        final Set<Skill> modelSkills = allSkillNodes.stream()
                .map(s -> {
                    try {
                        return s.toModelType();
                    } catch (IllegalValueException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .collect(Collectors.toSet());

        final Set<Category> modelCategories = categories.stream()
                .map(c -> {
                    try {
                        return c.toModelType();
                    } catch (IllegalValueException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .collect(Collectors.toSet());

        // Your current Person signature (from earlier errors):
        // Person(Name, Phone, Email, Set<Category>, Set<Skill>)
        return new Person(modelName, modelPhone, modelEmail, modelCategories, modelSkills);
    }
}

