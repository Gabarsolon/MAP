package Repository;

import Model.States.PrgState;

public interface IRepository {
    public PrgState getCrtPrg();
    public void setCrtPrg(PrgState prg);
}
