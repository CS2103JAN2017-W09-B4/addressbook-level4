package seedu.address.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.testutil.TypicalTestTasks;
import seedu.task.logic.TimedNotifications;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0141928B
/**
 * Tests for TimedNotifications
 */
public class TimedNotificationsTest {

    private TaskManager taskManager = new TaskManager();
    private ObservableList<ReadOnlyTask> taskList = taskManager.getTaskList();
    TimedNotifications notifications;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void noTasks() {
        //no list
        notifications = new TimedNotifications(null, 1000);
        assertNull(notifications.iterator);
        assertNull(notifications.getMessage());
        assertNull(notifications.notification);

        //empty list
        notifications = new TimedNotifications(taskList, 1000);
        assertNotNull(notifications.iterator);
        assertNull(notifications.getMessage());
        assertNull(notifications.notification);
    }

    @Test
    public void typicalTasks() {
        TaskManager typicalAddressBook = new TypicalTestTasks().getTypicalTaskManger();
        ObservableList<ReadOnlyTask> newTasks = typicalAddressBook.getTaskList();

        //list with no tasks right now
        notifications = new TimedNotifications(newTasks, 1000);
        assertNotNull(notifications.iterator);
        assertNull(notifications.getMessage());
        assertNull(notifications.notification);
    }
}
