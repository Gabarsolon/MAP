package Repository;

import Model.States.MyList;
import Model.States.PrgState;

public class Repository implements IRepository{
    private PrgState crtPrg;
    public Repository(PrgState prg){
        this.crtPrg = prg;
    }
    public PrgState getCrtPrg(){
        return crtPrg;
    }
}
