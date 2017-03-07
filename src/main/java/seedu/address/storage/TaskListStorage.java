package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyAddressBook> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param taskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyAddressBook taskList) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyAddressBook)
     */
    void saveTaskList(ReadOnlyAddressBook taskList, String filePath) throws IOException;

}
