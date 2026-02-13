package nephilim;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

class DateTimeParser {
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static LocalDateTime parseTime(String input) throws NephilimIOMissingArgsException {
        try {
            return LocalDateTime.parse(input, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new NephilimIOMissingArgsException("", "Could not understand the date "
                    + e.getParsedString() + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.");
        }
    }

    public static String toOutputString(LocalDateTime input) {
        return input.format(OUTPUT_DATE_FORMAT);
    }

    public static String toInternalString(LocalDateTime input) {
        return input.format(INPUT_DATE_FORMAT);
    }
}
