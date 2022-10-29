package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op;

    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value nr1 = e1.eval(tbl);
        if(nr1.getType() instanceof BoolType) {
            Value nr2 = e2.eval(tbl);
            if(nr2.getType() instanceof BoolType)
            {
                boolean bv1 = ((BoolValue)nr1).getVal();
                boolean bv2 = ((BoolValue)nr2).getVal();
                switch(op) {
                    case 1:
                        return new BoolValue(bv1 && bv2);
                    case 2:
                        return new BoolValue(bv1 || bv2);
                    default:
                        throw new MyException("Invalid operator");
                }
            }
            else
                throw new MyException("Operand 2 is not a boolean");
        }
        else
            throw new MyException("Opperand 1 is not a boolean");
    }
}
