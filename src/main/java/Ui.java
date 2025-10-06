import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interface interactions for the Doge application.
 * Responsible for printing messages, errors, and task lists updates.
 */
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

    public void showWelcome(){
        System.out.println("Hello from\n" + logo);
        printLine();
        System.out.println("Hello! Am Doge");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showBye(){
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message){
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Displays a loading error message if tasks are unable to load.
     *
     * @param message Loading error message.
     */
    public void showLoadingError(String message){
        printLine();
        System.out.println("Error loading tasks: " + message);
        printLine();
    }

    /**
     * Displays a numbered list of all tasks in the list.
     *
     * @param taskList List of tasks to display.
     */
    public void showTaskList(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i).listTasks());
        }
        printLine();
    }

    /**
     * Displays a summary after adding new tasks, including task details and total task count.
     *
     * @param taskList The updated list of tasks.
     */
    public void showNewTaskSummary(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(taskList.get(taskList.size() - 1).listTasks());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Displays a message after marking or unmarking a task.
     *
     * @param task The marked/unmarked task.
     * @param isMarked True if marked as done, false if unmarked.
     */
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

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The deleted task.
     * @param taskList The updated list of tasks.
     */
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

    /**
     * Displays a matching tasks from searching.
     *
     * @param matches The list of matching tasks.
     */
    public void showMatchingTasks (ArrayList<Task> matches){
        printLine();
        if (matches.isEmpty()){
            System.out.println("BONK! No matching tasks found.");
        } else {
            System.out.println("Matching tasks found:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + "." + matches.get(i).listTasks());
            }
        }
        printLine();
    }

}
