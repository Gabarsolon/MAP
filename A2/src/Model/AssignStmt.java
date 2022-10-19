package Model;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;

    String toString(){
        return id + "=" + exp.toString();
    }
    PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String,Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)){
            Value val = exp.eval(symTbl);
            Type typId = (symTbl.lookup(id)).getType();
            if(val.getType().equals(typId))
                symTbl.update(id,val);
            else throw new MyException("declared type of variable" + id + " and type of " +
                        "the asigned expression do not match");
        }
        else throw new MyException("the used variable" + id + " was not declared before");
        return state;
    }

}
