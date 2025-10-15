package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.person.Skill;

/**
 * Jackson-friendly version of {@link Skill}.
 */
class JsonAdaptedSkill {

    private final String skillName;

    @JsonCreator
    public JsonAdaptedSkill(String skillName) {
        this.skillName = skillName;
    }

    public JsonAdaptedSkill(Skill source) {
        this.skillName = source.skillName;
    }

    @JsonValue
    public String getSkillName() {
        return skillName;
    }

    public Skill toModelType() {
        return new Skill(skillName);
    }
}

