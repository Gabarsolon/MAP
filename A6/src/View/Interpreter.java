package View;

import Controller.Controller;
import Controller.IController;
import Model.Exceptions.MyException;
import Model.Statements.*;
import Model.States.*;
import Model.Expressions.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;

import javax.crypto.CipherInputStream;
import java.io.BufferedReader;
import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the path to the log file: ");
            String logFilePath = scanner.nextLine();

            PrgState.setAvailableId(0);

            IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))));

            IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)),
                                    new ArithExp(3, new ValueExp(new IntValue(3)),
                                            new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"),
                                            new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",
                                            new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(
                                            new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

            IStmt fileOperationsEx = new CompStmt(new VarDeclStmt("varf", new StringType()),
                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                            new CompStmt(new openRFile(new VarExp("varf")),
                                    new CompStmt(new VarDeclStmt("varc", new IntType()),
                                            new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
                                                            new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                            new closeRFile(new VarExp("varf"))))))))));

            IStmt heapAllocationEx = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
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

            IStmt heapReadingEx = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new allocHeap("a", new VarExp("v")),
                                            new CompStmt(new PrintStmt(new readHeap(new VarExp("v"))),
                                                    new PrintStmt(new ArithExp(1, new readHeap(new readHeap(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        /*
        Example: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        At the end of execution: Heap={1->30}, SymTable={v->(1,int)} and Out={20, 35}
         */

            IStmt heapWritingEx = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new PrintStmt(new readHeap(new VarExp("v"))),
                                    new CompStmt(new writeHeap("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(new ArithExp(1, new readHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));

            //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

            IStmt garbageCollectorEx = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new allocHeap("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new allocHeap("a", new VarExp("v")),
                                            new CompStmt(new allocHeap("v", new ValueExp(new IntValue(30))),
                                                    new PrintStmt(new readHeap(new readHeap(new VarExp("a")))))))));

            IStmt whileStmtEx = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new CompStmt(new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                            new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VarExp("v")))));

            IStmt concurrentEx = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                    new CompStmt(new allocHeap("a", new ValueExp(new IntValue(22))),
                                            new CompStmt(new forkStmt(new CompStmt(new writeHeap("v", new ValueExp(new IntValue(30))),
                                                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                                    new PrintStmt(new readHeap(new VarExp("a"))))))),
                                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                                            new PrintStmt(new readHeap(new VarExp("a")))))))));

            PrgState prg1, prg2, prg3, prg4, prg5, prg6, prg7, prg8, prg9, prg10;
            try{
                ex1.typecheck(new MyDictionary<>());
                prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), ex1);
            }catch (Exception e){
                System.out.println("Ex1: " + e);
                prg1 = null;
            }

            try{
                ex2.typecheck(new MyDictionary<>());
                prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), ex2);
            }catch (Exception e){
                System.out.println("Ex2: " + e);
                prg2 = null;
            }

            try{
                ex3.typecheck(new MyDictionary<>());
                prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), ex3);
            }catch (Exception e){
                System.out.println("Ex3: " + e);
                prg3=null;
            }

            try{
                fileOperationsEx.typecheck(new MyDictionary<>());
                prg4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), fileOperationsEx);
            }catch (Exception e){
                System.out.println("fileOperationsEx: " + e);
                prg4=null;
            }

            try{
                heapAllocationEx.typecheck(new MyDictionary<>());
                prg5 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), heapAllocationEx);
            }catch (Exception e){
                System.out.println("heapAllocationEx: " + e);
                prg5=null;
            }

            try{
                heapReadingEx.typecheck(new MyDictionary<>());
                prg6 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), heapReadingEx);
            }catch (Exception e){
                System.out.println("heapReadingEx: " + e);
                prg6=null;
            }

            try{
                heapWritingEx.typecheck(new MyDictionary<>());
                prg7 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), heapWritingEx);
            }catch (Exception e){
                System.out.println("heapWritingEx: " + e);
                prg7=null;
            }

            try{
                garbageCollectorEx.typecheck(new MyDictionary<>());
                prg8 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), garbageCollectorEx);
            }catch (Exception e){
                System.out.println("garbageCollectorEx: " + e);
                prg8=null;
            }

            try{
                whileStmtEx.typecheck(new MyDictionary<>());
                prg9 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), whileStmtEx);
            }catch (Exception e){
                System.out.println("whileStmtEx: " + e);
                prg9=null;
            }

            try{
                concurrentEx.typecheck(new MyDictionary<>());
                prg10 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                        new MyList<Value>(), new MyDictionary<String, BufferedReader>(), new MyHeap<Integer, Value>(), concurrentEx);
            }catch (Exception e){
                System.out.println("concurrentEx: " + e);
                prg10=null;
            }

            IRepository rp1 = new Repository(prg1, logFilePath);
            IRepository rp2 = new Repository(prg2, logFilePath);
            IRepository rp3 = new Repository(prg3, logFilePath);
            IRepository rp4 = new Repository(prg4, logFilePath);
            IRepository rp5 = new Repository(prg5, logFilePath);
            IRepository rp6 = new Repository(prg6, logFilePath);
            IRepository rp7 = new Repository(prg7, logFilePath);
            IRepository rp8 = new Repository(prg8, logFilePath);
            IRepository rp9 = new Repository(prg9, logFilePath);
            IRepository rp10 = new Repository(prg10, logFilePath);

            IController ctr1 = new Controller(rp1);
            IController ctr2 = new Controller(rp2);
            IController ctr3 = new Controller(rp3);
            IController ctr4 = new Controller(rp4);
            IController ctr5 = new Controller(rp5);
            IController ctr6 = new Controller(rp6);
            IController ctr7 = new Controller(rp7);
            IController ctr8 = new Controller(rp8);
            IController ctr9 = new Controller(rp9);
            IController ctr10 = new Controller(rp10);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new SetDisplayPrgStateFlag("displayFlag", "Enable the displaying of the program state"));
            menu.addCommand(new RunExample("1", "Run the first example", ctr1));
            menu.addCommand(new RunExample("2", "Run the second example", ctr2));
            menu.addCommand(new RunExample("3", "Run the third example", ctr3));
            menu.addCommand(new RunExample("4", "Run the file operations example", ctr4));
            menu.addCommand(new RunExample("5", "Run the heap allocation example", ctr5));
            menu.addCommand(new RunExample("6", "Run the heap reading example", ctr6));
            menu.addCommand(new RunExample("7", "Run the heap writing example", ctr7));
            menu.addCommand(new RunExample("8", "Run the garbage collector example", ctr8));
            menu.addCommand(new RunExample("9", "Run the while stmt example", ctr9));
            menu.addCommand(new RunExample("10", "Run the concurrent example", ctr10));
            menu.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}