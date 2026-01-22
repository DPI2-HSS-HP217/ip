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
                    if (arg < 1 || arg > 100 || Objects.isNull(tasks.get(arg - 1))) {
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
                default:
                    input += scn.nextLine();
                    tasks.add(new Task(input));
                    System.out.println(linebrk + '\n' + "Added: " +
                            input + '\n' + linebrk);
                    break;
            }
            input = scn.next();
        }
        input = "So concludes our conversation.";
        System.out.println(linebrk + '\n' + input + '\n' + linebrk);
    }
}
