package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.PrgState;
import Model.Types.Type;

public class VarDeclStmt implements IStmt{
    private String name;
    private Type typ;

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }
}
