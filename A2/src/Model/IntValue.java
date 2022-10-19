package Model;

public class IntValue implements Value{
    private int val;
    IntValue(int v){
        val=v;
    }

    public int getVal(){
        return val;
    }
    public String toString(){

    }

    public Type getType(){
        return new IntType();
    }
}
