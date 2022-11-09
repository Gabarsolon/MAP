package Model.States;

import Model.Statements.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;

    private IStmt originalProgram;

    private MyIDictionary<String, BufferedReader> fileTable;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,
                    MyIDictionary<String, BufferedReader>ft, IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out=ot;
        fileTable=ft;
        originalProgram=prg.deepCopy();
        stk.push(prg);
    }
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }
    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "PrgState{" +
                "exeStack=" + exeStack +
                "\nsymTable=" + symTable +
                "\nout=" + out +
//                ", originalProgram=" + originalProgram +
                "\nfileTable=" + fileTable+
                '}';
    }

}
