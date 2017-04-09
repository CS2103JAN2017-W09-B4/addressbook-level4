package seedu.task.logic;

import java.util.Timer;
import java.util.TimerTask;

import seedu.task.model.Model;

//@@author A0139161J
/**
 * Constantly updates the overdue task list "OMG Y U NO DO"
 */
public class OverdueTimer extends TimerTask {
    private Timer timer;
    private long interval;
    private final Model model;

    public OverdueTimer (Model model) {
        timer = new Timer();
        interval = (5000);
        this.model = model;
    }

    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0 , interval);
    }

    @Override
    public void run() {
        model.truncateOverdueList();
    }
}
