package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Values.Value;

public interface Exp {
    public Value eval(MyIDictionary<String,Value> tbl) throws MyException;
}
