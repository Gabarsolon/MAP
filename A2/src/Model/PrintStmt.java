package Model;

public class PrintStmt implements IStmt{
    private Exp exp;

    public String toString(){
        return "print(" + exp.toString()+ ")";
    }
    public PrgState execute(PrgState state) throws MyException{

        return state;
    }
}
