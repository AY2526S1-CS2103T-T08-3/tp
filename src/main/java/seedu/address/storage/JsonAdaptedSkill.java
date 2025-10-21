package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Skill;

/**
 * Jackson-friendly version of {@link Skill}.
 */
class JsonAdaptedSkill {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Skill's name field is missing!";

    private final String name;

    @JsonCreator
    public JsonAdaptedSkill(@JsonProperty("name") String name,
                            // Legacy AB-3 "tagged" items used "tagName"
                            @JsonProperty("tagName") String legacyTagName) {
        this.name = (name != null) ? name : legacyTagName;
    }

    public JsonAdaptedSkill(Skill source) {
        this.name = source.skillName;
    }

    public Skill toModelType() throws IllegalValueException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!Skill.isValidSkillName(name)) {
            throw new IllegalValueException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(name);
    }
}

