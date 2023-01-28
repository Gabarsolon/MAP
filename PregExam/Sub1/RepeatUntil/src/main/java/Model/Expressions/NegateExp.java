package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class NegateExp implements Exp{
    private Exp exp;

    public NegateExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value expVal = exp.eval(tbl, hp);
        return new BoolValue(!((BoolValue)expVal).getVal());
    }

    @Override
    public Exp deepCopy() {
        return new NegateExp(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typecheck(typeEnv);
        if(!typExp.equals(new BoolType()))
            throw new MyException("The expression must evaluate to bool");
        return new BoolType();
    }

    @Override
    public String toString() {
        return "NegateExp{" +
                "exp=" + exp +
                '}';
    }
}
