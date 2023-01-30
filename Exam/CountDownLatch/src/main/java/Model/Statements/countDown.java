package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.MyILatchTable;
import Model.States.MyIList;
import Model.States.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class countDown implements IStmt{
    String var;

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
        MyILatchTable latchTable = state.getLatchTable();
        MyIList<Value> outList = state.getOut();

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
        else if(foundIndexValue>0)
            latchTable.update(foundIndex, foundIndexValue-1);
        else
            outList.add(new IntValue(state.getPrgId()));
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
            throw new MyException("countDown: The variable isn't an integer");
        return typeEnv;
    }
}
