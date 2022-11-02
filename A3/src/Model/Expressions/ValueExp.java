package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Values.Value;

public class ValueExp implements Exp{
    private Value e;

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e +
                '}';
    }

    public ValueExp(Value e) {
        this.e = e;
    }

    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {return e;}

    public ValueExp deepCopy(){
        return new ValueExp(e.deepCopy());
    }
}
