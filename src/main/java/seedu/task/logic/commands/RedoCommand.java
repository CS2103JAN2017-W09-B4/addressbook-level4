package seedu.task.logic.commands;

import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
//@@author A0139161J
/**
 * Reverse the previous undo commands
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Last action reverted";
    public static final String MESSAGE_FAIL = "Failed to redo";
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getRedoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_REDO);
            }
            Object toRedo = gStack.getRedoStack().peek();
            if (toRedo.getClass() == Task.class) {
                return taskToUndo(toRedo, gStack);
            //@@author A0139322L
            } else if (toRedo.getClass() == Integer.class) {
                Integer times = (Integer) gStack.getRedoStack().pop();
                int intTimes = times.intValue();

                for (int i = 0; i < intTimes; i++) {
                    toRedo = gStack.getRedoStack().peek();
                    assert toRedo.getClass() == Task.class : "The target task(s) cannot be missing!";
                    ReadOnlyTask unmutableTask = gStack.redoDelete();
                    model.deleteTask(unmutableTask);
                }

                gStack.getUndoStack().push(times);
                return new CommandResult(MESSAGE_SUCCESS);
            //@@author A0139161J
            } else {
                gStack.redoClear();
                model.resetData(new TaskManager());
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
            }
        } catch (DuplicateTaskException e) {
            assert false : "There will not be any duplicate tasks";
        } catch (TaskNotFoundException e) {
            assert false : "Tasks will always be found";
        }
        assert false : "It will not reach this statement, as a CommandResult will always be thrown no matter what";
        return null;
    }

    /**
     * Detects if it's an add/edit/delete command to redo
     * @param Object toRedo
     * @param GlobalStack gStack
     * @return CommandResult Object
     * @throws DuplicateTaskException
     * @throws TaskNotFoundException
     */
    private CommandResult taskToUndo(Object toRedo, GlobalStack gStack)
            throws DuplicateTaskException, TaskNotFoundException {
        String parserInfo = ((Task) toRedo).getParserInfo();
        if (parserInfo.equals(COMMAND_WORD_ADD)) {
            gStack.redoAdd();
            model.addTask((Task) toRedo);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
        } else if (parserInfo.equals(COMMAND_WORD_EDIT)) {
            Task editedTask = gStack.redoGetEditedTask();
            Task originalTask = gStack.redoGetOriginalTask();
            model.deleteTask(originalTask);
            model.addTask(editedTask);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
        } else {
            ReadOnlyTask unmutableTask = gStack.redoDelete();
            model.deleteTask(unmutableTask);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
        }
    }
}
