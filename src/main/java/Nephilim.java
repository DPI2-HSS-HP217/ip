import java.util.*;

public class Nephilim {

    public static void main(String[] args) {
        /* String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n"; */
        //Greeting
        String linebrk = "____________________________________________________________";
        System.out.println(linebrk);
        System.out.println("Greetings from Nephilim-451.");
        System.out.println("Please state the nature of your request.");
        System.out.println(linebrk);

        //Setup Variables
        ArrayList<String> tasks = new ArrayList<>(100);

        //Read commands
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();

        while (!(input.equals("bye"))) {
            switch (input) {
                case "list":
                    System.out.println(linebrk);
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(linebrk);
                    break;
                default:
                    tasks.add(input);
                    System.out.println(linebrk + '\n' + "Added: " +
                            input + '\n' + linebrk);
                    break;
            }
            input = scn.nextLine();
        }
        input = "So concludes our conversation.";
        System.out.println(linebrk + '\n' + input + '\n' + linebrk);
    }
}
