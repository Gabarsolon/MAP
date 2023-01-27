package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.MyISemaphoreTable;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.*;
import javafx.util.Pair;

import java.util.List;

public class acquire implements IStmt{
    String var;

    public acquire(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyISemaphoreTable semTable = state.getSemTable();
        MyIStack<IStmt> exeStack = state.getExeStack();

        Value foundIndex;
        if(!symTbl.isDefined(var))
            throw new MyException("The variable isn't an int");

        foundIndex = symTbl.lookup(var);
        if(!foundIndex.getType().equals(new IntType()))
            throw new MyException("The variable isn't defined in the symTable");

        Integer semTblKey = ((IntValue)foundIndex).getVal();
        Pair<Integer, List<Integer>> entry = semTable.lookup(semTblKey);
        if(entry == null)
            throw new MyException("The index isn't in the semaphore table");
        Integer N1 = entry.getKey();
        List<Integer> List1 = entry.getValue();
        Integer NL = List1.size();

        if(N1>NL){
            if(!List1.contains(state.getPrgId()))
                List1.add(state.getPrgId());
        }
        else
            exeStack.push(this);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new acquire(var);
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
        return "acquire{" +
                "var='" + var + '\'' +
                '}';
    }
}
