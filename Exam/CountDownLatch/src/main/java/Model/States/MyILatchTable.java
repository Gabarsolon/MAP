package Model.States;

public interface MyILatchTable {
    public Integer newEntrance(Integer val);
    public Integer lookup(Integer key);
    public void update(Integer key, Integer value);
}
