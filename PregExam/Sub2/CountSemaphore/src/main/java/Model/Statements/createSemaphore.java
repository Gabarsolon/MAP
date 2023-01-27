package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.States.MyISemaphoreTable;
import Model.States.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
public class createSemaphore implements IStmt{
    private String var;
    private Exp exp1;

    public createSemaphore(String var, Exp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heapTbl = state.getHeapTable();
        MyISemaphoreTable semTable = state.getSemTable();

        Value number1 = exp1.eval(symTbl, heapTbl);
        if(!number1.getType().equals(new IntType()))
            throw new MyException("The expression must evaluate to an integer");
        Integer newfreelocation = semTable.newEntry(new Pair<>(((IntValue)number1).getVal(), new ArrayList<Integer>()));

        if(symTbl.isDefined(var)){
            if(symTbl.lookup(var).getType().equals(new IntType()))
                symTbl.update(var, new IntValue(newfreelocation));
            else
                throw new MyException("The type of the variable isn't int");
        }
        else
            throw new MyException("The varaible isn't defined in the symTable");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new createSemaphore(var, exp1.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(var);
        Type typExp = exp1.typecheck(typeEnv);
        if(!typVar.equals(new IntType()))
            throw new MyException("createSemaphore: The type of the variable isn't int");
        if(!typExp.equals(new IntType()))
            throw new MyException("createSemaphore: The expression doesn't evaluate to int");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "createSemaphore{" +
                "var='" + var + '\'' +
                ", exp1=" + exp1 +
                '}';
    }
}
