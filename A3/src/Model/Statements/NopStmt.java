package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.PrgState;

public class NopStmt implements IStmt{

    @Override
    public String toString() {
        return "NopStmt{}";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }
    public NopStmt deepCopy(){
        return new NopStmt();
    }
}
