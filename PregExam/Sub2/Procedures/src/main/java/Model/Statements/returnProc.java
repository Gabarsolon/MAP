package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class returnProc implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<MyIDictionary<String, Value>> symTblStack = state.getSymTableStack();
        symTblStack.pop();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new returnProc();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "returnProc{}";
    }
}
