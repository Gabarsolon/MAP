package Model.States;

import java.util.HashMap;

public class MyHeap<K, V> implements MyIHeap<K, V>{
    private HashMap<Integer, V> data;
    private Integer freePos;
    public MyHeap(){
        data = new HashMap<Integer,V>();
        freePos = 1;
    }

    @Override
    public V lookup(Integer address) {
        return data.get(address);
    }

    @Override
    public void update(Integer address, V val) {
        data.put(address, val);
    }

    public Integer newEntry(V val){
        data.put(freePos, val);
        freePos++;
        return freePos-1;
    }
}
