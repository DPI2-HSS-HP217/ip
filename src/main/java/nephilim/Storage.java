package nephilim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//test for regression before continuing

/**
 * Represents the bridge between hard drive and the application. Allows for
 * saving and reading data from hard drive.
 */
class Storage {
    private static final File directory = new File("./data");
    private static final File data = new File(directory + "/listDataEncode.txt");
    private static final File txtData = new File(directory + "/listData.txt");

    private static TaskList scannerAsTaskList(Scanner reader, Parser parser) throws NephilimException, IOException {
        int taskCount = 0;
        TaskList inList = new TaskList(new ArrayList<>()); //List of tasks to return
        while (reader.hasNextLine()) {
            String[] input = reader.nextLine().split(" ", 2);
            String[] args = parser.parse(input[1]);
            taskCount++;

            switch(args[0]) {
                case "todo" :
                    inList.addTask(new Todo(args[1]));
                    break;
                case "deadline" :
                    inList.addTask(new Deadline(args[1], args[2]));
                    break;
                case "event" :
                    inList.addTask(new Event(args[1], args[2], args[3]));
                    break;
                default:
                    throw new NephilimException("Internal error reading from file. Unable to recognise " + args[0]);
            }
            if ("true".equals(input[0])) {
                inList.markTask(taskCount - 1);
            }
        }
        return inList;
    }

    /**
     * Reads encoded data and translates the plaintext data into
     * an ArrayList of Tasks, which it then returns.
     * Will find a workaround to not require sending a copy of commands and flags.
     *
     * @param commands ArrayList representing list of valid commands.
     * @param flags ArrayList representing list of flags related to valid commands.
     *
     * @return an ArrayList of tasks, read from data file.
     */
    protected static TaskList readListFromData(ArrayList<String> commands, ArrayList<ArrayList<String>>flags) throws FileNotFoundException, IOException, NephilimException {
        directory.mkdir();
        data.createNewFile();

        Scanner reader = new Scanner(data);
        Parser parser = new Parser(commands, flags);

        assert(!commands.isEmpty());
        assert(!flags.isEmpty());

        return scannerAsTaskList(reader, parser);
    }

    /**
     * Saves data of task list to file, both in a more human-readable format
     * (listData.txt) and a more machine-readable format (listDataEncode.txt)
     * Overrides any existing data when saving to file.
     *
     * todo: add handling for file corruption
     *
     * @param tasks The arraylist of tasks to save to memory.
     */
    protected static void saveListToData(TaskList tasks) throws IOException, NephilimException {
        txtData.createNewFile();
        data.createNewFile();

        FileWriter txtWriter = new FileWriter(txtData);
        FileWriter encodeWriter = new FileWriter(data);
        StringBuilder txtOutputMessage = new StringBuilder();
        StringBuilder encodeOutputMessage = new StringBuilder();

        for (int i = 0; i < tasks.getSize(); i++) {
            try {
                txtOutputMessage.append(tasks.getTask(i).toString() + '\n');

                boolean isDone = tasks.getTask(i).getIsDone();
                encodeOutputMessage.append(isDone + tasks.getTask(i).encode() + '\n');
            } catch (NephilimIOMissingArgsException e) {
                throw new NephilimException("Unable to save list to file");
            }
        }
        txtWriter.write(txtOutputMessage.toString());
        encodeWriter.write(encodeOutputMessage.toString());
        txtWriter.close();
        encodeWriter.close();
    }

}
