import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Doge {
    private static final String LINE = "____________________________________________________________";
    private static final String DATA_DIR =  "data";
    private static final String DATA_FILE = DATA_DIR + "/doge.txt";

    public Doge() {

    }

    private static void loadTasks(ArrayList<Task> taskList) throws DogeException {
        try{
            File f = new File(DATA_FILE);
            if(!f.exists()){ //file doesn't exist, start with new list through saveTasks
                return;
            }

            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                Task task = parseTask(sc.nextLine());
                if(task != null){
                    taskList.add(task);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new DogeException("Error: File not found: " + e.getMessage());
        }
    }

    private static Task parseTask(String line) {
        String[] lineParts = line.split(" \\|");
        if(lineParts.length < 3){
            return null; //skip that task
        }
        //different parts of the tasks
        String type = lineParts[0];
        boolean isDone = lineParts[1].equals("1");
        String description = lineParts[2];
        Task task = createTask(type, description, lineParts);
        if (task != null && isDone){
            task.markAsDone();
        }
        return task;
    }

    private static Task createTask(String type, String description, String[] lineParts) {
        switch(type){
        case "T":
            return new ToDo(description);
        case "D":
            if (lineParts.length < 4){
                return null;
            }
            return new Deadline(description, lineParts[3]);
        case "E":
            if  (lineParts.length < 4){
                return null;
            }
            String[] eventTimes = lineParts[3].split(" to ");
            if (eventTimes.length < 2){
                return null;
            }
            return new Event(description, eventTimes[0], eventTimes[1]);
        default:
            return null;
        }
    }

    private static void saveTasks(ArrayList<Task> taskList) throws DogeException {
        try{
            File f = new File(DATA_FILE);
            //checks if the file exists, and creates it if it doesn't exist, else throws an error
            if (f.getParentFile() != null && !f.getParentFile().mkdirs() &&  !f.getParentFile().exists()) {
                throw new  DogeException("Error: Directory not created: " + f.getParentFile().getAbsolutePath());
            }

            FileWriter fw = new FileWriter(DATA_FILE);
            for (Task task : taskList){
                String line;
                String doneStatus = task.isDone ? "1" : "0";
                if (task instanceof ToDo){
                    line = "T | " + doneStatus + " | "+ task.description;
                } else if (task instanceof Deadline dTask){ //to access the "by" field
                    line  = "D | " + doneStatus + " | "+ task.description + " | " + dTask.by;
                } else if (task instanceof Event eTask){
                    line = "E | " + doneStatus + " | "+ task.description + " | " + eTask.from + " to " + eTask.to;
                } else {
                    continue;
                }
                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new DogeException("Error: Could not write to file: " + e.getMessage());
        }
    }

    private static void printLine(){
        System.out.println(LINE);
    }

    private static void printWelcome(){
        String logo = " ____                   \n" +
                "|  _ \\  ___   __ _  ___ \n" +
                "| | | |/ _ \\ / _` |/ _ \\\n" +
                "| |_| | (_) | (_| |  __/\n" +
                "|____/ \\___/ \\__, |\\___|\n" +
                "             |___/      ";
        System.out.println("Hello from\n" + logo);
        printLine();
        System.out.println("Hello! Am Doge");
        System.out.println("What can I do for you?");
        printLine();
    }

    private static void handleBye(){
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    private static void listTasks(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i).listTasks());
        }
        printLine();
    }

    private static void printNewTaskSummary(ArrayList<Task> taskList) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(taskList.get(taskList.size() - 1).listTasks());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLine();
    }

    private static void addTask(String command, String[] inputParts, ArrayList<Task> taskList) throws DogeException {
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        switch (command) {
            case "todo":
                taskList.add(new ToDo(inputParts[1]));
                printNewTaskSummary(taskList);
                break;
            case "deadline":
                String[] deadlineParts = inputParts[1].split(" /by ");
                taskList.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                printNewTaskSummary(taskList);
                break;
            case "event":
                String[] eventParts = inputParts[1].split(" /from ");
                String[] fromToParts = eventParts[1].split(" /to "); //getting the from: and to: parts
                taskList.add(new Event(eventParts[0], fromToParts[0], fromToParts[1]));
                printNewTaskSummary(taskList);
                break;
            default:
                throw new UnknownCommandException();
        }
        saveTasks(taskList);
    }

    private static void toggleTaskStatus(String cmd, String[] inputParts, ArrayList<Task> taskList) throws DogeException {
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        int index = Integer.parseInt(inputParts[1]) - 1;
        printLine();
        if (cmd.equals("mark")){
            taskList.get(index).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            taskList.get(index).markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(taskList.get(index).listTasks());
        printLine();
        saveTasks(taskList);
    }

    private static void deleteTask(String[] inputParts, ArrayList<Task> taskList) throws DogeException {
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        int index = Integer.parseInt(inputParts[1]) - 1;
        printLine();
        System.out.println("Aight. Task deletus:");
        System.out.println(taskList.get(index).listTasks());
        taskList.remove(index);
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLine();
        saveTasks(taskList);
    }

    public static void main(String[] args) {
        printWelcome();
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> taskList = new ArrayList<>();
        try{
            loadTasks(taskList);
        }catch(DogeException e){
            printLine();
            System.out.println(e.getMessage());
            printLine();
        }

        while(true) {
            String input = scanner.nextLine(); //reads input
            String[] inputParts = input.split(" ", 2); //only splits at the first " "
            String command = inputParts[0];

            try {
                switch (command) {
                case "bye":
                    handleBye();
                    scanner.close();
                    return;
                case "list":
                    listTasks(taskList);
                    break;
                case "mark", "unmark":
                    toggleTaskStatus(command, inputParts, taskList);
                    break;
                case "todo", "deadline", "event":
                    addTask(command, inputParts, taskList);
                    break;
                case "delete":
                    deleteTask(inputParts, taskList);
                    break;
                default:
                    throw new UnknownCommandException();
                }
            } catch (DogeException e){
                printLine();
                System.out.println(e.getMessage());
                printLine();
            }
        }
    }

}
