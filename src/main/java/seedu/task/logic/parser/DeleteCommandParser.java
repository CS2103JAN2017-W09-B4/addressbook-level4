package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Optional;
import java.util.OptionalInt;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        if (args.contains("-")) {             // denotes a deletion of a subset
            ArrayList<OptionalInt> indexes = ParserUtil.parseIndexes(args);
            if (!indexes.get(0).isPresent() || !indexes.get(1).isPresent()) {
                return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            int times = indexes.get(1).getAsInt() - indexes.get(0).getAsInt() + 1;
            return new DeleteCommand(indexes.get(0).getAsInt(), times);
        } else {
            Optional<Integer> index = ParserUtil.parseIndex(args);
            if (!index.isPresent()) {
                return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(index.get(), 0);
        }

    }

}
