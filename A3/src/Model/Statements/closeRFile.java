package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.MyIDictionary;
import Model.States.PrgState;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class closeRFile implements IStmt{
    Exp exp;

    public closeRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "closeRFile{" +
                "exp=" + exp +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        StringValue val;

        try{
            val = (StringValue)exp.eval(symtbl);
        }catch(Exception e){
            throw new MyException("The evaluation of the expression is not a string value");
        }

        String fileString = val.getVal();
        if(!fileTable.isDefined(fileString)){
            throw new MyException("The file isn't in the file table");
        }

        BufferedReader bf = fileTable.lookup(fileString);
        try{
            bf.close();
        }catch(Exception e) {
            e.toString();
        }
        fileTable.delete(fileString);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new closeRFile(exp.deepCopy());
    }
}
