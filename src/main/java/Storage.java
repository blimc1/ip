import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles saving and loading tasks, to and from a specified file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a new Storage instance.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath){
        this.filePath = filePath;
    }

    /**
     * Loads all the tasks from the file into an ArrayList.
     * Skips lines stored tasks that are not properly formatted.
     *
     * @return a new ArrayList of the stored tasks.
     * @throws DogeException If the file cannot be read.
     */
    public ArrayList<Task> load() throws DogeException{
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);
        if(!file.exists()){
            return taskList;
        }
        try (Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                Task task = Parser.parseTask(sc.nextLine());
                if(task != null){
                    taskList.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new DogeException("Error: File not found: " + e.getMessage());
        }
        return taskList;
    }

    /**
     * Saves the current task list to file.
     * Creates parent directory if it does not exist.
     *
     * @param taskList The list of tasks to save.
     * @throws DogeException If the file cannot be written to.
     */
    public void save(ArrayList<Task> taskList) throws DogeException{
        File file = new File(filePath);
        if (file.getParentFile() != null && !file.getParentFile().mkdirs() &&  !file.getParentFile().exists()) {
            throw new  DogeException("Error: Directory not created: " + file.getParentFile().getAbsolutePath());
        }

        try (FileWriter Writer = new FileWriter(filePath)){
            for (Task task : taskList){
                String line = formatTask(task);
                if (line != null){
                    Writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e){
            throw new DogeException("Error: Could not write to file: " + e.getMessage());
        }
    }

    private String formatTask(Task task){
        String doneStatus = task.isDone ? "1" : "0";
        if (task instanceof ToDo){
            return "T | " + doneStatus + " | "+ task.description;
        } else if (task instanceof Deadline dTask){ //to access the "by" field
            return "D | " + doneStatus + " | "+ task.description + " | " + dTask.by;
        } else if (task instanceof Event eTask){
            return "E | " + doneStatus + " | "+ task.description + " | " + eTask.from + " to " + eTask.to;
        }
        return null;
    }
}
