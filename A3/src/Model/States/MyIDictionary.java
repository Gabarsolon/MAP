package Model.States;

public interface MyIDictionary <T1,T2>{
    public boolean isDefined(T1 key);
    public T2 lookup(T1 key);
    public void update(T1 key, T2 value);
//    public void add(T1 key, T2 value);

}
