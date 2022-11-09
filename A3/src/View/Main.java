package View;

import Controller.Controller;
import Controller.IController;
import Model.Statements.*;
import Model.States.*;
import Model.Expressions.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;

import java.io.BufferedReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),
                                new ArithExp(3,new ValueExp(new IntValue(3)),
                                        new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",
                                        new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(
                                                new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
            new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
            new CompStmt(new openRFile(new VarExp("varf")),
            new CompStmt(new VarDeclStmt("varc", new IntType()),
            new CompStmt(new readFile(new VarExp("varf"), "varc"),
            new CompStmt(new PrintStmt(new VarExp("varc")),
            new CompStmt(new readFile(new VarExp("varf"), "varc"),
            new CompStmt(new PrintStmt(new VarExp("varc")),
            new closeRFile(new VarExp("varf"))))))))));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the path to the log file: ");
        String logFilePath = scanner.nextLine();
        IRepository repository = new Repository(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex1), logFilePath);
        IController controller = new Controller(repository);
        while(true) {
            try{
                System.out.println("1.Input a program");
                System.out.println("2.Execute the current program");
                System.out.println("3.Enable the displaying of the current program state");
                System.out.println("4.Test file table");
                System.out.println("5.Exit");
                System.out.print("Input a option: ");
                int option = scanner.nextInt();
                switch (option){
                    case 1:
                        System.out.print("Which of the hardcoded program do you want?(input 1 2 or 3): ");
                        int programOption = scanner.nextInt();
                        switch(programOption){
                            case 1:
                                repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(),new MyDictionary<String, BufferedReader>(), ex1));
                                System.out.println("First program selected");
                                break;
                            case 2:
                                repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), new MyDictionary<String, BufferedReader>(),ex2));
                                System.out.println("Second program selected");
                                break;
                            case 3:
                                repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), new MyDictionary<String, BufferedReader>(),ex3));
                                System.out.println("Third program selected");
                                break;
                            default:
                                System.out.println("Invalid option");
                                break;
                        }
                        break;
                    case 2:
                        controller.allStep();
                        System.out.println("Program executed successfully");
                        break;
                    case 3:
                        System.out.print("Input true or false: ");
                        controller.setDisplayPrgState(scanner.nextBoolean());
                        break;
                    case 4:
                        repository.setCrtPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex4));
                        controller.allStep();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}