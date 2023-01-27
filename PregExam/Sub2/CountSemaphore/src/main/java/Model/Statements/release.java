package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.MyISemaphoreTable;
import Model.States.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;
import java.util.List;

public class release implements IStmt{
    String var;

    public release(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyISemaphoreTable semTable = state.getSemTable();

        if(!symTbl.isDefined(var))
            throw new MyException("The variable isn't defined in the symTbl");

        Value foundIndex = symTbl.lookup(var);
        if(!foundIndex.getType().equals(new IntType()))
            throw new MyException("The variable isn't of type int");

        Integer semTableIndex = ((IntValue)foundIndex).getVal();
        Pair<Integer, List<Integer>> entry = semTable.lookup(semTableIndex);
        if(entry==null)
            throw new MyException("The index isn't in the semaphore table");

        List<Integer> List1 = entry.getValue();

        if(List1.contains(state.getPrgId()))
            List1.remove(state.getPrgId());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new release(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(var);
        if(typVar.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("acquire: The variable isn't of type int");
    }

    @Override
    public String toString() {
        return "release{" +
                "var='" + var + '\'' +
                '}';
    }
}
