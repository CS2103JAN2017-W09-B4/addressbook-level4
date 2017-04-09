package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    //@@author A0139322L
    public static final String TO_INDICATOR = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing,\n"
            + "or, if two index numbers are provided, all tasks between the two index numbers used\n"
            + "in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [" + TO_INDICATOR + " SECOND_INDEX]\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "or: " + COMMAND_WORD + " 1 " + TO_INDICATOR + " 5\n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_TASKS_SUCCESS = "Deleted Tasks:\n";

    public final int targetIndex;
    public final int times;

    public DeleteCommand(int targetIndex, int times) {
        this.targetIndex = targetIndex;
        this.times = times;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        String subsetDeleted = MESSAGE_DELETE_TASKS_SUCCESS;

        if (times == 0) {
        //@@author
            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
            //@@author A0139161J
            Task task = new Task (taskToDelete);
            task.setParserInfo("delete");
            task.setIndex(targetIndex - 1);
            try {
                GlobalStack gStack = GlobalStack.getInstance();
                gStack.getUndoStack().push(task); // task that got deleted, to be restored
                /**Debugging purpose
                 * System.out.println("Parser Info : " + task.getParserInfo());
                 * System.out.println("Index = " + index);
                 * System.out.println("Task added to stack : " + gStack.getUndoStack().peek().toString());
                 */
                model.deleteTask(taskToDelete);
                /**Debugging purpose
                 * gStack.printStack();
                 */
                //@@author

            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        //@@author A0139322L
        } else {
            if (lastShownList.size() < targetIndex || lastShownList.size() < (targetIndex + times - 1)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            GlobalStack gStack = GlobalStack.getInstance();
            for (int i = 0; i < times; i++) {
                ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

                Task task = new Task(taskToDelete);
                task.setParserInfo("delete");
                task.setIndex(targetIndex - 1);
                try {
                    gStack.getUndoStack().push(task);

                    model.deleteTask(taskToDelete);
                    if (i == times - 1) {
                        subsetDeleted = subsetDeleted + String.format("%1$s", taskToDelete);
                    } else {
                        subsetDeleted = subsetDeleted + String.format("%1$s,\n", taskToDelete);
                    }
                } catch (TaskNotFoundException pnfe) {
                    assert false : "The target task(s) cannot be missing";
                }
            }

            Integer t = new Integer(times);
            gStack.getUndoStack().push(t);

            return new CommandResult(subsetDeleted);
        }
    }
}
