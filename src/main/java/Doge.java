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
        Task[] taskList = new Task[100];
        int numberOfTasks = 0;

        while(true) {
            String input = scanner.nextLine(); //reads input
            Task t = new Task(input);

            if (input.equals("bye")) { //handles exit
                System.out.println("____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________\n" +
                        "Here are the tasks in your list:");
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println((i + 1) + "." + taskList[i].listTasks());
                }
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("mark ")) { //mark as done
                String[] inputParts  = input.split(" ");
                int taskNumber = Integer.parseInt(inputParts[1]) - 1;
                taskList[taskNumber].markAsDone();
                System.out.println("____________________________________________________________\n" +
                        "Nice! I've marked this task as done: \n" +
                        taskList[taskNumber].listTasks() +
                        "\n____________________________________________________________\n");
            } else if (input.startsWith("unmark ")) { //unmark done task
                String[] inputParts  = input.split(" ");
                int taskNumber = Integer.parseInt(inputParts[1]) - 1; //converts string to int
                taskList[taskNumber].markAsNotDone();
                System.out.println("____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet: \n" +
                        taskList[taskNumber].listTasks() +
                        "\n____________________________________________________________\n");
            } else { //echo task
                taskList[numberOfTasks] = t;
                numberOfTasks++;
                System.out.println("____________________________________________________________\n" +
                        "added: " + input +
                        "\n____________________________________________________________\n");
            }
        }

        scanner.close();
    }

}
