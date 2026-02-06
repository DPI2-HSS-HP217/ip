package nephilim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser class parses string inputs and handles the logic of how to respond
 * to command tokens within the input.
 * In the future, Parser will hand off parsed input to separate logic classes
 * to read arguments, throw exceptions and perform further operations.
 */

class Parser {

    /* Array List of all available commands. To potentially transfer into
    * a separate class with HashMap for faster lookup in the future */
    private final ArrayList<String> commands = new ArrayList<>();
    /* Array List of the flags with which to tokenize string input. 1:1 index
    * matching with the commands ArrayList above*/
    private final ArrayList<ArrayList<String>> flags = new ArrayList<>();
    /* Array List of consumers that take in a String array of arguments and performs
    * certain operations based on the command (1:1 index matching with commands)  */
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    /**
     * Constructor that will set up all arraylists with default commands.
     * @param tasks TaskList from Nephilim
     * @param outUi Ui from Nephilim
     */
    public Parser(TaskList tasks, Ui outUi) {
        //Set up commands list
        commands.addAll(Arrays.asList("bye", "mark", "unmark", "list", "delete", "todo", "deadline",
                "event", "find"));
        //Bye command
        flags.add(new ArrayList<>()); //No flags needed
        instructions.add((x) -> {
            return false; //Boolean dictates to Nephilim whether it should cease running
        });
        //Mark command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
           tasks.markTask(Integer.parseInt(x[1]) - 1);
           Storage.saveListToData(tasks);
           outUi.print("This task has been marked as done: \n"
                   + tasks.getTask(Integer.parseInt(x[1]) - 1));
           return true;
        });
        //Unmark command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            tasks.unmarkTask(Integer.parseInt(x[1]) - 1);
            Storage.saveListToData(tasks);
            outUi.print("This task is no longer considered done: \n"
                    + tasks.getTask(Integer.parseInt(x[1]) - 1));
            return true;
        });
        //List command
        flags.add(new ArrayList<>());
        instructions.add((x) -> {
            outUi.print("Your tasks are as follows:\n" + tasks.listOut());
            return true;
        });
        //Delete command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            Task task = tasks.getTask(Integer.parseInt(x[1]) - 1);
            tasks.deleteTask(Integer.parseInt(x[1]) - 1);
            Storage.saveListToData(tasks);
            outUi.print("This task is no longer: \n" + task
                    + '\n' + tasks.getSize() + " tasks remain.");
            return true;
        });
        //todo command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            Todo task = new Todo(x[1]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            outUi.print("This task has been added: \n" + task);
            return true;
        });
        //deadline command
        flags.add(new ArrayList<>(Arrays.asList(" ", " /by ")));
        instructions.add((x) -> {
            Deadline task = new Deadline(x[1], x[2]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            outUi.print("This task has been added: \n" + task);
            return true;
        });
        //event command
        flags.add(new ArrayList<>(Arrays.asList(" ", " /from ", " /to ")));
        instructions.add((x) -> {
            Event task = new Event(x[1], x[2], x[3]);
            tasks.addTask(task);
            Storage.saveListToData(tasks);
            outUi.print("This task has been added: \n" + task);
            return true;
        });
        //find command
        flags.add(new ArrayList<>(Arrays.asList(" ")));
        instructions.add((x) -> {
            String output = tasks.listOut(x[1]);
            if (output.isEmpty()) {
                outUi.print("No task of that description can be found. Note this is case-sensitive.");
            } else {
                outUi.print("The following tasks have been found:\n" + output);
            }
            return true;
        });
    }

    /**
     * Parses user input into command and arguments (if any) (command arg1 arg2 arg3....)
     *
     * @param input The user's input string.
     * @return String[] array that denotes the tokenized string.
     */
    public String[] parse(String input) throws NephilimIOMissingArgsException {
        String[] initial = input.split(" ");
        int searchResult = commands.indexOf(initial[0]);
        if (searchResult == -1) {
            throw new NephilimIOMissingArgsException(initial[0], "Command not recognised");
        } else {
            ArrayList<String> flagSet = flags.get(searchResult);
            ArrayList<String> output = new ArrayList<>();
            String[] evalString = {"", input}; //Setup for the loop ahead
            for (int i = 0; i < flagSet.size(); i++) {
                evalString = evalString[1].split(flagSet.get(i), 2);  //split remaining string with relevant flag
                if (evalString.length == 1) {
                    throw new NephilimIOMissingArgsException(input,
                            "A flag is missing or a field is empty. Possible missing flag: " + flagSet.get(i));
                }
                output.add(evalString[0]); //then add the first token to output
            }
            output.add(evalString[1]);
            //Lookup relevant instruction and pass the arguments array to it. Then return the output
            return output.toArray(evalString);
        }
    }

    /**
     * Parses user input into tokens and calls relevant logic handling function
     * based on command token.
     *
     * @param input The user's input string.
     * @return Boolean that tells Nephilim whether to break the command searching loop
     * or not.
     */
    public boolean parseAndRun(String input) throws NephilimException, IOException {
        String[] initial = input.split(" ");
        int searchResult = commands.indexOf(initial[0]);
        if (searchResult == -1) {
            throw new NephilimIOMissingArgsException(initial[0], "Command not recognised");
        } else {
            ArrayList<String> flagSet = flags.get(searchResult);
            ArrayList<String> output = new ArrayList<>();
            String[] evalString = {"", input}; //Setup for the loop ahead
            for (int i = 0; i < flagSet.size(); i++) {
                evalString = evalString[1].split(flagSet.get(i), 2);  //split remaining string with relevant flag
                if (evalString.length == 1) {
                    throw new NephilimIOMissingArgsException(input,
                            "A flag is missing or a field is empty. Possible missing flag: " + flagSet.get(i));
                }
                output.add(evalString[0]); //then add the first token to output
            }
            output.add(evalString[1]);
            //Lookup relevant instruction and pass the arguments array to it. Then return the output
            return instructions.get(searchResult).run(output.toArray(evalString));
        }
    }

}
