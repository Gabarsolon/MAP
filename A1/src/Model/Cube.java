package Model;

public class Cube implements IObject{
    private Integer volume;
    public Cube(Integer volume){
        this.volume = volume;
    }
    public String toString(){
        return "Cube, volume=" + this.volume;
    }
    public boolean solve(Integer volume){
        return this.volume > volume;
    }
}
