package nephilim;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void parse_correctCommand_success() throws NephilimException, IOException {

        /* Array List of all available commands. To potentially transfer into
         * a separate class with HashMap for faster lookup in the future */
        final ArrayList<String> commands = new ArrayList<>();
        /* Array List of the flags with which to tokenize string input. 1:1 index
         * matching with the commands ArrayList above*/
        final ArrayList<ArrayList<String>> flags = new ArrayList<>();
        //Setting up a simple commands and flags list.
        commands.addAll(Arrays.asList("mark", "dummy", "event"));
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        flags.add(new ArrayList<>(Arrays.asList("suspicious")));
        flags.add(new ArrayList<>(Arrays.asList(" ", " /from ", " /to ")));


        //Input "mark 4" with 2 tokens, 1 flag. tokenized into array {"mark", "4"}
        String[] markString = {"mark", "4"};
        assertArrayEquals(markString, new Parser(commands, flags).parse("mark 4"));

        //Input "event Party /from 2026-09-02 0830 /to 2026-09-03 2100" with 4 tokens, 3 different flags,
        // tokenized into array {"event", "Party", "2026-09-02 0830", "2026-09-03 2100"}
        String[] eventString = {"event", "Party", "2026-09-02 0830", "2026-09-03 2100"};
        assertArrayEquals(eventString,
                new Parser(commands, flags).parse("event Party /from 2026-09-02 0830"
                        + " /to 2026-09-03 2100"));
    }

    @Test
    public void parse_wrongCommand_exceptionThrown() {

        /* Array List of all available commands. To potentially transfer into
         * a separate class with HashMap for faster lookup in the future */
        final ArrayList<String> commands = new ArrayList<>();
        /* Array List of the flags with which to tokenize string input. 1:1 index
         * matching with the commands ArrayList above*/
        final ArrayList<ArrayList<String>> flags = new ArrayList<>();
        //Setting up a simple commands and flags list.
        commands.addAll(Arrays.asList("todo", "dummy", "event"));
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        flags.add(new ArrayList<>(Arrays.asList("suspicious")));
        flags.add(new ArrayList<>(Arrays.asList(" ", " /from ", " /to ")));

        //Input command that does not exist
        try {
            String[] inputString = {"blah"};
            assertEquals(inputString, new Parser(commands, flags).parse("blah"));
            fail(); //Should not reach this line
        } catch (NephilimIOMissingArgsException e) {
            String outputMessage = "The following input could not be understood: blah"
                    + "\nAdditional details: Command not recognised";
            assertEquals(outputMessage, e.toString());
        } catch (NephilimException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Input command that exists but with insufficient arguments
        try {
            String[] inputString = {"todo"};
            assertEquals(inputString, new Parser(commands, flags).parse("todo"));
        } catch (NephilimIOMissingArgsException e) {
            String outputMessage = "The following input could not be understood: todo"
                    + "\nAdditional details: A flag is missing or a field is empty. Possible missing flag:  ";
            assertEquals(outputMessage, e.toString());
        } catch (NephilimException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
