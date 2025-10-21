package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UpdateCommand.UpdatePersonDescriptor}.
 *
 * NOTE: This file must NOT declare a public EditPersonDescriptor class.
 * It should import and use the real descriptor from main code.
 */
public class UpdatePersonDescriptorTest {

    @Test
    public void equals_sameEmptyDescriptors_returnsTrue() {
        UpdateCommand.UpdatePersonDescriptor a = new UpdateCommand.UpdatePersonDescriptor();
        UpdateCommand.UpdatePersonDescriptor b = new UpdateCommand.UpdatePersonDescriptor();
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        UpdateCommand.UpdatePersonDescriptor a = new UpdateCommand.UpdatePersonDescriptor();
        assertEquals(a, a);
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        UpdateCommand.UpdatePersonDescriptor a = new UpdateCommand.UpdatePersonDescriptor();
        assertNotEquals(a, null);
        assertNotEquals(a, new Object());
    }

    @Test
    public void copyConstructor_copiesValuesAndEqualityHolds() {
        UpdateCommand.UpdatePersonDescriptor original = new UpdateCommand.UpdatePersonDescriptor();
        UpdateCommand.UpdatePersonDescriptor copy = new UpdateCommand.UpdatePersonDescriptor(original);
        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());
    }

    @Test
    public void isAnyFieldEdited_emptyDescriptor_returnsFalse() {
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdateCommand.UpdatePersonDescriptor();
        assertFalse(descriptor.isAnyFieldUpdated());
    }

    @Test
    public void toString_nonNullAndNonEmpty() {
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdateCommand.UpdatePersonDescriptor();
        String s = descriptor.toString();
        assertTrue(s != null && !s.isEmpty());
    }
}

