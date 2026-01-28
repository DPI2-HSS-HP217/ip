import java.util.*;

public class Nephilim {

    private static void print(String out) {
        String LINE_BREAK = "____________________________________________________________";
        System.out.println(LINE_BREAK);
        System.out.println(out);
        System.out.println(LINE_BREAK);
    }


    public static void main(String[] args) throws NephilimException {
        enum COMMAND {
            list, delete, unmark, mark, todo, event, deadline
        }

        //Greeting
        Nephilim.print("Greetings from Nephilim-451.\nPlease state the nature of your request.");


        //Setup Variables
        ArrayList<Task> tasks = new ArrayList<>(100);
        int taskCount = 0;

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
                        if (arg < 1 || arg > taskCount) {
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
                                taskCount--;
                                tasks.remove(arg - 1);
                            }
                            outputMessage.append(outString + '\n' + tasc);
                            if (command.equals("delete")) {
                                outputMessage.append('\n' + taskCount + " tasks remain.");
                            }
                        }
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "todo":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Todo(input.nextToken("")));
                        outputMessage.append("Added: " + tasks.get(taskCount));
                        taskCount++;
                        outputMessage.append('\n' + "Current task count: " + taskCount);
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "deadline":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Deadline(input.nextToken("")));
                        outputMessage.append("Added: " + tasks.get(taskCount));
                        taskCount++;
                        outputMessage.append('\n' + "Current task count: " + taskCount);
                        Nephilim.print(outputMessage.toString());
                        outputMessage.delete(0, outputMessage.length()); //flush output
                        break;
                    case "event":
                        if (!input.hasMoreTokens()) throw new NephilimInputException(command,
                                "Task description cannot be blank.");
                        tasks.add(new Event(input.nextToken("")));
                        outputMessage.append("Added: " + tasks.get(taskCount));
                        taskCount++;
                        outputMessage.append('\n' + "Current task count: " + taskCount);
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
        Nephilim.print("So concludes our conversation.");
    }
}
