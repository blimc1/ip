import java.util.ArrayList;
import java.util.Scanner;

public class Doge {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    private static final String DATA_DIR =  "data";
    private static final String DATA_FILE = DATA_DIR + "/doge.txt";

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

    public void run(){
        ui.showWelcome();
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

    public static void main(String[] args) {
        new Doge(DATA_FILE).run();
    }

}
