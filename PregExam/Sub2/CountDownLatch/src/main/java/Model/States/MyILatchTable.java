package Model.States;

import java.util.Map;

public interface MyILatchTable {
    public Integer newEntry(Integer value);
    public Integer lookup(Integer key);
    public void update(Integer key, Integer value);
    public Map<Integer, Integer> getData();
}
