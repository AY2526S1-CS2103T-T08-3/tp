package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VALUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VALUE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Category;

public class AssignCommandTest {
    private static final String CATEGORY = "Role";
    private static final String VALUE = "Manager";
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_success() {
        Index validIndex = Index.fromZeroBased(0);
        Category validCategory = new Category("Role", "Manager");
        AssignCommand assignCommand = new AssignCommand(validIndex, validCategory);

        assertNotNull(assignCommand);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(null, new Category(CATEGORY, VALUE)));
    }

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignCommand(Index.fromZeroBased(1), null));
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY));

        // same values -> returns true
        AssignCommand commandWithSameValues = new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_SECOND_PERSON,
                new Category(VALID_CATEGORY_AMY, VALID_VALUE_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_FIRST_PERSON,
                new Category(VALID_CATEGORY_BOB, VALID_VALUE_BOB))));
    }

}
