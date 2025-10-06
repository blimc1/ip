import java.util.ArrayList;

/**
 * Manages the list of tasks.
 * Supports add, delete, mark/unmark, list, and find operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a new TaskList.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Retrieves all the tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the back of the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a specified task from the list.
     *
     * @param index The index of the task to delete.
     */
    public void delete(int index) {
        tasks.remove(index);
    }

    /**
     * Toggles the done status of a task at the specified index.
     *
     * @param index The index of the task.
     * @param isDone True if marked as done, false if unmarked.
     */
    public void toggleStatus(int index, boolean isDone) {
        if (isDone){
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
    }

    /**
     * Finds a task containing the inputted keyword.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList of all the tasks containing the keyword.
     */
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
