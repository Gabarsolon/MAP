package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }
    public int getAddress(){
        return address;
    }
    public Type getType(){
        return new RefType(locationType);
    }
    public RefValue deepCopy(){
        return new RefValue(address, locationType);
    }
}
