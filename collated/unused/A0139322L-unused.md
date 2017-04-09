# A0139322L-unused
###### \SearchCommand.java
``` java
//Supposed to be a command to find tasks with keywords
//Unused because FindCommand exists (but I didn't know)
package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.EmptySearchException;


public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String MESSAGE_SEARCH_SUCCESS = "The search results have been filtered.";
    public static final String MESSAGE_EMPTY_SEARCH = "No results were found!";

    public static int[] searchResults;
    public final Set<String> keyword;

    /**
    * Creates a SearchCommand using the given keyword.
    *
    *
    **@param keyword
    @throws IllegalValueException if the keyword is invalid.
    */
    public SearchCommand(String keyword) throws IllegalValueException {
        this.keyword = new HashSet<String>();
        this.keyword.add(keyword);
    }

    @Override
    public CommandResult execute() throws EmptySearchException {

        if (keyword.size() <= 0) {
            throw new EmptySearchException(MESSAGE_EMPTY_SEARCH);
        }

        // filter the list according to the search
        model.updateFilteredTaskList(keyword);

        return new CommandResult(MESSAGE_SEARCH_SUCCESS);
    }
}
```
