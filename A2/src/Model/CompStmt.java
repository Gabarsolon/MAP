package Model;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public String toString(){
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return state;
    }

}
