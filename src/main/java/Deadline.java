public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String listTasks() {
        return "[D]" + super.listTasks() + " (by: " + by + ")";
    }
}