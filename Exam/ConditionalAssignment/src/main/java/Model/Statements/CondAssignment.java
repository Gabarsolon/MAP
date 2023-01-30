package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;

public class CondAssignment implements IStmt{
    private String v;
    private Exp exp1, exp2, exp3;

    public CondAssignment(String v, Exp exp1, Exp exp2, Exp exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public String toString() {
        return "CondAssignment{" +
                "v=" + v +
                "exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", exp3=" + exp3 +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt newStmt = new IfStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
        exeStack.push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignment(v, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp1Type = exp1.typecheck(typeEnv);
        if(!exp1Type.equals(new BoolType()))
            throw new MyException("condassignment: The exp1 doesn't evaluate to bool");
        Type vType = typeEnv.lookup(v);
        Type exp2Type = exp2.typecheck(typeEnv);
        Type exp3Type = exp3.typecheck(typeEnv);
        if(!vType.equals(exp2Type) || !exp2Type.equals(exp3Type))
            throw new MyException("condassignment: The variable v and the expression exp2 and exp3 must have the same type");
        return typeEnv;
    }
}
