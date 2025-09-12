public class EmptyDescriptionException extends DogeException{
    public EmptyDescriptionException(){
        super("BONK!! The description of a task cannot be empty.");
    }
}
