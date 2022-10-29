package View;

import Controller.Controller;
import Controller.IController;
import Model.Statements.*;
import Model.States.*;
import Model.Expressions.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),
                                new ArithExp(3,new ValueExp(new IntValue(3)),
                                        new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",
                                        new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(
                                                new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        Scanner scanner = new Scanner(System.in);
        IRepository repository = new Repository(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), ex1));
        IController controller = new Controller(repository);
        while(true) {
            System.out.println("1.Input a program");
            System.out.println("2.Execute the current program");
            System.out.println("3.Enable the displaying of the current program state");
            System.out.println("4.Exit");
            System.out.print("Input a option: ");
            int option = scanner.nextInt();
            switch (option){
                case 1: {
                    System.out.println("Which of the hardcoded program do you want?(input 1 2 or 3");
                    int programOption = scanner.nextInt();
                    switch(programOption){
                        case 1:
                            repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), ex1));
                            break;
                        case 2:
                            repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), ex2));
                            break;
                        case 3:
                            repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), ex3));
                            break;
                        default:
                            break;
                    }
                }
                case 2:
                    try{
                        controller.allStep();
                    }
                    catch(Exception e){
                        System.out.println(e.toString());
                    }
                case 3: {
                    System.out.print("Input true or false: ");
                    String displayPrgStateOption = scanner.nextLine();
                    if (displayPrgStateOption == "true")
                        controller.setPrgState(true);
                    else
                        controller.setPrgState(false);
                }
                case 4:
                    return;
            }
        }
    }
}