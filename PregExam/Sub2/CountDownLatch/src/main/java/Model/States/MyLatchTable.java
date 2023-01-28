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
        freePos = 1;
        while(data.containsKey(freePos))
            freePos++;
    }
    public Integer newEntry(Integer value){
        synchronized (this){
            getFreePos();
            data.put(freePos, value);
            return freePos;
        }
    }

    public Integer lookup(Integer key){
        synchronized (this){
            return data.get(key);
        }
    }

    public void update(Integer key, Integer value){
        synchronized (this){
            data.put(key, value);
        }
    }

    @Override
    public Map<Integer, Integer> getData() {
        return data;
    }
}
