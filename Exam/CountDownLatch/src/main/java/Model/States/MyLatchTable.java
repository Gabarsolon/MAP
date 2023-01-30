package Model.States;

import java.util.HashMap;
import java.util.Map;

public class MyLatchTable implements MyILatchTable{
    private Map<Integer, Integer> data;
    private Integer freePos;
    public MyLatchTable(){
        data = new HashMap<>();
    }
    private void getFreePos(){
        freePos=1;
        while(data.containsKey(freePos))
            freePos++;
    }
    public synchronized Integer newEntrance(Integer val){
        getFreePos();
        data.put(freePos, val);
        return freePos;
    }

    public synchronized Integer lookup(Integer key){
        return data.get(key);
    }

    public synchronized void update(Integer key, Integer value){
        data.put(key, value);
    }
}
