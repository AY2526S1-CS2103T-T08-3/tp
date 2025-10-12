package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null, null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "";
        String value = "Manager";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory, value));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String category = "Role";
        String invalidValue = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(category, invalidValue));
    }

    @Test
    public void isValidData() {
        // null category
        assertThrows(NullPointerException.class, () -> Category.isValidData(null));
    }

}
