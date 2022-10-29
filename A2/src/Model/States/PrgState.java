package Model.States;

import Model.Statements.IStmt;
import Model.Values.Value;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
//    private IStmt originalProgram;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out=ot;
        //originalProgram=new PrgState(stk, symtbl, ot, null);
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

//    public void setOriginalProgram(IStmt originalProgram) {
//        this.originalProgram = originalProgram;
//    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

//    public IStmt getOriginalProgram() {
//        return originalProgram;
//    }

    @Override
    public String toString() {
        return "PrgState{" +
                "exeStack=" + exeStack +
                ", symTable=" + symTable +
                ", out=" + out +
//                ", originalProgram=" + originalProgram +
                '}';
    }


}
