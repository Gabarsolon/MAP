package Controller;

import Model.Exceptions.MyException;
import Model.States.PrgState;

public interface IController {
    public PrgState oneStep(PrgState state) throws MyException;
//    public void setDisplayPrgState(boolean val);
    public void allStep() throws MyException;
}
