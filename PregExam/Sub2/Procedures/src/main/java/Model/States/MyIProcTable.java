package Model.States;

import Model.Statements.IStmt;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface MyIProcTable {
    public Pair<List<String>, IStmt> lookup(String key);
    public void update(String key, Pair<List<String>, IStmt> val);
    public Map<String, Pair<List<String>, IStmt>> getData();
}
