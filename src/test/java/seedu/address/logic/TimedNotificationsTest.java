package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.logic.TimedNotifications;

//@@author A0141928B
/**
 * Tests for TimedNotifications
 */
public class TimedNotificationsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void noTasks() {
        TimedNotifications notifications = new TimedNotifications(null, 1000);
        assertEquals(notifications.iterator, null);
        assertEquals(notifications.getMessage(), null);
        assertEquals(notifications.notification, null);
    }
}
