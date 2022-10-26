package Model.States;

import Model.Types.Type;
import Model.Values.Value;

public interface MyIDictionary <T1,T2>{
    public boolean isDefined(T1 key);
    public Value lookup(T1 key);
    public void update(T1 key, T2 value);
}
