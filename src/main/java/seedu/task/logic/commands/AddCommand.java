package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.antlr.runtime.RecognitionException;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.UniqueTaskList;

//@@author A0139161J
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Task Manager. "
            + "Parameters: TASK_NAME [d/DEADLINE] [p/PRIORITY_LEVEL] [i/ANY_INFO] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + "Buy Milk d/17-Mar-2017 p/4 i/For home t/chore";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the Task Manager";

    private boolean isEvent = false;
    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws CommandException
     */
    public AddCommand(String taskName, String deadline, String priorityLevel, String info, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        String fromDate = new String("");
        String fromTime = null;
        String toDate = new String("");
        String toTime = null;
        if (!deadline.equals("")) {
            try {
                deadline = nattyParser(deadline, fromDate, fromTime, toDate, toTime);
            } catch (RecognitionException e) {
                throw new IllegalValueException("Please key in a valid date input");
            }
        }
        this.toAdd = new Task(
                new TaskName(taskName),
                new Deadline(deadline),
                new PriorityLevel(priorityLevel),
                new Information(info),
                new UniqueTagList(tagSet)
        );
        toAdd.setParserInfo("add");
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            GlobalStack gStack = GlobalStack.getInstance();
            gStack.getUndoStack().push(toAdd);
            selectTargetTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    /** Returns String in format of hh:mm
     *  Precond: dateTime string formed by NattyParser required as input
     */
    private String getTime(String dateTime) {
        List<String> output = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(dateTime);
        List<String> list = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        st = new StringTokenizer(list.get(3), ":");
        while (st.hasMoreTokens()) {
            output.add(st.nextToken());
        }
        return new String(output.get(0) + ":" + output.get(1));
    }

    /**
     * UI auto selects the added task for user's convenience
     */
    private void selectTargetTask() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        int targetIndex = lastShownList.indexOf(toAdd);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
    }

    /**
     * Imported Natty parser for flexible input of dates
     * @param deadline
     * @param fromDate
     * @param fromTime
     * @param toDate
     * @param toTime
     * @return a String representing deadline
     */
    private String nattyParser(String deadline, String fromDate, String fromTime, String toDate, String toTime)
            throws RecognitionException {
        Parser parser = new Parser();
        List <DateGroup> groups = parser.parse(deadline);
        List dates = null;
        /* Future usage for recurring tasks
         *
         * int line;
         * int column;
         * String matchingValue;
         * String syntaxTree;
         * Map parseMap;
         * boolean isRecurring;
         * Date recursUntil;
         */
        for (DateGroup group: groups) {
            dates = group.getDates();
            /*line = group.getLine();
            column = group.getPosition();
            matchingValue = group.getText();
            syntaxTree = group.getSyntaxTree().toStringTree();
            parseMap = group.getParseLocations();
            isRecurring = group.isRecurring();
            recursUntil = group.getRecursUntil();
            */
        }

        if (dates != null) {
            fromDate = dates.get(0).toString();
            fromTime = getTime(fromDate);
            System.out.println(fromDate);
            if (dates.size() != 1) {
                toDate = dates.get(1).toString();
                toTime = getTime(toDate);
                System.out.println(toDate);
                isEvent = true;
            }
        }
        if (isEvent) {
            if (!checkDateCorrectness(fromDate, toDate)) {
                List<String> tempDate = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(fromDate);
                while (st.hasMoreTokens()) {
                    tempDate.add(st.nextToken());
                }
                Integer dateInteger = Integer.parseInt(tempDate.get(2));
                dateInteger -= 7;
                //fail safe method for now, in case if it truncates to a negative digit
                if (dateInteger < 0) {
                    dateInteger += 7;
                }
                tempDate.set(3, dateInteger.toString());
                StringBuilder sb = new StringBuilder(fromDate);
                sb.replace(8, 10, dateInteger.toString());
                fromDate = sb.toString();
            }
        }
        StringTokenizer st = new StringTokenizer(fromDate);
        List<String> listDeadline = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            listDeadline.add(st.nextToken());
        }
        List<String> endOfEvent = new ArrayList<String>();
        if (isEvent) {
            st = new StringTokenizer(toDate);
            while (st.hasMoreTokens()) {
                endOfEvent.add(st.nextToken());
            }
        }
        StringBuilder deadlineString = new StringBuilder();
        try {
            deadlineString.append(listDeadline.get(2) + "-" + listDeadline.get(1)
                + "-" + listDeadline.get(5) + " @ " + fromTime);
            if (isEvent) {
                deadlineString.append(" to " + endOfEvent.get(2) + "-" + endOfEvent.get(1) + "-" + endOfEvent.get(5)
                    + " @ " + toTime);
            }
            return deadlineString.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new RecognitionException();
        }
    }

    /** For event usage */
    private boolean checkDateCorrectness(String fromDate, String toDate) {
        boolean result = true;
        List<String> listFromDate = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(fromDate);
        while (st.hasMoreTokens()) {
            listFromDate.add(st.nextToken());
        }
        List<String> listToDate = new ArrayList<String>();
        st = new StringTokenizer(toDate);
        while (st.hasMoreTokens()) {
            listToDate.add(st.nextToken());
        }
        result = checkIfSameMonth(listFromDate, listToDate);
        return result;
    }

    /**
     * eg. fromDate = 17 Apr , toDate = 10 Apr
     *
     * If fromDate is more than toDate, returns false
     * else, returns true
     */
    private boolean checkIfSameMonth(List<String> listFromDate, List<String> listToDate) {
        boolean result = true;
        if (listFromDate.get(1).equals(listToDate.get(1))) {
            result = checkDate(listFromDate, listToDate);
        }
        return result;
    }

    private boolean checkDate(List<String> listFromDate, List<String> listToDate) {
        boolean result = true;
        int fromDate = Integer.parseInt(listFromDate.get(2));
        int toDate = Integer.parseInt(listToDate.get(2));
        if (fromDate > toDate) {
            result = false;
        }
        return result;
    }
}
