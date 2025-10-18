package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null, null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Category cat = new Category("Role", "Developer");
        assertTrue(cat.equals(cat));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Category cat = new Category("Role", "Developer");
        assertFalse(cat.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Category cat = new Category("Role", "Developer");
        String notACat = "Not a Category";
        assertFalse(cat.equals(notACat));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Category cat1 = new Category("Department", "HR");
        Category cat2 = new Category("Department", "HR");
        assertTrue(cat1.equals(cat2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Category cat1 = new Category("Role", "Developer");
        Category cat2 = new Category("Role", "Manager");
        assertFalse(cat1.equals(cat2));
    }

    @Test
    public void equals_listOfCategories_compareElements() {
        List<Category> list1 = Arrays.asList(
                new Category("Team", "Alpha"),
                new Category("Role", "Developer"),
                new Category("Department", "Finance")
        );

        List<Category> list2 = Arrays.asList(
                new Category("Team", "Alpha"),
                new Category("Role", "Developer"),
                new Category("Department", "Finance")
        );

        List<Category> list3 = Arrays.asList(
                new Category("Team", "Alpha"),
                new Category("Role", "Manager"), // different value here
                new Category("Department", "Finance")
        );

        // Check element wise equality
        assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            assertTrue(list1.get(i).equals(list2.get(i)));
        }

        assertEquals(list1, list2);

        // list1 and list3 differ at second element
        assertNotEquals(list1.get(1), list3.get(1));
    }

}
