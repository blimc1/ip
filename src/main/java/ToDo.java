public class ToDo extends Task{
    public ToDo(String description){
        super(description);
    }

    @Override
    public String listTasks(){
        return("[T]" + super.listTasks());
    }
}
