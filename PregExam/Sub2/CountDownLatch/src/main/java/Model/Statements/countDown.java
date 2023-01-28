package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ValueExp;
import Model.States.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class countDown implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public countDown(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "countDown{" +
                "var='" + var + '\'' +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyILatchTable latchTbl = state.getLatchTable();
        MyIList<Value> out = state.getOut();

        if(!symTbl.isDefined(var))
            throw new MyException("The variable isn't defined in the sym table");
        Value varVal = symTbl.lookup(var);
        if(!varVal.getType().equals(new IntType()))
            throw new MyException("The variable isn't of type int");

        lock.lock();
        Integer foundIndex = ((IntValue)varVal).getVal();
        Integer foundIndexVal = latchTbl.lookup(foundIndex);
        if(foundIndexVal==null)
            throw new MyException("The index isn't present in the latch table");
        else if(foundIndexVal > 0)
            latchTbl.update(foundIndex, foundIndexVal-1);
        out.add(new IntValue(state.getPrgId()));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new countDown(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(var);

        if(!varType.equals(new IntType()))
            throw new MyException("newLatch: The variable isn't of type int");

        return typeEnv;
    }
}
