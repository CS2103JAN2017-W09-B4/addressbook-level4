package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    //@@author A0139161J
    @XmlElement
    private List<XmlAdaptedTask> completedTasks;
    @XmlElement
    private List<XmlAdaptedTask> overdueTasks;
    //@@author

    /**
     * Creates an empty XmlSerializableTaskManager.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskManager() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
        //@@author A0139161J
        completedTasks = new ArrayList<>();
        overdueTasks = new ArrayList<>();
        //@@author
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        //@@author A0139161J
        completedTasks.addAll((src.getCompletedTaskList().stream().map(XmlAdaptedTask::new))
                .collect(Collectors.toList()));
        overdueTasks.addAll((src.getOverdueTaskList().stream().map(XmlAdaptedTask::new))
                .collect(Collectors.toList()));
        //@@author
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> tasks = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tasks);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tags);
    }

    //@@author A0139161J
    @Override
    public ObservableList<ReadOnlyTask> getCompletedTaskList() {
        final ObservableList<Task> tasks = this.completedTasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tasks);
    }

    @Override
    public ObservableList<ReadOnlyTask> getOverdueTaskList() {
        final ObservableList<Task> tasks = this.overdueTasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tasks);
    }
    //@@author
}
