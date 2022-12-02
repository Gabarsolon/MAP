package Repository;

import Model.Exceptions.MyException;
import Model.States.PrgState;

public interface IRepository {
    public PrgState getCrtPrg();
    public void setCrtPrg(PrgState prg);
    public void logPrgStateExec() throws MyException;
}
