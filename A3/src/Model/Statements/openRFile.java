package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.StringType;
import Model.Values.Value;

public class openRFile implements IStmt{
    Exp exp;
    public openRFile(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        Value value =exp.eval(symtbl);
        if(!value.getType().equals(new StringType())){

        }
        if(symtbl.lookup(value.))
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(exp.deepCopy());
    }
}
