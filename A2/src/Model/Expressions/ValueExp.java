package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Values.Value;

public class ValueExp implements Exp{
    private Value e;

    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {return e;}
}
