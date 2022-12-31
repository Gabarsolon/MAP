package com.gabarsolon.a7;

import Model.Statements.IStmt;
import Controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class InterpreterController {
    private List<IStmt> prgList;
    private List<IController> prgControllers;
    private Stage mainStage;
    @FXML
    private Label welcomeText;
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

            programListWindow.initModality(Modality.WINDOW_MODAL);
            programListWindow.initOwner(mainStage);

            Scene scene = new Scene(root, 200, 200);
            programListWindow.setScene(scene);
            programListWindow.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void setPrgList(List<IStmt> prgList) {
        this.prgList = prgList;
    }

    public void setPrgControllers(List<IController> prgControllers) {
        this.prgControllers = prgControllers;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void initialize(){

    }
}