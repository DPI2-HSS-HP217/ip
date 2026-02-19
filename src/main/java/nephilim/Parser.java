package nephilim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser class parses string inputs and returns them.
 */

class Parser {

    /* Array List of all available commands. To potentially transfer into
    * a separate class with HashMap for faster lookup in the future */
    private final ArrayList<String> commands;
    /* Array List of the flags with which to tokenize string input. 1:1 index
    * matching with the commands ArrayList above*/
    private final ArrayList<ArrayList<String>> flags;


    /**
     * Constructor that will set up all arraylists with default commands.
     * @param commands List of commands to parse from Nephilim
     * @param flags List of argument flags corresponding to the commands list
     */
    public Parser(ArrayList<String> commands, ArrayList<ArrayList<String>> flags) {
       this.commands = commands;
       this.flags = flags;
    }

    /**
     * Parses user input into command and arguments (if any) (command arg1 arg2 arg3....)
     * Then possibly executes instruction set related to command depending on isExecute parameter.
     *
     * @param input The user's input string.
     * @return String[] array that denotes the tokenized string.
     */
    public String[] parse(String input) throws NephilimException, IOException {
        String[] initial = input.split(" ");
        int searchResult = commands.indexOf(initial[0]);
        if (searchResult == -1) {
            throw new NephilimIOMissingArgsException(initial[0], "Command not recognised");
        }
        ArrayList<String> flagSet = flags.get(searchResult);
        ArrayList<String> output = new ArrayList<>();
        String[] evalString = {"", input}; //Setup for the parsing loop ahead, which operates on element in index 1

        for (int i = 0; i < flagSet.size(); i++) {
            evalString = evalString[1].split(flagSet.get(i), 2);
            if (evalString.length == 1) { //Length of 1 indicates that string could not be split according to flag: means flag is missing.
                throw new NephilimIOMissingArgsException(input,
                        "A flag is missing. Possible missing flag: " + flagSet.get(i));
            } else if (evalString[1].trim().isEmpty()) {
                throw new NephilimIOMissingArgsException(input,
                        "A field is empty. This is not allowed.");
            }
            output.add(evalString[0]);
        }
        output.add(evalString[1]);

        return output.toArray(evalString);

    }


}
