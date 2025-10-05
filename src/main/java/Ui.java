import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String logo = " ____                   \n" +
            "|  _ \\  ___   __ _  ___ \n" +
            "| | | |/ _ \\ / _` |/ _ \\\n" +
            "| |_| | (_) | (_| |  __/\n" +
            "|____/ \\___/ \\__, |\\___|\n" +
            "             |___/      ";

    private static void printLine(){
        System.out.println(LINE);
    }

    public void printWelcome(){
        System.out.println("Hello from\n" + logo);
        printLine();
        System.out.println("Hello! Am Doge");
        System.out.println("What can I do for you?");
        printLine();
    }

    public void showBye(){
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    public void showError(String message){
        printLine();
        System.out.println(message);
        printLine();
    }

    public void showLoadingError(String message){
        printLine();
        System.out.println("Error loading tasks: " + message);
        printLine();
    }

    public void showTaskList(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i).listTasks());
        }
        printLine();
    }

    public void showNewTaskSummary(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(taskList.get(taskList.size() - 1).listTasks());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLine();
    }

    public void showMarkedTask(Task task, boolean isMarked) {
        printLine();
        if (isMarked){
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(task.listTasks());
        printLine();
    }

    public void showDeletedTask(Task task, ArrayList<Task> taskList) {
        printLine();
        System.out.println("Aight. Task deletus:");
        System.out.println(task.listTasks());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLine();
    }

    public String readCommand (Scanner scanner){
        return scanner.nextLine();
    }

}
