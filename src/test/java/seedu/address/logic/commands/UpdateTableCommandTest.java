package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.TableBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateTableCommand.
 */
public class UpdateTableCommandTest {

    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private String[] tableStatusInString = new String[]{"1", "4"};
    private Table originalTable = new TableBuilder(TABLE1).build();


    //    @Test TODO
    //    public void execute_tableUpdatedByModel_updateSuccessful() {
    //        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
    //        UpdateTableCommand updateTableCommand = new UpdateTableCommand(tableStatusInString);
    //        ModelManager expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    //        expectedModel.deleteTable(originalTable);
    //        expectedModel.addTable(editedTable);
    //        assertCommandSuccess(Mode.RESTAURANT_MODE, updateTableCommand, model, commandHistory,
    //                new CommandResult(UpdateTableCommand.MESSAGE_SUCCESS), expectedModel);
    //    }

    @Test
    public void execute_invalidTableNumber_updateFailure() {
        String[] invalidTableStatusInString = new String[]{"9", "4"}; // Table 9 does not exist
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(invalidTableStatusInString);
        assertCommandFailure(Mode.RESTAURANT_MODE, updateTableCommand, model, commandHistory,
                String.format(UpdateTableCommand.MESSAGE_INVALID_TABLE_NUMBER, "9"));
    }

    @Test
    public void execute_invalidTableStatus_updateFailure() {
        String[] invaliDTableStatusInString = new String[]{"1", "6"}; // Max capacity of table is only 4
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(invaliDTableStatusInString);
        assertCommandFailure(Mode.RESTAURANT_MODE, updateTableCommand, model, commandHistory,
                String.format(TableStatus.MESSAGE_INVALID_NUMBER_OF_CUSTOMERS,
                        originalTable.getTableStatus().numberOfSeats));
    }

    @Test
    public void equals() {
        String[] tableStatusInString = new String[]{"1", "4"};
        final UpdateTableCommand standardCommand = new UpdateTableCommand(tableStatusInString);

        // same values -> returns true
        UpdateTableCommand editedUpdateTableCommand = new UpdateTableCommand(tableStatusInString);
        assertTrue(standardCommand.equals(editedUpdateTableCommand));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different number of taken seats -> returns false
        tableStatusInString = new String[]{"0", "4"};
        editedUpdateTableCommand = new UpdateTableCommand(tableStatusInString);
        assertFalse(standardCommand.equals(editedUpdateTableCommand));

        // different number of seats -> returns false
        tableStatusInString = new String[]{"1", "5"};
        editedUpdateTableCommand = new UpdateTableCommand(tableStatusInString);
        assertFalse(standardCommand.equals(editedUpdateTableCommand));
    }

}