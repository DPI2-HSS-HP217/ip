package nephilim;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the main chatbot class. It comprises a UI, TaskList and Parser.
 */
class Nephilim {
    private Ui outUi;
    private TaskList tasks;
    private Parser parser;
    private Scanner scanner;

    /**
     * Constructs the chatbot by setting up the UI, TaskList and Parser object,
     * Reads the stored data to TaskList using the Storage class.
     */
    public Nephilim() {
        this.outUi = new Ui();
        this.tasks = new TaskList(new ArrayList<>());

        this.scanner = new Scanner(System.in);
        try {
            this.tasks = Storage.readListFromData();
        } catch (FileNotFoundException e) {
            this.outUi.printException("Could not find storage file. Initialising with empty task file.");
        } catch (IOException e) {
            this.outUi.printException("List data file could not be read. Initialising with empty task file.");
        } catch (NephilimException e) {
            this.outUi.printException(e.toString());
        }
        this.parser = new Parser(tasks,outUi);
    }

    /**
     *  Attempts to read and then send user input to parser for parsing and execution.
     *
     * @return True if no major error encountered (keep receiving input), false otherwise
     */
    public boolean receiveInput() {
        try {
            return parser.parseAndRun(scanner.nextLine());
        } catch (NephilimException | IOException e) {
            outUi.printException(e.toString());
            return true;
        }
    }

    /**
     * Causes chatbot to run, constantly accepting new commands until it receives
     * a signal from parser to stop running.
     *
     */
    public void run() {
        outUi.print("Greetings from Nephilim-451.\nPlease state the nature of your request.");
        while (receiveInput()) {
            
        }
        scanner.close();
        outUi.print("So concludes our conversation.");
    }


    public static void main(String[] args) throws NephilimException {
        Nephilim bot = new Nephilim();

        bot.run();


    }
}
