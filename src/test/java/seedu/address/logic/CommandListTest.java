package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.CommandList;

//@@author A0141928B
/**
 * Tests for CommandList
 */
public class CommandListTest {
    private CommandList commandList = CommandList.getInstance();

    @Test
    public void addCommandsToList() {
        //add one command
        commandList.addToList("command1");
        assertTrue(commandList.iterator.hasNext());
        assertEquals(commandList.iterator.next(), "command1");

        //add another command
        commandList.addToList("command2");
        assertEquals(commandList.iterator.next(), "command2");
        assertEquals(commandList.iterator.next(), "command1");
    }

    @Test
    public void iterateThroughCommands() {
        //cycle through commands
        commandList.addToList("command1");
        commandList.addToList("command2");
        commandList.addToList("command3");
        assertEquals(commandList.iterator.next(), "command3");
        assertEquals(commandList.iterator.next(), "command2");
        assertEquals(commandList.iterator.next(), "command1");
        assertEquals(commandList.iterator.previous(), "command1");
        assertEquals(commandList.iterator.previous(), "command2");
        assertEquals(commandList.iterator.previous(), "command3");
        assertEquals(commandList.iterator.next(), "command3");
        assertEquals(commandList.iterator.next(), "command2");

        //reset iterator
        commandList.resetIterator();
        assertEquals(commandList.iterator.next(), "command3");

        //try to go to a more recent command than the most recent one
        assertEquals(commandList.iterator.previous(), "command3");
        assertFalse(commandList.iterator.hasPrevious());

        //try to go to an older command than the oldest one
        assertEquals(commandList.iterator.next(), "command3");
        assertEquals(commandList.iterator.next(), "command2");
        assertEquals(commandList.iterator.next(), "command1");
        assertFalse(commandList.iterator.hasNext());
    }
}
