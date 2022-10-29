package Model.States;

import java.util.List;

public class MyList<T> implements MyIList<T>{
    private List<T> data;

    public void add(T elem){
        data.add(elem);
    }
}
