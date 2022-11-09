package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.MyList;
import Model.States.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class openRFile implements IStmt{
    Exp exp;
    public openRFile(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        Value value =exp.eval(symtbl);
        String fileString = ((StringValue) value).getVal();
        if(!value.getType().equals(new StringType())){
            throw new MyException("The value must be of string type");
        }
        if(fileTable.isDefined(fileString)){
            throw new MyException("The file already exists in the filetable");
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(((StringValue) value).getVal()));
            fileTable.update(fileString, br);
        }catch(Exception e) {
            System.out.println(e.toString());
        }finally {
            return state;
        }
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(exp.deepCopy());
    }
}
