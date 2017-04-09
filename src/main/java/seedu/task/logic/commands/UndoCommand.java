//@@author A0139161J
package seedu.task.logic.commands;

import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Last action undone";
    public static final String MESSAGE_FAIL = "Failed to undo";
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getUndoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_UNDO);
            }
            Object toUndo = gStack.getUndoStack().peek(); //needs improvement
            gStack.printStack();
            if (toUndo.getClass() == Task.class) {
                String parserInfo = ((Task) toUndo).getParserInfo();
                System.out.println("Parser Info = " + parserInfo);
                if (parserInfo.equals(COMMAND_WORD_ADD)) {
                    gStack.undoAdd();
                    model.deleteTask((Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } else if (parserInfo.equals(COMMAND_WORD_EDIT)) {
                    gStack.undoEdit();
                    model.updateTask(((Task) toUndo).getIndex(), (Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } else if (parserInfo.equals(COMMAND_WORD_DELETE)) { // it'll be delete command
                    gStack.undoDelete(); // pushes task to redostack
                    /**Debugging purpose
                     * System.out.println("To be restored : " + toUndo.toString());
                     * System.out.println("Index to be restored" + ((Task) toUndo).getEditTaskIndex());
                    */
                    model.insertTasktoIndex(((Task) toUndo).getIndex(), (Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                }
            //@@author A0139322L
            } else if (toUndo.getClass() == Integer.class) {
                Integer times = (Integer) gStack.getUndoStack().pop();
            	int intTimes = times.intValue();
                
                for (int i = 0; i < intTimes; i++) {
                    toUndo = gStack.getUndoStack().peek();
                    assert toUndo != null : "The target task(s) cannot be missing!";
                	gStack.undoDelete();
                	model.insertTasktoIndex(((Task) toUndo).getIndex(), (Task) toUndo);
                }

                gStack.getRedoStack().push(times);
                return new CommandResult(MESSAGE_SUCCESS);
            //@@author
            } else {
                TaskManager undo = gStack.undoClear();
                //System.out.println(undo.toString());
                model.resetData(undo);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
            }
        } catch (TaskNotFoundException e) {
            assert false : "not possible";
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
        assert false : "not possible";
        return null;
    }
}
