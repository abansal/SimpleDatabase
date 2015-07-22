import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Created by abansal on 7/21/15.
 */
public class SimpleDatabase {
    public static void main(String[] args) {

        Database db = new Database();
        Scanner in = new Scanner(System.in);
        Command command = new Command("INITIAL"); //Initiating a dummy command

        // Run an infinite loop till
        while (!command.isEnd()){
            try {
                if (!command.isTransactionStart()) {
                    db.executeCommand(command);
                } else {
                    LinkedList<Command> transaction = new LinkedList<Command>();
                    int open_transactions = 1;
                    //Build a list and then call transaction
                    transaction.add(command);
                    //System.out.println("Starting Transaction");
                    while (open_transactions > 0) {
                        command = new Command(in.nextLine());
                        transaction.add(command);
                        if (command.isTransactionEnd()) open_transactions--;
                        else if (command.isTransactionStart()) open_transactions++;
                    }

                    db.transaction(transaction);
                }
                command = new Command(in.nextLine());
            } catch (IllegalArgumentException ie){
                System.out.println("ILLEGAL INPUT/ UNSUPPORTED COMMAND");
            } catch (NoSuchElementException ne){
                command = new Command("INITIAL");
            }
        }
    }

}
