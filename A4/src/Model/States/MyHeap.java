package Model.States;

import java.util.HashMap;

public class MyHeap<K, V> implements MyIHeap<K, V>{
    private HashMap<Integer, V> data;
    private Integer freePos;
    public MyHeap(){
        data = new HashMap<Integer,V>();
        freePos = 1;
    }
    public Integer newEntry(V val){
        data.put(freePos, val);
        freePos++;
        return freePos-1;
    }
}
