package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    int getAddress(){
        return address;
    }
    Type getType(){
        return new RefType(locationType);
    }
}
