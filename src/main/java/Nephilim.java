import java.util.*;

public class Nephilim {

    public static void main(String[] args) {
        /* String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n"; */
        String linebrk = "____________________________________________________________";
        System.out.println(linebrk);
        System.out.println("Greetings from Nephilim-451.");
        System.out.println("Please state the nature of your request.");
        System.out.println(linebrk);

        Scanner scn = new Scanner(System.in);
        String command = scn.nextLine();
        while (!(command.equals("bye"))) {
            System.out.println(linebrk + '\n' + command + '\n' + linebrk);
            command = scn.nextLine();
        }
        command = "So concludes our conversation.";
        System.out.println(linebrk + '\n' + command + '\n' + linebrk);
    }
}
