package Model;

public class Sphere implements IObject{
    private Integer volume;
    public Sphere(Integer volume){
        this.volume = volume;
    }
    public String toString(){
        return "Sphere, volume=" + this.volume;
    }
    public boolean solve(Integer volume){
        return this.volume > volume;
    }
}
