package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.UndoCommand;

public class UndoRedoCommandTest extends TaskManagerGuiTest {
    //@@author A0139161J
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void undoRedo_addFeature_pass() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task88;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //undo previous addition of task
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        assertFindResult("find Task88");

        //redo the previous undo
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        assertFindResult("find Task88", td.task88);
    }

    @Test
    public void undo_noPreviousCommands_pass() {
        GlobalStack gStack = GlobalStack.getInstance();
        gStack.getUndoStack().removeAllElements();
        commandBox.runCommand("undo");
        assertResultMessage(GlobalStack.MESSAGE_NOTHING_TO_UNDO);
    }

    @Test
    public void redo_noPreviousCommands_pass() {
        GlobalStack gStack = GlobalStack.getInstance();
        gStack.getRedoStack().removeAllElements();
        commandBox.runCommand("redo");
        assertResultMessage(GlobalStack.MESSAGE_NOTHING_TO_REDO);
    }

    @Test
    public void undo_deleteCommand_pass() {
        commandBox.runCommand("delete 1");
        assertFindResult("find Task11");
        commandBox.runCommand("undo");
        assertFindResult("find Task11", td.task11);
    }

    @Test
    public void undo_editCommand_pass() throws IllegalValueException {

        String detailsToEdit = "Task11 d/15-Mar-2015 @ 10:00 p/1 i/NUS t/School";
        int addressBookIndex = 1;

        TestTask originalTask = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder().withName("Task11").withDeadline("15-Mar-2015 @ 10:00")
                .withPriorityLevel("1").withInformation("NUS").withTags("School").build();
        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
        commandBox.runCommand("undo");
        assertFindResult("find Task11", originalTask);
        commandBox.runCommand("redo");
        assertFindResult("find Task11", editedTask);
    }
    //@@author

    // adapted from AddCommandTest
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName().taskName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    // adapted from FindCommandTest
    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length
                + " Task(s) listed! Including " + expectedHits.length
                + " Exact Match case(s) & 0 Near Match case(s).");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

    // adapted from EditCommandTest
    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTaskName().taskName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
