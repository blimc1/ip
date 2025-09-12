public class UnknownCommandException extends DogeException{
    public UnknownCommandException(){
        super("Much confused. I don't know what that means. Try other commands like: 'list', 'todo', 'deadline'");
    }
}
