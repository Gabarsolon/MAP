package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class await implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public await(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "await{" +
                "var='" + var + '\'' +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyILatchTable latchTable = state.getLatchTable();
        MyIStack<IStmt> exeStack = state.getExeStack();

        if(!symTbl.isDefined(var))
            throw new MyException("The variable isn't defined in the sym table");
        Value varValue = symTbl.lookup(var);
        if(!varValue.getType().equals(new IntType()))
            throw new MyException("The variable doesn't evaluate to an integer");

        lock.lock();
        Integer foundIndex = ((IntValue)varValue).getVal();
        Integer foundIndexValue = latchTable.lookup(foundIndex);
        if(foundIndexValue==null)
            throw new MyException("The found index isn't in the latch table");
        else if(foundIndexValue!=0)
            exeStack.push(this);
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new await(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(var);
        if(!varType.equals(new IntType()))
            throw new MyException("await: The variable isn't an integer");
       return typeEnv;
    }
}
