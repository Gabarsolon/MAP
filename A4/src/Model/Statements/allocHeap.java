package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.States.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;


public class allocHeap implements IStmt{
    String var_name;
    Exp exp;
    public allocHeap(String var_name, Exp expression){
        this.var_name = var_name;
        this.exp = expression;
    }

    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heapTbl  = state.getHeapTable();

        if(!symTbl.isDefined(var_name))
            throw new MyException("The variable named " + var_name + " isn't defined");

        Value var = symTbl.lookup(var_name);
        Value expEval = exp.eval(symTbl, heapTbl);
        if(!var.getType().equals(new RefType(expEval.getType())))
            throw new MyException("The types are not equal");

        Integer address = heapTbl.newEntry(expEval);
        symTbl.update(var_name, new RefValue(address, expEval.getType()));

        return state;
    }
    public allocHeap deepCopy(){
        return new allocHeap(var_name, exp);
    }
}