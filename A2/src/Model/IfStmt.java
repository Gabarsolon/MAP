package Model;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    IfStmt(Exp e, IStmt t, IStmt el){
        exp=e;
        thenS=t;
        elseS=el;
    }
    public PrgState execute(PrgState state) throws MyException{

        return state;
    }
}
