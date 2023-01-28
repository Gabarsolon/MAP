package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.States.MyILatchTable;
import Model.States.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLatch implements IStmt{
    private String var;
    private Exp exp;
    private static Lock lock = new ReentrantLock();
    public newLatch(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newLatch{" +
                "var='" + var + '\'' +
                ", exp=" + exp +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heapTbl = state.getHeapTable();
        MyILatchTable latchTbl = state.getLatchTable();

        Value num1 = exp.eval(symTbl, heapTbl);
        if(!num1.getType().equals(new IntType()))
            throw new MyException("The expression doesn't evaluate to an integer");

        lock.lock();
        Integer newfreelocation = latchTbl.newEntry(((IntValue)num1).getVal());
        lock.unlock();

        if(!symTbl.isDefined(var))
            throw new MyException("The variable isn't present in the sym table");
        Value varVal = symTbl.lookup(var);
        if(!varVal.getType().equals(new IntType()))
            throw new MyException("The variable isn't of type int");

        symTbl.update(var, new IntValue(newfreelocation));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newLatch(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(var);
        Type expType = exp.typecheck(typeEnv);

        if(!varType.equals(new IntType()))
            throw new MyException("newLatch: The variable isn't of type int");
        if(!expType.equals(new IntType()))
            throw new MyException("newLatch: The expression doesn't evaluate to int");
        return typeEnv;
    }
}
