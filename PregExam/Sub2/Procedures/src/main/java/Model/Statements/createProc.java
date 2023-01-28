package Model.Statements;

import Model.Exceptions.MyException;
import Model.States.MyDictionary;
import Model.States.MyIDictionary;
import Model.States.MyIProcTable;
import Model.States.PrgState;
import Model.Types.Type;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class createProc implements IStmt{
    String name;
    List<String> variablesList;
    IStmt body;

    public createProc(String name, List<String> variablesList, IStmt body) {
        this.name = name;
        this.variablesList = variablesList;
        this.body = body;
    }

    @Override
    public String toString() {
        return "createProc{" +
                "name='" + name + '\'' +
                ", variablesList=" + variablesList +
                ", body=" + body +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIProcTable procTable = state.getProcTable();

        if(procTable.lookup(name)!=null)
            throw new MyException("There's already a procedure with the same name");

        procTable.update(name, new Pair<>(variablesList, body));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        List<String> cloneVariablesList = variablesList.stream().collect(Collectors.toList());
        return new createProc(name, cloneVariablesList, body.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
//        MyIDictionary<String,Type> typeEnvClone = new MyDictionary<>();
//        typeEnvClone.setData(typeEnv.getData().entrySet().stream()
//                .collect(Collectors.toMap(e->e.getKey(), e->e.getValue().deepCopy())));
//        body.typecheck(typeEnvClone);
        return typeEnv;
    }
}
