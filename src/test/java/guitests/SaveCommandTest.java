package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import seedu.task.logic.commands.SaveCommand;

//@@author A0141928B
/**
 * Tests for the save command
 * Currently doesn't test absolute path because absolute path only works on Windows
 */
public class SaveCommandTest extends TaskManagerGuiTest {

    private static final String ROOT_FOLDER_PATH = "src/test/data/SaveCommandTest/";
    private static final String FILE1_PATH_AND_NAME = ROOT_FOLDER_PATH + "taskManager1.xml";
    private static final String FILE2_PATH_AND_NAME = ROOT_FOLDER_PATH + "taskManager/.xml";
    private static final String FILE3_PATH_AND_NAME = ROOT_FOLDER_PATH + "taskManager.jpg";
    private static final String FILE4_PATH_AND_NAME = ROOT_FOLDER_PATH + "testDirectory/taskManager.xml";
    private static final String FILE5_PATH_AND_NAME = "";

    @Test
    public void save_ValidRelativePath_Success() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + FILE1_PATH_AND_NAME);
        //Message is correct
        assertResultMessage(String.format(String.format(SaveCommand.MESSAGE_SUCCESS, FILE1_PATH_AND_NAME)));
        //File exists
        File file = new File(FILE1_PATH_AND_NAME);
        assertTrue(file.exists());
    }

    @Test
    public void save_InvalidRelativePath_MessageUsage() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + FILE2_PATH_AND_NAME);
        //Message is the usage message
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        //File doesn't exist
        File file = new File(FILE2_PATH_AND_NAME);
        assertFalse(file.exists());
    }

    @Test
    public void save_InvalidFileExtension_MessageInvalidFileType() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + FILE3_PATH_AND_NAME);
        //Message is the invalid file type message
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_FILE_TYPE));
        //File doesn't exist
        File file = new File(FILE3_PATH_AND_NAME);
        assertFalse(file.exists());
    }

    @Test
    public void save_ValidRelativeDirectory_Success() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + FILE4_PATH_AND_NAME);
        //Message is correct
        assertResultMessage(String.format(String.format(SaveCommand.MESSAGE_SUCCESS, FILE4_PATH_AND_NAME)));
        //File exists
        File file = new File(FILE4_PATH_AND_NAME);
        assertTrue(file.exists());
    }

    @Test
    public void save_NoPath_MessageUsage() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + FILE5_PATH_AND_NAME);
        //Message is the usage message
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_FILE_TYPE));
        //File doesn't exist
        File file = new File(FILE5_PATH_AND_NAME);
        assertFalse(file.exists());
    }

    @After
    /**
     * Cleans up all the files created during the test
     */
    public void cleanup() {
        deleteFiles(new File(ROOT_FOLDER_PATH));
    }

    /**
     * Deletes all files and sub-directories in a given directory. Also deletes the given directory.
     * @param directory the directory which will have all its files deleted. Will also itself be deleted.
     */
    public void deleteFiles(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteFiles(file);
            }
        }
        directory.delete();
    }
}
