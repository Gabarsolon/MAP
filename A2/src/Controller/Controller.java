package Controller;

import Model.Exceptions.MyException;
import Model.Statements.IStmt;
import Model.States.MyIStack;
import Model.States.PrgState;
import Repository.*;

public class Controller implements IController{
    private IRepository repository;
    private boolean displayPrgState;
    public Controller(IRepository repo){
        this.repository = repo;
        this.displayPrgState = false;
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
        if(displayPrgState){
            while(!prg.getExeStack().isEmpty()){
                oneStep(prg);
                System.out.println(prg);
            }
        }
        else{
            while(!prg.getExeStack().isEmpty()){
                oneStep(prg);
            }
        }
    }
    public void setDisplayPrgState(boolean val){
        this.displayPrgState = val;
    }
}
