package Model;

public class Cylinder implements IObject{
    private Integer volume;
    public Cylinder(Integer volume){
        this.volume = volume;
    }
    public String toString(){
        return "Cylinder, volume=" + this.volume;
    }
    public boolean solve(Integer volume){
        return this.volume > volume;
    }
}
