package nephilim;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {

    @Test
    public void construct_dateProperFormat_success() throws NephilimIOMissingArgsException {

        //Construct an event with the correct date format and arguments
        String correctString = "[E][ ] Party (from: Sep 02 2026 0830 to: Sep 03 2026 2100)";
        assertEquals(correctString,
                new Event("Party", "2026-09-02 0830", "2026-09-03 2100").toString());
    }

    @Test
    public void construct_dateWrongFormat_exceptionThrown() throws NephilimIOMissingArgsException {

        //Construct an event with the wrong date format
        String correctString = "[E][ ] Party (from: Sep 02 2026 0830 to: Sep 03 2026 2100)";
        String outputMessage = "The following input could not be understood: "
                + "event Party\nAdditional details: Could not understand the date "
                + "2026-09-02 8.30pm"
                + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.";
        try {
            assertEquals(correctString,
                    new Event("Party", "2026-09-02 8.30pm", "2026-09-03 2100").toString());
            fail(); // Should not reach this line
        } catch (NephilimIOMissingArgsException e) {
            assertEquals(outputMessage, e.toString());
        }
    }

}
