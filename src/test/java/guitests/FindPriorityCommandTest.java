package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.task.commons.core.Messages;

//A0135762A
public class FindPriorityCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_highPriority_Cases() {
        assertFindPriorityResult("important", td.task11, td.task55); // multiple results

        // find after deleting one high priority task
        commandBox.runCommand("list");
        commandBox.runCommand("delete 1");
        assertFindPriorityResult("important", td.task55); // multiple results

        // find after completing one high priority task
        commandBox.runCommand("list");
        commandBox.runCommand("complete 4");
        assertFindPriorityResult("important"); // no results
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindPriorityResult("important"); // no results
    }

    @Test
    public void find_invalidPriorityCommand_fail() {
        commandBox.runCommand("priority");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindPriorityResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage("Listed all the high priority tasks!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
