package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.RelExp;
import Model.States.MyDictionary;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.PrgState;
import Model.Types.Type;
import Model.Expressions.Exp;
import javafx.beans.binding.BooleanExpression;

import java.util.stream.Collectors;

public class SwitchStmt implements IStmt{
    private Exp exp, exp1, exp2;
    private IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3){
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return "SwitchStmt{" +
                "exp=" + exp +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", stmt1=" + stmt1 +
                ", stmt2=" + stmt2 +
                ", stmt3=" + stmt3 +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt newStmt = new IfStmt(new RelExp("==", exp, exp1), stmt1, new IfStmt(new RelExp("==", exp, exp2), stmt2, stmt3));
        exeStack.push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(),
                exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        Type typexp1 = exp.typecheck(typeEnv);
        Type typexp2 = exp.typecheck(typeEnv);
        if(typexp.equals(typexp1) && typexp1.equals(typexp2)){
            MyIDictionary<String, Type> typeEnvClone = new MyDictionary<>();

            typeEnvClone.setData(typeEnv.getData().entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
            stmt1.typecheck(typeEnvClone);

            typeEnvClone.setData(typeEnv.getData().entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
            stmt2.typecheck(typeEnvClone);

            typeEnvClone.setData(typeEnv.getData().entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
            stmt3.typecheck(typeEnvClone);
            return typeEnv;
        }
        else{
            throw new MyException("The expression in the switch statement are not of the same type");
        }
    }
}
