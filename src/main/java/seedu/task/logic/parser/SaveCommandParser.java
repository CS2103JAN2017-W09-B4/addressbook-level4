package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.ABSOLUTE_PATH_ARGS_FORMAT;
import static seedu.task.logic.parser.CliSyntax.RELATIVE_PATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

//@@author A0141928B
/**
 * Parses input argument and changes the save location
 */
public class SaveCommandParser {

    public static final String XML_EXTENSION = ".xml";
    public static final String FORWARD_SLASH = "/";
    public static final String BACKSLASH = "\\";

    /**
     * Parses the given argument in the context of the SaveCommand and
     * returns a SaveCommand object for execution
     */
    public Command parse(String args) {
        assert args != null;

        String path = args.trim();

        if (!path.endsWith(XML_EXTENSION)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveCommand.MESSAGE_INVALID_FILE_TYPE));
        }

        final Matcher relativeMatcher = RELATIVE_PATH_ARGS_FORMAT.matcher(path);
        final Matcher absoluteMatcher = ABSOLUTE_PATH_ARGS_FORMAT.matcher(path);
        if (relativeMatcher.matches()) {
            return new SaveCommand(path);
        } else {
            path = path.replace(FORWARD_SLASH, BACKSLASH);
            if (absoluteMatcher.matches()) {
                return new SaveCommand(path);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
            }
        }
    }

}
