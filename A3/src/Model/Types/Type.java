package Model.Types;

public interface Type {
    public boolean equals(Object another);
    public String toString();
    public Type deepCopy();
}
