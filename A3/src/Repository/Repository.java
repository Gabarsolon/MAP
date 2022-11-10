package Repository;

import Model.Exceptions.MyException;
import Model.States.MyList;
import Model.States.PrgState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Repository implements IRepository{
    private PrgState crtPrg;
    private String logFilePath;
    public Repository(PrgState prg, String logFilePath){
        this.crtPrg = prg;
        this.logFilePath = logFilePath;
    }
    public PrgState getCrtPrg(){
        return crtPrg;
    }
    public void setCrtPrg(PrgState prg){
        this.crtPrg = prg;
    }
    public void logPrgStateExec() throws MyException{
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("log.txt",true)));
            logFile.println("ExeStack:");
            StringTokenizer st = new StringTokenizer(crtPrg.getExeStack().toString(),";");
            while(st.hasMoreTokens()){
                logFile.println(st.nextToken());
            }
            logFile.println("SymTable:");
            logFile.println(crtPrg.getSymTable());
            logFile.println("Out:");
            logFile.println(crtPrg.getOut());
            logFile.println("FileTable:");
            logFile.println(crtPrg.getFileTable());
            logFile.println("----------------------------");
            logFile.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
