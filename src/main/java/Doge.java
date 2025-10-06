import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for the Doge task management chatbot.
 * Manages the Ui, Storage, Parser, and TaskList components to handle user commands.
 */
public class Doge {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    private static final String DATA_DIR =  "data";
    private static final String DATA_FILE = DATA_DIR + "/doge.txt";

    /**
     * Constructs a new Doge instance.
     * Initialises Ui and Parser, and loads tasks from storage (or creates a new list on error).
     *
     * @param filePath The path to the data file where tasks are stored.
     */
    public Doge(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DogeException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList(new ArrayList<>());
        }
        parser = new Parser();
    }

    /**
     * Runs the main application loop.
     * Displays the welcome message, reads user inputs and parses them, and handles errors.
     */
    public void run(){
        ui.printWelcome();
        Scanner sc = new Scanner(System.in);
        while (true){
            try {
                String input = ui.readCommand(sc);
                if (parser.parse(input, tasks, ui, storage)){
                    ui.showBye();
                    break;
                }
            } catch (DogeException e) {
                ui.showError(e.getMessage());
            }
        }
        sc.close();
    }

    /**
     * Entry point for the application.
     * Creates a Doge instance with the default file path and starts the run loop.
     */
    public static void main(String[] args) {
        new Doge(DATA_FILE).run();
    }

}
