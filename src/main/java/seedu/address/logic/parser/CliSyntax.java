package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");

    // Deprecated: legacy tag prefix
    public static final Prefix PREFIX_TAG = new Prefix("ttt/");

    // Prefixes for AssignCommand category (Department/Team/Role)
    public static final Prefix PREFIX_DEPARTMENT = new Prefix("d/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_TEAM = new Prefix("t/");

    // Prefix for filtering or describing skills
    public static final Prefix PREFIX_SKILL = new Prefix("s/");

    // Prefix for filtering or describing categories (shared with AssignCommand)
    // Use this for commands like: list c/Engineering
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");

    // Prefix for AssignCommand category and its value
    public static final Prefix PREFIX_ASSIGN_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_ASSIGN_CATEGORY_VALUE = new Prefix("v/");
}

