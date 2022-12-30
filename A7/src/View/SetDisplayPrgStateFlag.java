package View;

import Controller.Controller;
import Controller.IController;

import java.util.Scanner;

public class SetDisplayPrgStateFlag extends Command{
    public SetDisplayPrgStateFlag(String key, String desc){
        super(key, desc);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input true or false: ");
        Controller.displayPrgState = scanner.nextBoolean();
    }
}
