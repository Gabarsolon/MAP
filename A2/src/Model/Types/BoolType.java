package Model.Types;

public class BoolType implements Type{
    public boolean equals(Object another){
        if(another instanceof BoolType)
            return true;
        return false;
    }
    public String toString(){
        return "bool";
    }
//    public boolean defaultValue(){
//        return false;
//    }
}
