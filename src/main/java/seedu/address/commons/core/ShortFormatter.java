package seedu.address.commons.core;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/**
 * Truncates and formats verbose log reports for audit logs
 */
public class ShortFormatter extends Formatter {
    @Override
    public String format(LogRecord rec) {
        return String.format("%1$tF %1$tT | %2$s%n \n", rec.getMillis(), rec.getMessage().substring(8));
    }
}
