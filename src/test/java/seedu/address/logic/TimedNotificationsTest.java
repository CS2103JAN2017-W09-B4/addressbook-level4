package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TypicalTestTasks;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.TimedNotifications;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

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

    @Test
    public void taskDueIn3Hours() {
        //Get today's date and time
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(calendar.HOUR_OF_DAY, 3); //Add 3 hours to current time

        StringBuilder date = new StringBuilder(); //To build a string that matches the deadline format
        date.append(new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()))
                    .append(" @ ").append(new SimpleDateFormat("hh:mm").format(calendar.getTime()));

        //Create a task with the current time + 3 hours
        try {
            taskManager.addTask(new Task(new TaskBuilder().withName("task123")
                    .withInformation("info")
                    .withPriorityLevel("1")
                    .withDeadline(date.toString())
                    .withTags("tag").build()));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
        taskList = taskManager.getTaskList();

        notifications = new TimedNotifications(taskList, 1000);
        notifications.run();
        assertNotNull(notifications.iterator);
        assertEquals(notifications.getMessage(), "task123");
        assertNotNull(notifications.notification);
    }
}
