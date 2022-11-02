package Model.Types;

public class IntType implements Type{
    public boolean equals(Object another){
        if(another instanceof IntType)
            return true;
        else
            return false;
    }
    public String toString() {
        return "int";
    }
//    public int defaultValue(){
//        return 0;
//    }
    public IntType deepCopy(){
        return new IntType();
    }
}
