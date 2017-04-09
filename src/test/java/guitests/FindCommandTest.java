package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.task.commons.core.Messages;

//@@author A0135762A
public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_exactMatchCases() {
        assertFindExactMatchResult("find Random"); // no results
        assertFindExactMatchResult("find Task11", td.task11); // multiple results
        assertFindExactMatchResult("find tASK11", td.task11); // multiple results, not case sensitive

        // find after deleting one task
        commandBox.runCommand("list");
        commandBox.runCommand("delete 1");
        assertFindExactMatchResult("find Task11");
        assertFindExactMatchResult("find Task33", td.task33);

        // find after completing one task
        commandBox.runCommand("list");
        commandBox.runCommand("complete 2");
        assertFindExactMatchResult("find Task33");
        assertFindExactMatchResult("find Task55", td.task55);
    }

    @Test
    public void find_nearMatchCases() {
        commandBox.runCommand("list");

        // Levenshtein distance
        assertFindNearMatchResult("find Task5566");  // no results, additional two or more characters
        assertFindNearMatchResult("find ask55", td.task55); // missing one character
        assertFindNearMatchResult("find Task555", td.task55);  // additional one character

        // transposition error
        assertFindNearMatchResult("find sTks55");  // no results, more than one pair of letters swapped
        assertFindNearMatchResult("find Tsak55", td.task55); // only one pair of letters swapped

        // contains
        assertFindNearMatchResult("find 555");  // 555 is not within Task55
        assertFindNearMatchResult("find 55", td.task55); // 55 is within Task55
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindExactMatchResult("find Task"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findtask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindExactMatchResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length
                + " Task(s) listed! Including " + expectedHits.length
                + " Exact Match case(s) & 0 Near Match case(s).");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

    private void assertFindNearMatchResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length
                + " Task(s) listed! Including 0 Exact Match case(s) & "
                + expectedHits.length + " Near Match case(s).");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
