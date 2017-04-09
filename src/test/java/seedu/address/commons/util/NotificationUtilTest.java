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
        NotificationUtil.displayNotification("description");
    }

    @Test
    public void displayNotification_noDescription_assertionError() {
        thrown.expect(AssertionError.class);
        NotificationUtil.displayNotification("");
    }

}
