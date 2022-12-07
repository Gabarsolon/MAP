package View;

import Controller.IController;

public class RunExample extends Command{
    private IController ctr;
    RunExample(String key, String desc, IController ctr){
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute(){
        try{
            ctr.allStep();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
