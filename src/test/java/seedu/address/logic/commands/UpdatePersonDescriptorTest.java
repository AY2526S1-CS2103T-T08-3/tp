package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class UpdatePersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdatePersonDescriptor descriptorWithSameValues = new UpdatePersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        UpdatePersonDescriptor editedAmy = new UpdatePersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new UpdatePersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new UpdatePersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        UpdatePersonDescriptor UpdatePersonDescriptor = new UpdatePersonDescriptor();
        String expected = UpdatePersonDescriptor.class.getCanonicalName() + "{name="
                + UpdatePersonDescriptor.getName().orElse(null) + ", phone="
                + UpdatePersonDescriptor.getPhone().orElse(null) + ", email="
                + UpdatePersonDescriptor.getEmail().orElse(null) + ", address="
                + UpdatePersonDescriptor.getAddress().orElse(null) + ", categories="
                + UpdatePersonDescriptor.getCategories().orElse(null) + ", skills="
                + UpdatePersonDescriptor.getSkills().orElse(null) + "}";
        assertEquals(expected, UpdatePersonDescriptor.toString());
    }
}
