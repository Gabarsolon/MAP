package Model.States;

import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface MyISemaphoreTable {
    public Integer newEntry(Pair<Integer, List<Integer>> value);
    static Lock lock = new ReentrantLock();
    public Pair<Integer, List<Integer>> lookup(Integer key);
    public void update(Integer key, Pair<Integer, List<Integer>> value);
}
