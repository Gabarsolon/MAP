package View;

import Controller.Controller;
import Controller.IController;
import Model.Statements.*;
import Model.States.*;
import Model.Expressions.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;
import com.sun.source.tree.VariableTree;

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

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new allocHeap("a", new VarExp("v")),
            new CompStmt(new PrintStmt(new VarExp("v")),
                         new PrintStmt(new VarExp("a")))))));

        /*
        Example:
        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        At the end of execution: Heap={1->20, 2->(1,int)}, SymTable={v->(1,int), a->(2,Ref int)} and
        Out={20, 25}
        */

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new allocHeap("a", new VarExp("v")),
            new CompStmt(new PrintStmt(new readHeap(new VarExp("v"))),
            new PrintStmt(new ArithExp(1, new readHeap(new readHeap(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        /*
        Example: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        At the end of execution: Heap={1->30}, SymTable={v->(1,int)} and Out={20, 35}
         */

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
            new CompStmt(new PrintStmt(new readHeap(new VarExp("v"))),
            new CompStmt(new writeHeap("v", new ValueExp(new IntValue(30))),
            new PrintStmt(new ArithExp(1, new readHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new allocHeap("a", new VarExp("v")),
            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(30))),
            new PrintStmt(new readHeap(new readHeap(new VarExp("a")))))))));





        PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), ex1);
        PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex2);
        PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex3);
        PrgState prg4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex4);
        PrgState prg5 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex5);
        PrgState prg6 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex6);
        PrgState prg7 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex7);
        PrgState prg8 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(),ex8);


        IRepository rp1 = new Repository(prg1, logFilePath);
        IRepository rp2 = new Repository(prg2, logFilePath);
        IRepository rp3 = new Repository(prg3, logFilePath);
        IRepository rp4 = new Repository(prg4, logFilePath);
        IRepository rp5 = new Repository(prg5, logFilePath);
        IRepository rp6 = new Repository(prg6, logFilePath);
        IRepository rp7 = new Repository(prg7, logFilePath);
        IRepository rp8 = new Repository(prg8, logFilePath);

        IController ctr1 = new Controller(rp1);
        IController ctr2 = new Controller(rp2);
        IController ctr3 = new Controller(rp3);
        IController ctr4 = new Controller(rp4);
        IController ctr5 = new Controller(rp5);
        IController ctr6 = new Controller(rp6);
        IController ctr7 = new Controller(rp7);
        IController ctr8 = new Controller(rp8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new SetDisplayPrgStateFlag("displayFlag", "Enable the displaying of the program state"));
        menu.addCommand(new RunExample("1", "Run the first example", ctr1));
        menu.addCommand(new RunExample("2", "Run the second example", ctr2));
        menu.addCommand(new RunExample("3", "Run the third example", ctr3));
        menu.addCommand(new RunExample("4", "Run the fourth example", ctr4));
        menu.addCommand(new RunExample("5", "Run the fifth example", ctr5));
        menu.addCommand(new RunExample("6", "Run the sixth example", ctr6));
        menu.addCommand(new RunExample("7", "Run the seventh example", ctr7));
        menu.addCommand(new RunExample("8", "Run the eighth example", ctr8));
        menu.show();
    }
}