package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EditCommand.EditPersonDescriptor}.
 *
 * NOTE: This file must NOT declare a public EditPersonDescriptor class.
 * It should import and use the real descriptor from main code.
 */
public class EditPersonDescriptorTest {

    @Test
    public void equals_sameEmptyDescriptors_returnsTrue() {
        EditCommand.EditPersonDescriptor a = new EditCommand.EditPersonDescriptor();
        EditCommand.EditPersonDescriptor b = new EditCommand.EditPersonDescriptor();
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        EditCommand.EditPersonDescriptor a = new EditCommand.EditPersonDescriptor();
        assertEquals(a, a);
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        EditCommand.EditPersonDescriptor a = new EditCommand.EditPersonDescriptor();
        assertNotEquals(a, null);
        assertNotEquals(a, new Object());
    }

    @Test
    public void copyConstructor_copiesValuesAndEqualityHolds() {
        EditCommand.EditPersonDescriptor original = new EditCommand.EditPersonDescriptor();
        EditCommand.EditPersonDescriptor copy = new EditCommand.EditPersonDescriptor(original);
        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());
    }

    @Test
    public void isAnyFieldEdited_emptyDescriptor_returnsFalse() {
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void toString_nonNullAndNonEmpty() {
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        String s = descriptor.toString();
        // We don't assert exact content to avoid coupling to implementation.
        assertTrue(s != null && !s.isEmpty());
    }
}

