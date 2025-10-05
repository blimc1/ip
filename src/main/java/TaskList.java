import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public void toggleStatus(int index, boolean isDone) {
        if (isDone){
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
    }

    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }
}
