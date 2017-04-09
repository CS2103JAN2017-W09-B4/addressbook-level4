package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.util.NotificationUtil;

//@@author A0141928B
/**
  * Tests for NotificationUtil
 */
public class NotificationUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void displayNotification_withDescription() {
        //Only works on Windows OS
        if (System.getProperty("os.name").startsWith("Windows")) {
            NotificationUtil.displayNotification("description");
        }
    }

    @Test
    public void displayNotification_noDescription_assertionError() {
        //Only works on Windows OS
        if (System.getProperty("os.name").startsWith("Windows")) {
            thrown.expect(AssertionError.class);
            NotificationUtil.displayNotification("");
        }
    }

}
