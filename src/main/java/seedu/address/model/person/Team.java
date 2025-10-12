package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's Team
 */
public class Team {

    public static final String MESSAGE_CONSTRAINTS = "Team can take any value";

    /*
     * The first character of the team must not be a whitespace,
     */
    public static final String VALIDATION_REGEX = "(^$)|[^\\s].*";

    public final String value;

    /**
     * Constructs a Team.
     *
     * @param team A valid team.
     */
    public Team(String team) {
        requireNonNull(team);
        checkArgument(isValidTeam(team), MESSAGE_CONSTRAINTS);
        value = team;
    }

    /**
     * Returns true if a given string is a valid team.
     */
    public static boolean isValidTeam(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }
}
