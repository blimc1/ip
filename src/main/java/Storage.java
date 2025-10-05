import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private final String filePath;

    public Storage(String filePath){
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws DogeException{
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);
        if(!file.exists()){ //file doesn't exist, start with new list through saveTasks
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
