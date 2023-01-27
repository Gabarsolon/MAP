package Model.States;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySemaphoreTable implements MyISemaphoreTable{
    private Map<Integer, Pair<Integer, List<Integer>>> data;

    private Integer freePos;
    public MySemaphoreTable(){
        this.data = new HashMap<>();
    }

    @Override
    public  Pair<Integer, List<Integer>> lookup(Integer key) {
        MyISemaphoreTable.lock.lock();
        Pair<Integer, List<Integer>> value = data.get(key);
        MyISemaphoreTable.lock.unlock();
        return value;
    }

    @Override
    public  void update(Integer key, Pair<Integer, List<Integer>> value) {
        MyISemaphoreTable.lock.lock();
        data.put(key, value);
        MyISemaphoreTable.lock.unlock();
    }

    @Override
    public Integer newEntry(Pair<Integer, List<Integer>> value) {
        MyISemaphoreTable.lock.lock();
        getFreePos();
        data.put(freePos, value);
        MyISemaphoreTable.lock.unlock();
        return freePos;
    }

    private void getFreePos(){
        this.freePos = 1;
        while(data.containsKey(freePos))
            freePos++;
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MySemaphoreTable{" +
                "data=" + data +
                ", freePos=" + freePos +
                '}';
    }
}
