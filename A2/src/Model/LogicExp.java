package Model;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op;

    public Value eval(MyIDictionary<String,Value> tbl) throws MyException{}
}
