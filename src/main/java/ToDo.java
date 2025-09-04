public class ToDo extends Task{
    public ToDo(String description){
        super(description); //calls on the Task's member for description
    }

    @Override
    public String listTasks(){
        return("[T]" + super.listTasks());
    }
}
