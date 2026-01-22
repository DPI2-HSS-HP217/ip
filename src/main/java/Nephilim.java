import java.util.*;

public class Nephilim {

    public static void main(String[] args) {
        String linebrk = "____________________________________________________________";
        System.out.println(linebrk);
        System.out.println("Greetings from Nephilim-451.");
        System.out.println("Please state the nature of your request.");
        System.out.println(linebrk);

        //Setup Variables
        ArrayList<Task> tasks = new ArrayList<>(100);
        int taskCount = 0;

        //Read commands
        Scanner scn = new Scanner(System.in);
        String input = scn.next();

        while (!(input.equals("bye"))) {
            switch (input) {
                case "list":
                    System.out.println(linebrk);
                    System.out.println("Your tasks are as follows:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(linebrk);
                    break;
                case "unmark":
                case "mark":
                    int arg = Integer.parseInt(scn.next());
                    if (arg < 1 || arg > taskCount) {
                        System.out.println("No such task exists.");
                    } else {
                        Task tasc = tasks.get(arg - 1);
                        String markString = input.equals("mark") ? "Task successfully marked as done: "
                                                                 : "Task successfully marked as incomplete: ";
                        if (input.equals("mark")) tasc.setDone();
                        else tasc.resetDone();
                        System.out.println(markString + '\n' + tasc);
                    }
                    System.out.println(linebrk);
                    break;
                case "todo":
                    taskCount++;
                    input = scn.nextLine().substring(1);
                    tasks.add(new Todo(input));
                    System.out.println(linebrk + '\n' + "Added: " +
                            input + '\n' + linebrk);
                    System.out.println("Current task count: " + taskCount);
                    break;
                case "deadline":
                    taskCount++;
                    input = scn.nextLine().substring(1);
                    Task in = new Deadline(input);
                    tasks.add(in);
                    System.out.println(linebrk + '\n' + "Added: " +
                             in + '\n' + linebrk);
                    System.out.println("Current task count: " + taskCount);
                    break;
                case "event":
                    taskCount++;
                    input = scn.nextLine().substring(1);
                    Task in2 = new Event(input);
                    tasks.add(in2);
                    System.out.println(linebrk + '\n' + "Added: " +
                            in2 + '\n' + linebrk);
                    System.out.println("Current task count: " + taskCount);
                    break;
                default:
                    taskCount++;
                    input += scn.nextLine();
                    tasks.add(new Task(input));
                    System.out.println(linebrk + '\n' + "Added: " +
                            input + '\n' + linebrk);
                    System.out.println("Current task count: " + taskCount);
                    break;
            }
            input = scn.next();
        }
        input = "So concludes our conversation.";
        System.out.println(linebrk + '\n' + input + '\n' + linebrk);
    }
}
