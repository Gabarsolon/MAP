package Model;

public class VarExp implements Exp{
    String id;
    Value eval(MyIDictionary<String, Value> tbl) throws MyException{
        return tbl.lookup(id);
    }
}
