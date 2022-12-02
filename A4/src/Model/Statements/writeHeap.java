package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIHeap;
import Model.States.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

public class writeHeap implements IStmt{
    String var_name;
    Exp exp;
    public writeHeap(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heapTbl = state.getHeapTable();

        Value newVal = exp.eval(symTbl, heapTbl);
        if(!symTbl.isDefined(var_name))
            throw new MyException("The variable isn't defined in the symbol table");

        Value oldVal = symTbl.lookup(var_name);
        Integer address = ((RefValue) oldVal).getAddress();
        if(heapTbl.lookup(address)==null)
            throw new MyException("The variable isn't defined in the heap table");
        if(!oldVal.getType().equals(new RefType(newVal.getType())))
            throw new MyException("The types aren't the same");

        heapTbl.update(address, newVal);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new writeHeap(var_name, exp.deepCopy());
    }
}
