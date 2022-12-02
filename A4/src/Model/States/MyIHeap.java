package Model.States;

public interface MyIHeap<K, V>{
    public Integer newEntry(V val);
    public V lookup(Integer address);
    public void update(Integer address, V val);
}
