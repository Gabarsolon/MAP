package Model.Expressions;

import Model.Exceptions.MyException;
import Model.States.MyDictionary;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op;

    public ArithExp(int op, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                switch (op){
                    case 1:
                        return new IntValue(n1+n2);
                    case 2:
                        return new IntValue(n1-n2);
                    case 3:
                        return new IntValue(n1*n2);
                    case 4:
                        if(n2==0)
                            throw new MyException("divison by zero");
                        else
                            return new IntValue(n1/n2);
                    default:
                        throw new MyException("Invalid operation");
                }

            }else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }
    public ArithExp deepCopy(){
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
