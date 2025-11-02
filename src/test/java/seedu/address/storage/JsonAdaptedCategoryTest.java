package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Category;

public class JsonAdaptedCategoryTest {

    private static final String VALID_CATEGORY = "Role";
    private static final String VALID_VALUE = "Manager";

    private static final String VALID_EMPTY_VALUE = "";
    private static final String VALID_EMPTY_CATEGORY = "";

    private static final String INVALID_CATEGORY = "";
    private static final String INVALID_VALUE = "";

    @Test
    public void constructor_validFields_correctlyInitializes() {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(VALID_CATEGORY, VALID_VALUE);
        assertEquals(VALID_CATEGORY, adaptedCategory.getCategory());
        assertEquals(VALID_VALUE, adaptedCategory.getValue());
    }

    @Test
    public void constructor_modelCategory_correctlyInitializes() {
        Category category = new Category(VALID_CATEGORY, VALID_VALUE);
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(category);
        assertEquals(VALID_CATEGORY, adaptedCategory.getCategory());
        assertEquals(VALID_VALUE, adaptedCategory.getValue());
    }

    @Test
    public void toModelType_validCategory_returnsCategory() throws Exception {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(VALID_CATEGORY, VALID_VALUE);
        Category category = adaptedCategory.toModelType();
        assertEquals(VALID_CATEGORY, category.getCategory());
        assertEquals(VALID_VALUE, category.getValue());
    }

    @Test
    public void toModelType_nullCategory_throwsNullPointerException() {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(null, VALID_VALUE);
        assertThrows(NullPointerException.class, adaptedCategory::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsNullPointerException() {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(VALID_CATEGORY, null);
        System.out.println(adaptedCategory.getValue());
        assertThrows(NullPointerException.class, adaptedCategory::toModelType);
    }

    @Test
    public void toModelType_emptyCategory_returnsCategory() throws Exception {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(VALID_EMPTY_CATEGORY, VALID_VALUE);
        IllegalValueException e = assertThrows(IllegalValueException.class, adaptedCategory::toModelType);
        assertEquals(Category.CATEGORY_CORRUPT, e.getMessage());
    }

    @Test
    public void toModelType_emptyValue_returnsCategory() throws Exception {
        JsonAdaptedCategory adaptedCategory = new JsonAdaptedCategory(VALID_CATEGORY, VALID_EMPTY_VALUE);
        IllegalValueException e = assertThrows(IllegalValueException.class, adaptedCategory::toModelType);
        assertEquals(Category.CATEGORY_CORRUPT, e.getMessage());
    }
}
