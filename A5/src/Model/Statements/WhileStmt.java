package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt){
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIHeap<Integer, Value> heapTbl = state.getHeapTable();

        Value cond = exp.eval(symTbl, heapTbl);
        if(!cond.getType().equals(new BoolType()))
            throw new MyException("The expression must be a bool");
        if(((BoolValue)cond).getVal()) {
            exeStack.push(this);
            exeStack.push(stmt);
        }
        return state;
    }

    @Override
    public String toString() {
        return "WhileStmt{" +
                "exp=" + exp +
                ", stmt=" + stmt +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }
}
