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
                String line = sc.nextLine();
                String[] lineParts = line.split(" \\|");
                if(lineParts.length < 3){
                    continue; //skip that task
                }
                //different parts of the tasks
                String type = lineParts[0];
                boolean isDone = lineParts[1].equals("1");
                String description = lineParts[2];
                Task task;

                switch(type){
                    case "T":
                        task = new ToDo(description);
                        break;
                    case "D":
                        if (lineParts.length < 4){
                            continue;
                        }
                        task = new Deadline(description, lineParts[3]);
                        break;
                    case "E":
                        if  (lineParts.length < 4){
                            continue;
                        }
                        String[] eventTimes = lineParts[3].split(" to ");
                        if (eventTimes.length < 2){
                            continue;
                        }
                        task = new Event(description, eventTimes[0], eventTimes[1]);
                        break;
                    default:
                        continue;
                }
                if(isDone){
                    task.markAsDone();
                }
                taskList.add(task);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new DogeException("Error: File not found: " + e.getMessage());
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
        ArrayList<Task> taskList = new ArrayList<>();
        try{
            loadTasks(taskList);
        }catch(DogeException e){
            System.out.println(LINE);
            System.out.println(e.getMessage());
            System.out.println(LINE);
        }

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
                        for (int i = 0; i < taskList.size(); i++) {
                            System.out.println((i + 1) + "." + taskList.get(i).listTasks());
                        }
                        System.out.println(LINE);
                        break;
                    case "mark":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        int markIndex = Integer.parseInt(inputParts[1]) - 1; //converts string to int
                        taskList.get(markIndex).markAsDone();
                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(taskList.get(markIndex).listTasks());
                        System.out.println(LINE);
                        saveTasks(taskList);
                        break;
                    case "unmark":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        int unmarkIndex = Integer.parseInt(inputParts[1]) - 1;
                        taskList.get(unmarkIndex).markAsNotDone();
                        System.out.println(LINE);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(taskList.get(unmarkIndex).listTasks());
                        System.out.println(LINE);
                        saveTasks(taskList);
                        break;
                    case "todo":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        taskList.add(new ToDo(inputParts[1]));
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList.get(taskList.size() - 1).listTasks());
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        System.out.println(LINE);
                        saveTasks(taskList);
                        break;
                    case "deadline":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        String[] deadlineParts = inputParts[1].split(" /by ");
                        taskList.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList.get(taskList.size() - 1).listTasks());
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        System.out.println(LINE);
                        saveTasks(taskList);
                        break;
                    case "event":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        String[] eventParts = inputParts[1].split(" /from ");
                        String[] fromToParts = eventParts[1].split(" /to "); //getting the from: and to: parts
                        taskList.add(new Event(eventParts[0], fromToParts[0], fromToParts[1]));
                        System.out.println(LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(taskList.get(taskList.size() - 1).listTasks());
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        System.out.println(LINE);
                        saveTasks(taskList);
                        break;
                    case "delete":
                        if (inputParts.length == 1) {
                            throw new EmptyDescriptionException();
                        }
                        int deleteIndex = Integer.parseInt(inputParts[1]) - 1;
                        System.out.println(LINE);
                        System.out.println("Aight. Task deletus:");
                        System.out.println(taskList.get(deleteIndex).listTasks());
                        taskList.remove(deleteIndex);
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        System.out.println(LINE);
                        saveTasks(taskList);
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
