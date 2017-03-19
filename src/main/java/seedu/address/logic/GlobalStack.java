package seedu.address.logic;

import java.util.NoSuchElementException;
import java.util.Stack;

import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;



public class GlobalStack {

    private static GlobalStack instance = null;
    private Stack<Object> undoStack;
    private Stack<Object> redoStack;
    public static final String MESSAGE_NOTHING_TO_UNDO = "No commands left to undo";
    public static final String MESSAGE_NOTHING_TO_REDO = "No commands left to redo";

    private GlobalStack() {
        undoStack = new Stack<Object>();
        redoStack = new Stack<Object>();
    }

    public static GlobalStack getInstance() {
        if (instance == null) {
            instance = new GlobalStack();
        }
        return instance;
    }

    /*
     * Pre-condition : Top of stack contains 2 consecutive task objects to be popped
     * Usage : Undo Edit Command
     */
    public Task undoEdit() {
        Task originalTask = (Task) undoStack.pop();
        Task editedTask = (Task) undoStack.pop();
        redoStack.push(originalTask);
        redoStack.push(editedTask);
        return originalTask;
    }

    public Task redoEdit() {
        Task editedTask = (Task) redoStack.pop();
        Task originalTask = (Task) redoStack.pop();
        undoStack.push(editedTask);
        undoStack.push(originalTask);
        return editedTask;
    }

    /*
     * Pre-condition : Top of stack contains task object
     * Usage : Undo Add Command
     */
    public Task undoAdd() throws NoSuchElementException {
        try {
            Task temp = (Task) undoStack.pop();
            redoStack.push(temp);
            return temp;
        } catch (NoSuchElementException ne) {
            throw new NoSuchElementException(MESSAGE_NOTHING_TO_UNDO);
        }
    }

    public Task redoAdd() throws NoSuchElementException {
        try {
            Task temp = (Task) redoStack.pop();
            undoStack.push(temp);
            return temp;
        } catch (NoSuchElementException ne) {
            throw new NoSuchElementException(MESSAGE_NOTHING_TO_REDO);
        }
    }

    public ReadOnlyTask undoDelete() {
        ReadOnlyTask undoTask = (ReadOnlyTask) undoStack.pop();
        redoStack.push(undoTask);
        return undoTask;
    }

    public int getUndoDeleteIndex() {
        int undoIndex = (int) undoStack.pop();
        redoStack.push(undoIndex);
        return undoIndex;
    }

    public ReadOnlyTask redoDelete() {
        ReadOnlyTask redoTask = (ReadOnlyTask) redoStack.pop();
        undoStack.push(redoTask);
        return redoTask;
    }

    public void getRedoDeleteIndex() {
        int redoIndex = (int) redoStack.pop();
        undoStack.push(redoIndex);
    }

    public ObservableList<ReadOnlyTask> undoClear() {
        ObservableList<ReadOnlyTask> toUndo = (ObservableList<ReadOnlyTask>) undoStack.pop();
        redoStack.push(toUndo);
        return toUndo;
    }

    public void redoClear() {
        undoStack.push(redoStack.pop());
    }

    public Stack<Object> getUndoStack() {
        return undoStack;
    }

    public Stack<Object> getRedoStack() {
        return redoStack;
    }

    public void printStack () {
        Stack<Object> temp = (Stack<Object>) undoStack.clone();
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(temp.pop().toString());
        }
    }
}
