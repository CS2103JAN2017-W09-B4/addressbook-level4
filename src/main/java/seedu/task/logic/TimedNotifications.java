package seedu.task.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.ObservableList;
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

    public TimedNotifications(ObservableList<ReadOnlyTask> tasks, int interval) {
        this.tasks = tasks;
        this.period = interval;
    }

    public void updateMessage() {
        this.message = "";

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(calendar.HOUR_OF_DAY, 3); //Add 3 hours to current time

        StringBuilder date = new StringBuilder(); //To build a string that matches the deadline format
        date.append(new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()))
                    .append(" @ ").append(new SimpleDateFormat("hh:mm").format(calendar.getTime()));

        Deadline deadline;

        Iterator<ReadOnlyTask> i = tasks.iterator();
        while (i.hasNext()) {
            ReadOnlyTask current = i.next();
            deadline = current.getDate();
            if (deadline.value.equals(date.toString())) {
                this.message = current.getTaskName().toString();
                break;
            }
        }
    }

    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, period);
    }

    @Override
    public void run() {
        updateMessage();
        if (!message.isEmpty()) {
            NotificationUtil notification = new NotificationUtil();
            notification.displayNotification(this.message + " is due in 3 hours");
        }
    }
}
