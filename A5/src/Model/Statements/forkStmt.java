package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.*;
import Model.Values.Value;

import java.io.BufferedReader;

public class forkStmt implements IStmt{
    IStmt stmt;



    public forkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symTbl = state.getSymTable().deepCopy();
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIHeap<Integer, Value> heapTbl= state.getHeapTable();
        PrgState.setAvailableId(PrgState.getAvailableId()+1);

        return new PrgState(exeStack,symTbl,out,fileTbl,heapTbl,stmt);
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
}
