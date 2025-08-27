import java.util.Scanner;

public class Doge {
    public static void main(String[] args) {
        String logo = " ____                   \n" +
                "|  _ \\  ___   __ _  ___ \n" +
                "| | | |/ _ \\ / _` |/ _ \\\n" +
                "| |_| | (_) | (_| |  __/\n" +
                "|____/ \\___/ \\__, |\\___|\n" +
                "             |___/      ";
        System.out.println("Hello from\n" + logo);
        System.out.println("____________________________________________________________\n" +
                " Hello! Am Doge\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n");

        //creates a scanner object to read the input
        Scanner scanner = new Scanner(System.in);

        while(true) {
            //reads input
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");
                break;
            } else {
                System.out.println("____________________________________________________________\n" +
                        input + "\n" +
                        "____________________________________________________________\n");
            }
        }

        scanner.close();
    }

}
