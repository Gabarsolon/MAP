package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.*;
import Model.Types.Type;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class forkStmt implements IStmt{
    IStmt stmt;

    public forkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = new MyStack<>();

        MyIStack<MyIDictionary<String, Value>> reversedSymTblStack = new MyStack<>();
        MyIStack<MyIDictionary<String, Value>> originalSymTblStack = state.getSymTableStack();
        MyIStack<MyIDictionary<String, Value>> cloneSymTblStack = new MyStack<>();

        while(!originalSymTblStack.isEmpty()){
            reversedSymTblStack.push(originalSymTblStack.pop());
        }
        while(!reversedSymTblStack.isEmpty()){
            MyIDictionary<String, Value> symTbl = reversedSymTblStack.pop();
            originalSymTblStack.push(symTbl);
            MyIDictionary<String, Value> cloneSymTbl = new MyDictionary<>();
            cloneSymTbl.setData(symTbl.getData().entrySet().stream()
                    .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
            cloneSymTblStack.push(cloneSymTbl);
        }

        MyIList<Value> out = state.getOut();
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIHeap<Integer, Value> heapTbl= state.getHeapTable();
        PrgState.setAvailableId(PrgState.getAvailableId()+1);

        return new PrgState(exeStack,cloneSymTblStack,out,fileTbl,heapTbl,stmt);
    }

    @Override
    public String toString() {
        return "forkStmt{" +
                "stmt=" + stmt +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String,Type> typeEnvClone = new MyDictionary<>();
        typeEnvClone.setData(typeEnv.getData().entrySet().stream()
                .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
        stmt.typecheck(typeEnvClone);
        return typeEnv;
    }
}
