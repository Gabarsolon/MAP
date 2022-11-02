package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyIDictionary;
import Model.Values.Value;

public class VarExp implements Exp{
    String id;
    public VarExp(String id){
        this.id = id;
    }
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value val = tbl.lookup(id);
        if(val == null)
            throw new MyException("The variable wasn't declared before");
        return val;
    }
    public VarExp deepCopy(){
        return new VarExp(id);
    }
}
