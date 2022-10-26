package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.PrgState;

public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
}
