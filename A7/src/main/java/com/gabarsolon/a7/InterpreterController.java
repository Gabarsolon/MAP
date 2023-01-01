package com.gabarsolon.a7;

import Model.Statements.IStmt;
import Controller.IController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class InterpreterController {
    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private TableView<String> heapTableView;

    @FXML
    private TextField noOfPrgStatesTextField;

    @FXML
    private ListView<String> outListView;

    @FXML
    private ListView<String> prgStatesListView;

    @FXML
    private Button runOneStepButton;

    @FXML
    private TableView<String> symTableView;
    private List<IStmt> prgList;
    private List<IController> prgControllers;
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
            plc.setProgramListView(prgList);
            plc.getProgramListView().getSelectionModel().
                    selectedItemProperty().addListener(new ChangeListener<IStmt>() {
                        @Override
                        public void changed(ObservableValue<? extends IStmt> observable, IStmt oldValue, IStmt newValue) {
                            Integer index = plc.getProgramListView().getSelectionModel().getSelectedIndex();
                            System.out.println(index);
                            programListWindow.hide();
                            mainStage.show();
                        }
                    });

            programListWindow.initModality(Modality.WINDOW_MODAL);
            programListWindow.initOwner(mainStage);

            Scene scene = new Scene(root, 200, 200);
            programListWindow.setTitle("Program List");
            programListWindow.setScene(scene);
            programListWindow.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void executeProgram(Integer index){

    }
    public void setPrgList(List<IStmt> prgList) {
        this.prgList = prgList;
    }

    public void setPrgControllers(List<IController> prgControllers) {
        this.prgControllers = prgControllers;
    }

    @FXML
    public void initialize(){
        heapTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        symTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}