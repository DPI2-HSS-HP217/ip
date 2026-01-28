import java.util.*;

public class Nephilim {

    public static void main(String[] args) throws NephilimException {
        enum COMMAND {
            list, delete, unmark, mark, todo, event, deadline
        }
        String lineBreak = "____________________________________________________________";
        System.out.println(lineBreak);
        System.out.println("Greetings from Nephilim-451.");
        System.out.println("Please state the nature of your request.");
        System.out.println(lineBreak);

        //Setup Variables
        ArrayList<Task> tasks = new ArrayList<>(100);
        int taskCount = 0;

        //Read commands
        Scanner scn = new Scanner(System.in);
        StringTokenizer input = new StringTokenizer(scn.nextLine(), " ");
        String command = input.nextToken();

        while (!(command.equals("bye"))) {
            try {
                switch (command) {
                    case "list":
                        System.out.println(lineBreak);
                        System.out.println("Your tasks are as follows:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println(lineBreak);
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
                                tasc.resetDone();
                            }
                            else {
                                taskCount--;
                                tasks.remove(arg - 1);
                            }
                            System.out.println(outString + '\n' + tasc);
                            if (command.equals("delete")) {
                                System.out.println(taskCount + " tasks remain.");
                            }
                        }
                        System.out.println(lineBreak);
                        break;
                    case "todo":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Todo(input.nextToken("")));
                        System.out.println(lineBreak + '\n' + "Added: " +
                                tasks.get(taskCount) + '\n' + lineBreak);
                        taskCount++;
                        System.out.println("Current task count: " + taskCount);
                        break;
                    case "deadline":
                        if (!input.hasMoreTokens()) {
                            throw new NephilimInputException(command, "Task description cannot be blank.");
                        }
                        tasks.add(new Deadline(input.nextToken("")));
                        System.out.println(lineBreak + '\n' + "Added: "
                                + tasks.get(taskCount) + '\n' + lineBreak);
                        taskCount++;
                        System.out.println("Current task count: " + taskCount);
                        break;
                    case "event":
                        if (!input.hasMoreTokens()) throw new NephilimInputException(command,
                                "Task description cannot be blank.");
                        tasks.add(new Event(input.nextToken("")));
                        System.out.println(lineBreak + '\n' + "Added: "
                                + tasks.get(taskCount) + '\n' + lineBreak);
                        taskCount++;
                        System.out.println("Current task count: " + taskCount);
                        break;
                    default:
                        throw new NephilimInputException(command, "The command "
                                + command + " does not exist");
                }
                input = new StringTokenizer(scn.nextLine(), " ");
                command = input.nextToken();
            } catch(NephilimException e) {
                System.out.println(lineBreak + '\n' + e.toString() + '\n' + lineBreak);
                input = new StringTokenizer(scn.nextLine(), " ");
                command = input.nextToken();
            }
        }
        //Exit message
        System.out.println(lineBreak + '\n' + "So concludes our conversation." + '\n' + lineBreak);
    }
}
