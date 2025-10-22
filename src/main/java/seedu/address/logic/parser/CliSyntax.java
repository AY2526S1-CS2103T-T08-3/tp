package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");

    // Prefixes for AssignCommand categories (Department / Team / Role)
    public static final Prefix PREFIX_DEPARTMENT = new Prefix("d/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_TEAM = new Prefix("t/");

    // Prefix for filtering or describing skills
    public static final Prefix PREFIX_SKILL = new Prefix("s/");

    // Prefix for filtering or describing categories (used by commands like: listbycategory c/role)
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");

    // Prefixes for AssignCommand category and its corresponding value
    public static final Prefix PREFIX_ASSIGN_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_ASSIGN_CATEGORY_VALUE = new Prefix("v/");
}

