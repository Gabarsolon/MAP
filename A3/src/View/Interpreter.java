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

public class Interpreter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the path to the log file: ");
        String logFilePath = scanner.nextLine();

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

        PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex1);
        PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex2);
        PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex3);
        PrgState prg4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), ex4);

        IRepository rp1 = new Repository(prg1, logFilePath);
        IRepository rp2 = new Repository(prg2, logFilePath);
        IRepository rp3 = new Repository(prg3, logFilePath);
        IRepository rp4 = new Repository(prg4, logFilePath);

        IController ctr1 = new Controller(rp1);
        IController ctr2 = new Controller(rp2);
        IController ctr3 = new Controller(rp3);
        IController ctr4 = new Controller(rp4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", "Run the first example", ctr1));
        menu.addCommand(new RunExample("2", "Run the second example", ctr2));
        menu.addCommand(new RunExample("3", "Run the third example", ctr3));
        menu.addCommand(new RunExample("4", "Run the fourth example", ctr4));
        menu.addCommand(new SetDisplayPrgStateFlag("5", "Enable the displaying of the program state"));
        menu.show();
    }
}