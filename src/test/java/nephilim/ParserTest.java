package nephilim;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_correctCommand_success() throws NephilimIOMissingArgsException {

        //Input "mark 4" with 2 tokens, 1 flag. tokenized into array {"mark", "4"}
        String[] markString = {"mark", "4"};
        assertArrayEquals(markString, new Parser(new TaskList(new ArrayList<>()), new Ui()).parse("mark 4"));

        //Input "event Party /from 2026-09-02 0830 /to 2026-09-03 2100" with 4 tokens, 3 different flags,
        // tokenized into array {"event", "Party", "2026-09-02 0830", "2026-09-03 2100"}
        String[] eventString = {"event", "Party", "2026-09-02 0830", "2026-09-03 2100"};
        assertArrayEquals(eventString,
                new Parser(new TaskList(new ArrayList<>()), new Ui()).parse("event Party /from 2026-09-02 0830"
                        + " /to 2026-09-03 2100"));
    }

    @Test
    public void parse_wrongCommand_exceptionThrown() {

        //Input command that does not exist
        try {
            String[] inputString = {"blah"};
            assertEquals(inputString, new Parser(new TaskList(new ArrayList<>()), new Ui()).parse("blah"));
            fail(); //Should not reach this line
        } catch (NephilimIOMissingArgsException e) {
            String outputMessage = "The following input could not be understood: blah"
                    + "\nAdditional details: Command not recognised";
            assertEquals(outputMessage, e.toString());
        }

        //Input command that exists but with insufficient arguments
        try {
            String[] inputString = {"todo"};
            assertEquals(inputString, new Parser(new TaskList(new ArrayList<>()), new Ui()).parse("todo"));
        } catch (NephilimIOMissingArgsException e) {
            String outputMessage = "The following input could not be understood: todo"
                    + "\nAdditional details: A flag is missing or a field is empty. Possible missing flag:  ";
            assertEquals(outputMessage, e.toString());
        }
    }

    @Test
    public void parseAndRun_correctCommand_success() throws NephilimException, IOException {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.addTask(new Todo("Apple orange"));
        Parser parser = new Parser(tasks, new Ui());


        //Add command
        parser.parseAndRun("todo Apple apple");
        assertEquals("[T][ ] Apple apple", tasks.getTask(1).toString());

        //Mark command
        parser.parseAndRun("mark 2");
        assertEquals("[T][X] Apple apple", tasks.getTask(1).toString());


    }
}
