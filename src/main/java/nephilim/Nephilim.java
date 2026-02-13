package nephilim;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the main chatbot class. It comprises a UI, TaskList and Parser.
 */
class Nephilim {
    private Ui outUi;
    private TaskList tasks;
    private Parser parser;
    private Storage storage;

    /* Array List of all available commands. To potentially transfer into
     * a separate class with HashMap for faster lookup in the future */
    private final ArrayList<String> commands = new ArrayList<>();
    /* Array List of the flags with which to tokenize string input. 1:1 index
     * matching with the commands ArrayList above*/
    private final ArrayList<ArrayList<String>> flags = new ArrayList<>();
    /* Array List of consumers that take in a String array of arguments and performs
     * certain operations based on the command (1:1 index matching with commands)  */
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    private void initCommands() {

        //Clear existing commands, flags and instructions lists
        this.commands.clear();
        this.flags.clear();
        this.instructions.clear();

        //Set up commands, flags and instructions list.
        commands.addAll(Arrays.asList("bye", "mark", "unmark", "list", "delete", "todo", "deadline",
                "event", "find", "schedule"));

        //Bye command
        flags.add(new ArrayList<>()); //No flags needed
        instructions.add((x) -> {
            return this.close();

        });
        //Mark command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            int listIndex = Integer.parseInt(x[1]) - 1; //Due to printed list being 1 indexed, and java arraylists
                                                        //being 0 indexed, need to adjust argument by -1. This
                                                        //will apply to all future instructions.
            tasks.markTask(listIndex);
            Storage.saveListToData(tasks);
            return outUi.print("This task has been marked as done: \n"
                    + tasks.getTask(Integer.parseInt(x[1]) - 1));

        });
        //Unmark command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            int listIndex = Integer.parseInt(x[1]) - 1;
            tasks.unmarkTask(listIndex);
            Storage.saveListToData(tasks);
            return outUi.print("This task is no longer considered done: \n"
                    + tasks.getTask(listIndex));
        });
        //List command
        flags.add(new ArrayList<>());
        instructions.add((x) -> {
            return outUi.print("Your tasks are as follows:\n" + tasks.listOut());
        });
        //Delete command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            int listIndex = Integer.parseInt(x[1]) - 1;
            Task task = tasks.getTask(listIndex);
            tasks.deleteTask(listIndex);
            Storage.saveListToData(tasks);
            return outUi.print("This task is no longer: \n" + task
                    + '\n' + tasks.getSize() + " tasks remain.");
        });
        //todo command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            Todo task = new Todo(x[1]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            return outUi.print("This task has been added: \n" + task);
        });
        //deadline command
        flags.add(new ArrayList<>(Arrays.asList(" ", " /by ")));
        instructions.add((x) -> {
            Deadline task = new Deadline(x[1], x[2]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            return outUi.print("This task has been added: \n" + task);
        });
        //event command
        flags.add(new ArrayList<>(Arrays.asList(" ", " /from ", " /to ")));
        instructions.add((x) -> {
            Event task = new Event(x[1], x[2], x[3]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            return outUi.print("This task has been added: \n" + task);
        });
        //find command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            String output = tasks.listOut(x[1]);
            if (output.isEmpty()) {
                return outUi.print("No task of that description can be found. Note this is case-sensitive.");
            } else {
                return outUi.print("The following tasks have been found:\n" + output);
            }
        });
        //schedule command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            String output = tasks.listOut(DateTimeParser.parseTime(x[1]));
            if (output.isEmpty()) {
                return outUi.print("No task of that description can be found. Note this is case-sensitive.");
            } else {
                return outUi.print("The following tasks have been found:\n" + output);
            }
        });
        /* This causes the method to be long, true, but currently unable to find a way
        to easily add all these instructions into the arraylist without being
        wrapped by a function (constructor or otherwise) */

    }


    /**
     * Constructs the chatbot by setting up the UI, TaskList and Parser object,
     * Reads the stored data to TaskList using the Storage class.
     */
    public Nephilim() throws FileNotFoundException, IOException, NephilimException{
        this.outUi = new Ui();
        this.tasks = new TaskList(new ArrayList<>());

        initCommands();

        this.tasks = Storage.readListFromData(commands, flags);
        this.parser = new Parser(commands, flags);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            return this.execute(parser.parse(input));
        } catch (NephilimException | IOException e) {
            return e.toString();
        }
    }

    /**
     * Initialises chatbot. Returns greeting message.
     *
     * @return Greeting message.
     */
    public String init() {
        return outUi.print("Greetings from Nephilim-451.\nPlease state the nature of your request.");

    }

    /**
     * Closes chatbot. Returns closing message.
     * @return Closing message.
     */
    public String close() {
        return outUi.print("Fair winds guide you home.");
    }

    /**
     * Executes a set of instructions based on string array input. Input string array is
     * expected to have a valid command in index 0 and the rest to be arguments for that command.
     * @param tokens String array containing valid command in index 0 and arguments in following indexes.
     * @return What message to print back to user on completion of instruction.
     * @throws NephilimException If input could not be recognised in some way.
     * @throws IOException Certain instructions may throw this error due to writing/reading from file.
     */
    private String execute(String[] tokens) throws NephilimException, IOException {
        assert(commands.contains(tokens[0]));
        Instruction instruction = instructions.get(commands.indexOf(tokens[0]));
        return instruction.run(tokens);
    }
}
