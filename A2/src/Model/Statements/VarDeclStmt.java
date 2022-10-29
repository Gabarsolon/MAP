package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.PrgState;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{
    private String name;
    private Type typ;

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new MyException("Variable is already declared");
        if(typ.toString() == "bool")
            symTable.update(name, new BoolValue(false));
        else
            symTable.update(name, new IntValue(0));
        return state;
    }
}
