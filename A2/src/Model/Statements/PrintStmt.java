package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIList;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    private Exp exp;
    public PrintStmt(Exp exp){
        this.exp = exp;
    }
    public String toString(){
        return "print(" + exp.toString()+ ")";
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        out.add(exp.eval(symTable));
        return state;
    }
    public PrintStmt deepCopy(){
        return new PrintStmt(exp);
    }
}
