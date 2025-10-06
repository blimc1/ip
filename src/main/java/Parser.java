import java.util.ArrayList;

/**
 * Parses user input and assigns them to the appropriate handlers.
 * Supports "bye", "list", "mark", "unmark", "todo", "deadline", "event", "delete", and "find" commands.
 * Throws exceptions for invalid inputs.
 */
public class Parser {
    /**
     * Parses user commands and executes it using the appropriate method.
     *
     * @param input The raw user input string.
     * @param tasks The task list to operate on.
     * @param ui The UI to display appropriate messages.
     * @param storage The storage for saving changes.
     * @return true if user enters "bye" to exit, false otherwise.
     * @throws DogeException If there is parsing or execution errors.
     */
    public boolean parse(String input, TaskList tasks, Ui ui, Storage storage)  throws DogeException{
        if (input.trim().isEmpty()) {
            throw new UnknownCommandException();
        }
        String[] inputParts = input.split(" ", 2); //only splits at the first " "
        String command = inputParts[0];
        switch (command) {
            case "bye":
                return true;
            case "list":
                ui.showTaskList(tasks.getTasks());
                return false;
            case "mark", "unmark":
                toggleTaskStatus(command, inputParts, tasks, ui, storage);
                return false;
            case "todo", "deadline", "event":
                addTask(command, inputParts, tasks, ui, storage);
                return false;
            case "delete":
                deleteTask(inputParts, tasks, ui, storage);
                return false;
            case "find":
                handleFind(inputParts, tasks, ui);
                return false;
            default:
                throw new UnknownCommandException();
        }
    }

    private void handleFind (String[] inputParts, TaskList tasks, Ui ui) throws DogeException{
        if (inputParts.length < 2) {
            throw new EmptyDescriptionException();
        }
        String keyword = inputParts[1];
        ArrayList<Task> matchesFound = tasks.find(keyword);
        ui.showMatchingTasks(matchesFound);
    }

    private void addTask (String command, String[] inputParts, TaskList tasks, Ui ui, Storage storage)
            throws DogeException{
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        Task task = createTask(command, inputParts[1]);
        tasks.add(task);
        ui.showNewTaskSummary(tasks.getTasks());
        storage.save(tasks.getTasks());
    }

    private Task createTask (String command, String details) throws DogeException{
        switch (command) {
        case "todo":
            return new ToDo(details);
        case "deadline":
            String[] deadlineParts = details.split(" /by ");
            if (deadlineParts.length < 2){
                throw new EmptyDescriptionException();
            }
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        case "event":
            String[] eventParts = details.split(" /from ");
            if (eventParts.length < 2){
                throw new EmptyDescriptionException();
            }
            String[] fromToParts = eventParts[1].split(" /to ");
            if (fromToParts.length < 2){
                throw new EmptyDescriptionException();
            }
            return new Event(eventParts[0], fromToParts[0], fromToParts[1]);
        default:
            throw new UnknownCommandException();
        }
    }

    private void toggleTaskStatus(String command, String[] inputParts, TaskList tasks, Ui ui, Storage storage)
            throws DogeException{
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        try {
            int index = Integer.parseInt(inputParts[1]) - 1;
            if (index < 0 || index >= tasks.getTasks().size()) {
                throw new InvalidIndexException();
            }
            boolean isMarked = command.equals("mark");
            tasks.toggleStatus(index, isMarked);
            ui.showMarkedTask(tasks.getTasks().get(index), isMarked);
            storage.save(tasks.getTasks());
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }
    }

    private void deleteTask (String[] inputParts, TaskList tasks, Ui ui, Storage storage) throws DogeException{
        if (inputParts.length == 1) {
            throw new EmptyDescriptionException();
        }
        try {
            int index = Integer.parseInt(inputParts[1]) - 1;
            if (index < 0 || index >= tasks.getTasks().size()) {
                throw new InvalidIndexException();
            }
            Task deletedTask = tasks.getTasks().get(index);
            tasks.delete(index);
            ui.showDeletedTask(deletedTask, tasks.getTasks());
            storage.save(tasks.getTasks());
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Parses each line in the saved file into a Task object.
     * Skips invalid lines.
     *
     * @param line The raw line from the saved file.
     * @return The parsed task, or null if invalid.
     */
    public static Task parseTask(String line){
        String[] lineParts = line.split(" \\| ");
        if(lineParts.length < 3){
            return null;
        }
        String type = lineParts[0];
        boolean isDone = lineParts[1].equals("1");
        String description = lineParts[2];
        Task task = createTaskFromFile(type, description, lineParts);
        if (task != null && isDone){
            task.markAsDone();
        }
        return task;
    }

    private static Task createTaskFromFile(String type, String description, String[] lineParts){
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
}
