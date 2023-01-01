package com.gabarsolon.a7;

import Model.Statements.IStmt;
import Controller.IController;
import Model.States.PrgState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.StringTokenizer;

public class InterpreterController {
    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private TableView<ObservableList<String>> heapTableView;

    @FXML
    private TextField noOfPrgStatesTextField;

    @FXML
    private ListView<String> outListView;

    @FXML
    private ListView<Integer> prgStatesListView;

    @FXML
    private Button runOneStepButton;

    @FXML
    private TableView<String> symTableView;

    private IController currentPrgController;
    private List<IStmt> stmtList;
    private List<IController> prgControllers;
    private List<PrgState> prgList;
    private Stage mainStage;
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void createProgramListWindow(){
        try {
            Stage programListWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramList.fxml"));
            StackPane root = loader.load();

            ProgramListController plc = loader.getController();
            plc.setProgramListView(stmtList);
            plc.getProgramListView().getSelectionModel().
                    selectedItemProperty().addListener(new ChangeListener<IStmt>() {
                        @Override
                        public void changed(ObservableValue<? extends IStmt> observable, IStmt oldValue, IStmt newValue) {
                            currentPrgController = prgControllers.get(plc.getProgramListView().getSelectionModel().getSelectedIndex());
                            prgList = currentPrgController.getPrgList();
                            currentPrgController.prepareExecution();
                            updateProgramStatus();
                            programListWindow.hide();
                            mainStage.show();
                        }
                    });

            programListWindow.initModality(Modality.WINDOW_MODAL);
            programListWindow.initOwner(mainStage);

            Scene scene = new Scene(root, 600, 400);
            programListWindow.setTitle("Program List");
            programListWindow.setScene(scene);
            programListWindow.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private void updateProgramStatus(){
        prgList = currentPrgController.removeCompletedPrg(prgList);

        prgStatesListView.getItems().clear();
        heapTableView.getItems().clear();
        outListView.getItems().clear();
        fileTableListView.getItems().clear();
        symTableView.getItems().clear();
        exeStackListView.getItems().clear();

        noOfPrgStatesTextField.setText(Integer.toString(prgList.size()));
        prgList.stream().forEach(prg->prgStatesListView.getItems().add(prg.getPrgId()));

        prgList.get(0).getHeapTable().getData().entrySet().stream().forEach(
                e-> heapTableView.getItems().add(FXCollections.observableArrayList(e.getKey().toString(), e.getValue().toString()))
        );
        prgList.get(0).getOut().getData().stream().forEach(e->outListView.getItems().add(e.toString()));
        prgList.get(0).getFileTable().getData().entrySet().stream().forEach(e->fileTableListView.getItems().add(e.getKey()));
    }
    @FXML
    void runOneStep(ActionEvent event) {
        try{
            currentPrgController.oneStepForAllPrg(currentPrgController.getPrgList());
            updateProgramStatus();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void setStmtList(List<IStmt> stmtList) {
        this.stmtList = stmtList;
    }

    public void setPrgControllers(List<IController> prgControllers) {
        this.prgControllers = prgControllers;
    }

    @FXML
    public void initialize(){
        heapTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        symTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        prgStatesListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                symTableView.getItems().clear();
                exeStackListView.getItems().clear();

                PrgState prg = prgList.get((Integer)newValue);
                //prgList.get((Integer)newValue).getSymTable().getData().entrySet().stream().forEach();
                StringTokenizer st = new StringTokenizer(prg.getExeStack().toString(),";");
                while(st.hasMoreTokens()){
                    exeStackListView.getItems().add(st.nextToken());
                }
            }
        });
    }
}