package Model.States;

import Model.Exceptions.MyException;
import Model.Statements.IStmt;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIStack<MyIDictionary<String, Value>> symTableStack;
    private MyIList<Value> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heapTable;
    private MyIProcTable procTable;
    private IStmt originalProgram;
    private Integer id;
    private static Integer availableId;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTbl, MyIList<Value> ot,
                    MyIDictionary<String, BufferedReader>ft,MyIHeap<Integer, Value>ht, MyIProcTable prcTbl, IStmt prg){
        exeStack=stk;
        out=ot;
        symTableStack = new MyStack<>();
        symTableStack.push(symTbl);
        fileTable=ft;
        heapTable=ht;
        originalProgram=prg.deepCopy();
        procTable=prcTbl;
        id=availableId;
        stk.push(prg);
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public Integer getPrgId() {
        return id;
    }

    public static Integer getAvailableId(){return availableId;}
    public static void setAvailableId(Integer newId) {
        availableId=newId;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

//    public void setSymTable(MyIDictionary<String, Value> symTable) {
//        this.symTable = symTable;
//    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIStack<MyIDictionary<String, Value>> getSymTableStack() {
        return symTableStack;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIHeap<Integer, Value> getHeapTable(){
        return heapTable;
    }

    public MyIProcTable getProcTable() {
        return procTable;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    @Override
    public String toString() {
        return "PrgState{" +
                "id=" + id +
                "\nexeStack=" + exeStack +
                "\nsymTable=" + symTableStack +
                "\nout=" + out +
//                ", originalProgram=" + originalProgram +
                "\nfileTable=" + fileTable+
                "\nheapTable=" + heapTable+
                "\nprocTable=" + procTable+
                "}\n"+
                "---------------------------------------------";
    }

}
