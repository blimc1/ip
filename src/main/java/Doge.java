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

        //array to store text input by the user
        String[] textList = new String[100];
        int numberOfTexts = 0;

        while(true) {
            String input = scanner.nextLine(); //reads input

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________\n");
                for (int i = 0; i < numberOfTexts; i++) {
                    System.out.println((i + 1) + ". " + textList[i]);
                }
                System.out.println("____________________________________________________________\n");
            } else {
                textList[numberOfTexts] = input;
                numberOfTexts++;
                System.out.println("____________________________________________________________\n" +
                        "added: " + input +
                        "\n____________________________________________________________\n");
            }
        }

        scanner.close();
    }

}
