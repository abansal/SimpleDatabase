import java.util.*;
/**
 * Created by Ankur Bansal on 7/21/15.
 */
public class Database {
    private HashMap<String, String> data; //Map to store Key-Value pairs
    private HashMap<String, Integer> valueCount; //Reverse index for values to count

    public Database(){
        data = new HashMap<String, String>();
        valueCount = new HashMap<String, Integer>();
    }

    public void set(String name, String value) {
        String prev = data.put(name, value);
        if(prev != null){
            valueCount.put(prev, valueCount.get(prev)-1);
        }
        if(valueCount.containsKey(value)){
            valueCount.put(value, valueCount.get(value)+1);
        }else{
            valueCount.put(value,1);
        }
    }

    public String get(String name) {
        return data.get(name);
    }

    public int numEqualTo(String value){
        if(valueCount.containsKey(value))
            return valueCount.get(value);
        else return 0;
    }

    public void unset(String name){
        String prev = data.remove(name);
        if(valueCount.get(prev) == 1){
            valueCount.remove(prev);
        }else{
            valueCount.put(prev, valueCount.get(prev)-1);
        }

    }

    // Executes a given command
    public void executeCommand(Command command){
        try {

            switch (command.getAction()) {
                case SET:
                    set(command.getArgs().get(0), command.getArgs().get(1));
                    break;
                case GET:
                    System.out.println(get(command.getArgs().get(0)));
                    break;
                case UNSET:
                    unset(command.getArgs().get(0));
                    break;
                case NUMEQUALTO:
                    System.out.println(numEqualTo(command.getArgs().get(0)));
            }
        }catch (IllegalArgumentException e){
            System.out.println("Invalid input");
        }
    }

    // Method to handle the transactions
    // Makes a recursive call to self for nested transactions
    public void transaction(LinkedList<Command> commands){
        Stack<Command> rollback_commands = new Stack<Command>(); //Store commands for rollback
        commands.pop();//Remove the Begin command

        while(!commands.peek().isTransactionEnd()){
            //Nested Transaction: If its a beginning of a nested transaction, make a recursive call.
            if(commands.peek().isTransactionStart())
                transaction(commands);

            // Execute a command.
            // If its a write/update command add a subsequent command to rollback stack
            if(commands.peek().changesRecord())
                rollback_commands.push(getUndoCommand(commands.peek()));
            executeCommand(commands.pop());
        }

        //Check if last command was ROLLBACK then we need to rollback
        if(commands.get(0).isRollback())
            rollback(rollback_commands);
    }

    //Gives a undo command for a given command
    private Command getUndoCommand(Command command){
        ArrayList<String> args = new ArrayList<String>(2);
        String name = command.getArgs().get(0);
        args.add(name);
        args.add(get(name));
        return new Command(Command.Action.SET, args);
    }

    // Take a stack and run all commands to Rollback a transaction
    public void rollback(Stack<Command> commands){
        while (!commands.isEmpty()) executeCommand(commands.pop());
    }

}