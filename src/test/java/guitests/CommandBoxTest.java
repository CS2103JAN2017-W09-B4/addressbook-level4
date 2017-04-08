package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.task.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    //@@author A0141928B
    @Test
    public void commandBox_useCommandList() {
        //insert some valid commands
        commandBox.runCommand("add task1");
        commandBox.runCommand("complete 1");
        //get previous commands
        commandBox.pressUpArrowKey();
        assertEquals("complete 1", commandBox.getCommandInput());
        commandBox.pressUpArrowKey();
        assertEquals("add task1", commandBox.getCommandInput());
        //try to get an older command where there are no older commands
        commandBox.pressUpArrowKey();
        assertEquals("add task1", commandBox.getCommandInput());
        //get next command
        commandBox.pressDownArrowKey();
        assertEquals("complete 1", commandBox.getCommandInput());
        //change directions from down to up
        commandBox.pressUpArrowKey();
        assertEquals("add task1", commandBox.getCommandInput());
        //change direction from up to down
        commandBox.pressDownArrowKey();
        assertEquals("complete 1", commandBox.getCommandInput());
        //empty command box when there are no commands more recent
        commandBox.pressDownArrowKey();
    }
}
