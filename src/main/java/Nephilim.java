import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


class Nephilim {

    /**
     * Formats and prints a string output message by enclosing it in two
     * horizontal lines.
     * @param out The output string message.
     */
    private static void print(String out) {
        final String LINE_BREAK = "____________________________________________________________";
        System.out.println(LINE_BREAK);
        System.out.println(out);
        System.out.println(LINE_BREAK);
    }

    /**
     * Reads data of task list from file.
     * @return Returns an arraylist representing the list of saved tasks.
     *         If file does not exist, return an empty list.
     */
    private static ArrayList<Task> readListFromData() {
        File directory = new File("./data");
        File data = new File("./data/listDataEncode.txt");
        ArrayList<Task> tasksOut = new ArrayList<>(); //List of tasks to return
        directory.mkdir();
        try {
            if (!data.createNewFile()) {
                int taskCount = 0;
                Scanner reader = new Scanner(data);
                while (reader.hasNextLine()) {
                    String[] input = reader.nextLine().split(" ", 3);
                    taskCount++;
                    switch(input[1]) {
                        case "T" :
                            tasksOut.add(new Todo(input[2]));
                            break;
                        case "D" :
                            tasksOut.add(new Deadline(input[2]));
                            break;
                        case "E" :
                            tasksOut.add(new Event(input[2]));
                            break;
                    }
                    if ("true".equals(input[0])) {
                        tasksOut.get(taskCount - 1).setDone();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            Nephilim.print("File could not be found. This exception should not occur.");
            e.printStackTrace();
            //Note: This should not happen as this should only be thrown when FileReader
            // constructor is run - which in turn should only happen if
            //createNewFile() returns false - e.g the file specified already exists.
        } catch (IOException e) {
            Nephilim.print(e.toString() + "\n List data file could not be read. Restart "
                    + "the chatbot if this occurs.");
        } catch (NephilimInputException e) {
            Nephilim.print(e.toString() + "\n An error occured when importing saved data. "
                    + "Make sure you do not edit the listDataEncode.txt file.");
        }
        return tasksOut;
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
    private static void saveListToData(ArrayList<Task> tasks) {
        File txtData = new File("./data/listDataEncode.txt");
        File data = new File("./data/listData.txt");
        try {
            txtData.createNewFile();
            data.createNewFile();
            FileWriter txtWriter = new FileWriter(data);
            FileWriter encodeWriter = new FileWriter(txtData);
            StringBuilder txtOutputMessage = new StringBuilder();
            StringBuilder encodeOutputMessage = new StringBuilder();

            for (int i = 0; i < tasks.size(); i++) {
                txtOutputMessage.append(tasks.get(i).toString() + '\n');
                System.out.println(txtOutputMessage.toString());
                boolean isDone = tasks.get(i).getIsDone();
                encodeOutputMessage.append(isDone + tasks.get(i).encode() + '\n');
            }
            txtWriter.write(txtOutputMessage.toString());
            encodeWriter.write(encodeOutputMessage.toString());
            txtWriter.close();
            encodeWriter.close();

        } catch (IOException e) {
            Nephilim.print(e.toString() + "\n List data file could not be written to. Restart "
                    + "the chatbot if this occurs.");
        }

    }


    public static void main(String[] args) throws NephilimException {

        //Initialize chatbot
        Nephilim.print("Greetings from Nephilim-451.\nPlease state the nature of your request.");


        //Setup Variables
        ArrayList<Task> tasks = Nephilim.readListFromData();

        //Read commands
        Scanner scn = new Scanner(System.in);
        StringTokenizer input = new StringTokenizer(scn.nextLine(), " ");
        StringBuilder outputMessage = new StringBuilder();
        String command = input.nextToken();

        //Parse commands

        while (!(command.equals("bye"))) {
            try {
                switch (command) {
                    case "list":

                        outputMessage.append("Your tasks are as follows:\n");
                        for (int i = 0; i < tasks.size(); i++) {
                            outputMessage.append((i + 1) + ". " + tasks.get(i) + '\n');
                        }
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output

                        break;
                    case "delete":
                    case "unmark":
                    case "mark":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Please specify which task you wish to " + command);
                        }
                        int arg = Integer.parseInt(input.nextToken());
                        if (arg < 1 || arg > tasks.size()) {
                            System.out.println("No such task exists.");
                        } else {
                            Task tasc = tasks.get(arg - 1);
                            String outString = command.equals("mark") ? "Task successfully marked as done: "
                                    : command.equals("unmark")
                                    ? "Task successfully marked as incomplete: "
                                    : "Deleted task: ";
                            if (command.equals("mark")) {
                                tasc.setDone();
                            }
                            else if (command.equals("unmark")) {
                                tasc.setUnDone();
                            }
                            else {
                                tasks.remove(arg - 1);
                            }
                            outputMessage.append(outString + '\n' + tasc + '\n');
                            if (command.equals("delete")) {
                                outputMessage.append(tasks.size() + " tasks remain.");
                            }
                            Nephilim.saveListToData(tasks);
                        }
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "todo":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Todo(input.nextToken("")));
                        Nephilim.saveListToData(tasks);
                        outputMessage.append("Added: " + tasks.get(tasks.size() - 1));

                        outputMessage.append('\n' + "Current task count: " + tasks.size());
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "deadline":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Deadline(input.nextToken("")));
                        Nephilim.saveListToData(tasks);
                        outputMessage.append("Added: " + tasks.get(tasks.size() - 1));
                        outputMessage.append('\n' + "Current task count: " + tasks.size());
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "event":
                        if (!input.hasMoreTokens()) throw new NephilimInputException(command,
                                "Task description cannot be blank.");
                        tasks.add(new Event(input.nextToken("")));
                        Nephilim.saveListToData(tasks);
                        outputMessage.append("Added: " + tasks.get(tasks.size() - 1));
                        outputMessage.append('\n' + "Current task count: " + tasks.size());
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    default:
                        throw new NephilimInputException(command, "The command "
                                + command + " does not exist");
                }

                input = new StringTokenizer(scn.nextLine(), " ");
                command = input.nextToken();
            } catch(NephilimException e) {
                Nephilim.print(e.toString());
                input = new StringTokenizer(scn.nextLine(), " ");
                command = input.nextToken();
            }
        }
        //Exit message
        scn.close();

        Nephilim.print("So concludes our conversation.");
    }
}
