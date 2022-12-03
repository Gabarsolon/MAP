package Controller;

import Model.Exceptions.MyException;
import Model.Statements.IStmt;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller implements IController{
    private IRepository repository;
    public static boolean displayPrgState;
    public Controller(IRepository repo){
        this.repository = repo;
        this.displayPrgState = true;
    }
    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    public PrgState oneStep(PrgState state) throws MyException{
        MyIStack<IStmt> stk = state.getExeStack();
        if(stk.isEmpty())
            throw new MyException("PrgState stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    public void allStep() throws MyException{
        PrgState prg = repository.getCrtPrg();
        if(prg.getExeStack().isEmpty())
            throw new MyException("PrgState stack is empty");
        repository.logPrgStateExec();
        if(displayPrgState){
            while(!prg.getExeStack().isEmpty()){
                oneStep(prg);
                repository.logPrgStateExec();
                prg.getHeapTable().setData(unsafeGarbageCollector(
                        getAddrFromSymTable(prg.getSymTable().getData().values()),
                        prg.getHeapTable().getData()));
                repository.logPrgStateExec();
                System.out.println(prg);
            }
        }
        else{
            while(!prg.getExeStack().isEmpty()){
                oneStep(prg);
                repository.logPrgStateExec();
                prg.getHeapTable().setData(unsafeGarbageCollector(
                        getAddrFromSymTable(prg.getSymTable().getData().values()),
                        prg.getHeapTable().getData()));
                repository.logPrgStateExec();
            }
        }
    }
//    public void setDisplayPrgState(boolean val){
//        this.displayPrgState = val;
//    }
}
