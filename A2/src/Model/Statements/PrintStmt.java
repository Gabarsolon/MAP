package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.PrgState;

public class PrintStmt implements IStmt{
    private Exp exp;

    public String toString(){
        return "print(" + exp.toString()+ ")";
    }
    public PrgState execute(PrgState state) throws MyException {

        return state;
    }
}
