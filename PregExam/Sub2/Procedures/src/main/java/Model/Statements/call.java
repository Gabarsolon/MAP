package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.States.*;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class call implements IStmt{
    String fname;
    List<Exp> parameters;

    public call(String fname, List<Exp> parameters) {
        this.fname = fname;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "call{" +
                "fname='" + fname + '\'' +
                ", parameters=" + parameters +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIProcTable procTable = state.getProcTable();
        MyIDictionary<String, Value> symTbl = state.getSymTableStack().top();
        MyIHeap<Integer, Value> heapTbl = state.getHeapTable();
        MyIStack<MyIDictionary<String,Value>> symTblStack = state.getSymTableStack();
        MyIStack<IStmt> exeStack = state.getExeStack();

        Pair<List<String>, IStmt> procValBody = procTable.lookup(fname);
        if(procValBody==null)
            throw new MyException("The procedure doesn't exist");
        List<String> values = procValBody.getKey();
        List<Value> expEvaluations = parameters.stream().map(e->
                        {
                            try{
                                return e.eval(symTbl, heapTbl);
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            return new IntValue(0);
                        })
                .collect(Collectors.toList());
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for(int i=0;i<values.size();i++)
            newSymTable.update(values.get(i), expEvaluations.get(i));
        System.out.println(newSymTable);
        symTblStack.push(newSymTable);
        exeStack.push(new returnProc());
        exeStack.push(procValBody.getValue());
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new call(fname, parameters.stream().map(e->e.deepCopy()).collect(Collectors.toList()));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
