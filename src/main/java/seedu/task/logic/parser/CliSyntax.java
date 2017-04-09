package seedu.task.logic.parser;

import java.util.regex.Pattern;

import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY_LEVEL = new Prefix("p/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ANY_INFO = new Prefix("i/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    //@@author A0141928B
    public static final Pattern ABSOLUTE_PATH_ARGS_FORMAT =
            Pattern.compile("(?!.*\\\\\\\\)([A-Z|a-z]:\\\\[^*|\"<>?\\n]*)([^\\\\/:*|\"<>?\\n]\\.xml)");
    public static final Pattern RELATIVE_PATH_ARGS_FORMAT =
            Pattern.compile("(?!.*//)([^*|\\\"<>?\\n]*)([^\\\\\\/:*|\\\"<>?\\n]\\.xml)");
}
