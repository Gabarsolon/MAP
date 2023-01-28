package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Expressions.NegateExp;
import Model.States.MyDictionary;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;

import java.util.stream.Collectors;

public class repeat implements IStmt{
    private IStmt stmt1;
    private Exp exp2;

    public repeat(IStmt stmt1, Exp exp2) {
        this.stmt1 = stmt1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt newStmt = new CompStmt(stmt1, new WhileStmt(new NegateExp(exp2), stmt1));
        exeStack.push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new repeat(stmt1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp2 = exp2.typecheck(typeEnv);
        if(typeExp2.equals(new BoolType())){
            MyIDictionary<String,Type> typeEnvClone = new MyDictionary<>();
            typeEnvClone.setData(typeEnv.getData().entrySet().stream()
                    .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
            stmt1.typecheck(typeEnvClone);
            return typeEnv;
        }
        else
            throw new MyException("The expression doesn't evaluate to bool");
    }

    @Override
    public String toString() {
        return "repeat{" +
                "stmt1=" + stmt1 +
                ", exp2=" + exp2 +
                '}';
    }
}
