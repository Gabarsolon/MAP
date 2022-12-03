package Model.States;


import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    private Map<T1, T2> data;

    public MyDictionary(){
        data = new HashMap<T1,T2>();
    }
    @Override
    public boolean isDefined(T1 key) {
        return data.get(key)!=null;
    }

    @Override
    public T2 lookup(T1 key) {
        return data.get(key);
    }

    public Map<T1, T2> getData() {
        return data;
    }

    @Override
    public void update(T1 key, T2 value) {
        data.put(key,value);
    }

    public String toString(){
        return data.toString();
    }
//    @Override
//    public void add(T1 key, T2 value) {
//        data.put(key,value);
//    }

    public void delete(T1 key){
        data.remove(key);
    }
}
