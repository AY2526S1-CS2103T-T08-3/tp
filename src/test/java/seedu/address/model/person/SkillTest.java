package seedu.address.model.person;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    /*@Test
    public void constructor_invalidSkillName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Skill(invalidTagName));
    }

    @Test
    public void isValidSkillName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Skill.isValidSkillName(null));
    }*/

}
