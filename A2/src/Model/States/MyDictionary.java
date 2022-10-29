package Model.States;

import java.util.Dictionary;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    private Dictionary<T1, T2> data;
    @Override
    public boolean isDefined(T1 key) {
        return data.get(key)!=null;
    }

    @Override
    public T2 lookup(T1 key) {
        return data.get(key);
    }

    @Override
    public void update(T1 key, T2 value) {
        data.put(key,value);
    }

//    @Override
//    public void add(T1 key, T2 value) {
//        data.put(key,value);
//    }
}
