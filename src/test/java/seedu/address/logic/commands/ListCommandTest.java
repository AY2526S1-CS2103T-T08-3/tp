package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicate.PersonHasTagPredicate;
import seedu.address.testutil.TypicalPersons;

public class ListCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_listAll_showsAllPersons() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listBySkillJava_showsSkilledPersons() {
        PersonHasTagPredicate pred = new PersonHasTagPredicate("java");
        expectedModel.updateFilteredPersonList(pred);
        assertCommandSuccess(new ListCommand(pred), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
