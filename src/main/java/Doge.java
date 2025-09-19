import java.util.Scanner;

public class Doge {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public Doge() {

    }

    public static void main(String[] args) {
        String logo = " ____                   \n" +
                "|  _ \\  ___   __ _  ___ \n" +
                "| | | |/ _ \\ / _` |/ _ \\\n" +
                "| |_| | (_) | (_| |  __/\n" +
                "|____/ \\___/ \\__, |\\___|\n" +
                "             |___/      ";
        System.out.println("Hello from\n" + logo);
        System.out.println(LINE);
        System.out.println("Hello! Am Doge");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        //creates a scanner object to read the input
        Scanner scanner = new Scanner(System.in);

        //array to store text input by the user
        Task[] taskList = new Task[MAX_TASKS];
        int numberOfTasks = 0;


        while(true) {
            String input = scanner.nextLine(); //reads input
            String[] inputParts = input.split(" ", 2); //only splits at the first " "
            String command = inputParts[0];

            try {
                switch (command) {
                    case "bye":
                        System.out.println(LINE);
                        System.out.println(" Bye. Hope to see you again soon!");
                        System.out.println(LINE);
                        scanner.close();
                        return;
                    case "list":
                        System.out.println(LINE);
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < numberOfTasks; i++) {
                            System.out.println((i + 1) + "." + taskList[i].listTasks());
                        }
                        System.out.println(LINE);
                        break;
                    case "mark":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        int markIndex = Integer.parseInt(inputParts[1]) - 1; //converts string to int
                        taskList[markIndex].markAsDone();
                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(taskList[markIndex].listTasks());
                        System.out.println(LINE);
                        break;
                    case "unmark":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        int unmarkIndex = Integer.parseInt(inputParts[1]) - 1;
                        taskList[unmarkIndex].markAsNotDone();
                        System.out.println(LINE);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(taskList[unmarkIndex].listTasks());
                        System.out.println(LINE);
                        break;
                    case "todo":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        taskList[numberOfTasks] = new ToDo(inputParts[1]);
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList[numberOfTasks].listTasks());
                        numberOfTasks++;
                        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    case "deadline":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        String[] deadlineParts = inputParts[1].split(" /by ");
                        taskList[numberOfTasks] = new Deadline(deadlineParts[0], deadlineParts[1]);
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList[numberOfTasks].listTasks());
                        numberOfTasks++;
                        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    case "event":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        String[] eventParts = inputParts[1].split(" /from ");
                        String[] fromToParts = eventParts[1].split(" /to "); //getting the from: and to: parts
                        taskList[numberOfTasks] = new Event(eventParts[0], fromToParts[0], fromToParts[1]);
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList[numberOfTasks].listTasks());
                        numberOfTasks++;
                        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (DogeException e){
                System.out.println(LINE);
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }

}
