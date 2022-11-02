package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.MyStack;
import Model.States.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    @Override
    public String toString() {
        return "IfStmt{" +
                "exp=" + exp +
                ", thenS=" + thenS +
                ", elseS=" + elseS +
                '}';
    }

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIStack<IStmt> stk = state.getExeStack();
        Value cond = exp.eval(symTable);
        if(!(cond.getType() instanceof BoolType))
            throw new MyException("Conditional expression is not a boolean");
        if(((BoolValue)cond).getVal())
            stk.push(thenS);
        else
            stk.push(elseS);
        return state;
    }
}
