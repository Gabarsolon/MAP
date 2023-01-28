package Model.States;

import Model.Statements.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProcTable implements MyIProcTable{
    private Map<String, Pair<List<String>, IStmt>> data;

    public MyProcTable(){
        this.data = new HashMap<>();
    }
    public Pair<List<String>, IStmt> lookup(String key){
        return data.get(key);
    }

    public void update(String key, Pair<List<String>, IStmt> val){
        data.put(key, val);
    }

    public Map<String, Pair<List<String>, IStmt>> getData(){
        return data;
    }
}
