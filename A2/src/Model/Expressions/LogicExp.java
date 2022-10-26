package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Values.Value;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op;

    public Value eval(MyIDictionary<String,Value> tbl) throws MyException {
        Value nr1 = eval();
    }
}
