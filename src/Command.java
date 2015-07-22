import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by abansal on 7/21/15.
 */
public class Command {
    public enum Action {SET, GET, UNSET, NUMEQUALTO, END, BEGIN, COMMIT,ROLLBACK, INITIAL};

    Action action;
    ArrayList<String> args = new ArrayList<String>();

    public Command(String command){
        StringTokenizer line = new StringTokenizer(command.trim(), " ");
        action = Action.valueOf(line.nextToken());
        while (line.hasMoreTokens()) args.add(line.nextToken());
    }

    public Command(Action action, ArrayList<String> args){
        this.action = action;
        this.args = args;
    }

    public Action getAction(){
        return action;
    }

    public ArrayList<String> getArgs(){
        return args;
    }

    public boolean isTransactionStart(){
        return getAction() == Action.BEGIN;
    }

    public boolean isTransactionEnd(){
        return getAction() == Action.COMMIT || getAction() == Action.ROLLBACK;
    }

    public boolean isEnd(){
        return getAction() == Action.END;
    }

    public boolean isRollback(){
        return getAction() == Action.ROLLBACK;
    }

    public boolean changesRecord(){
        return getAction() == Action.SET || getAction() == Action.UNSET;
    }

}
