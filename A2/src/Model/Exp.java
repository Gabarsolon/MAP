package Model;

public interface Exp {
    public Value eval(MyIDictionary<String,Value>tbl) throws MyException;
}
