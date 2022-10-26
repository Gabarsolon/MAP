package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIStack;
import Model.States.PrgState;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public String toString(){
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return state;
    }

}
