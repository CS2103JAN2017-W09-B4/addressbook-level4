package seedu.task.commons.util;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;

//@@author A0141928B
/**
 * Creates a notification in the system tray
 */
public class NotificationUtil {
    private static final String TITLE = "doTASK reminder";
    private static final String ICON_PATH = "src/main/resources/images/icon.png";
    private static SystemTray tray = SystemTray.getSystemTray();
    private static TrayIcon notification;

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    /**
     * Creates a notification on the system tray
     * @param description the message to be displayed in the notification, cannot be empty
     */
    public static void displayNotification(String description) {

        assert !description.isEmpty();

        try {
            notification = new TrayIcon(Toolkit.getDefaultToolkit().createImage(ICON_PATH));
            notification.setImageAutoSize(true);
            tray.add(notification);
        } catch (IllegalArgumentException iae) {
            logger.fine("Image not found.");
        } catch (UnsupportedOperationException uoe) {
            logger.fine("System tray not supported by current platform.");
        } catch (SecurityException se) {
            logger.fine("No permission to access system tray.");
        } catch (AWTException e) {
            logger.fine("Failed to set notification in system tray.");
        }

        notification.displayMessage(TITLE, description, MessageType.NONE);
    }
}
