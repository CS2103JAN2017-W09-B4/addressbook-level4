package seedu.task.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.NotificationUtil;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0141928B
/**
 * Creates a notification in the system tray
 */
public class TimedNotifications extends TimerTask {
    private String message;
    public Timer timer;
    private int period;
    public ObservableList<ReadOnlyTask> tasks;
    public Iterator<ReadOnlyTask> iterator = null;
    public NotificationUtil notification = null;
    public Logger logger = LogsCenter.getLogger(LogsCenter.class);

    public TimedNotifications(ObservableList<ReadOnlyTask> tasks, int interval) {
        this.tasks = tasks;
        this.period = interval;

        try {
            iterator = tasks.iterator();
        } catch (NullPointerException npe) {
            logger.fine("No task list");
        }
    }

    /**
     * Look through the task list to find a task that is due 3 hours later
     * @return The name of the task, "" if no task is due 3 hours later
     */
    public String getTaskName() {
        String taskName = "";

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(calendar.HOUR_OF_DAY, 3); //Add 3 hours to current time

        StringBuilder date = new StringBuilder(); //To build a string that matches the deadline format
        date.append(new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()))
                    .append(" @ ").append(new SimpleDateFormat("hh:mm").format(calendar.getTime()));

        Deadline deadline;

        if (iterator != null) {
            while (iterator.hasNext()) {
                ReadOnlyTask current = iterator.next();
                deadline = current.getDate();
                if (deadline.value.equals(date.toString())) {
                    taskName = current.getTaskName().toString();
                    break;
                }
            }
        }
        return taskName;
    }

    /**
     * Sets a given text as the message to be displayed in the notification
     * @param text the message to be displayed in the notification
     */
    public void createMessage(String text) {
        this.message = text;
    }

    /**
     * @return The message to be displayed in the notification
     */
    public String getMessage() {
        return this.message;
    }

    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, period);
    }

    @Override
    public void run() {
        createMessage(getTaskName());
        if (!message.isEmpty()) {
            notification = new NotificationUtil();
            notification.displayNotification(this.message + " is due in 3 hours");
        }
    }
}
