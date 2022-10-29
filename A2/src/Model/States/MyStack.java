package Model.States;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> data;

    @Override
    public void push(T v) {
        data.push(v);
    }

    @Override
    public T pop() {
        return data.pop();
    }
}
