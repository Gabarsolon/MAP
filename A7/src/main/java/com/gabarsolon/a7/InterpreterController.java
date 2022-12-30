package com.gabarsolon.a7;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InterpreterController {
    private void createProgramListWindow(){
        try {
            Stage programListWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramList.fxml"));
            VBox root = loader.load();
            Scene scene = new Scene(root, 200, 200);
            programListWindow.setScene(scene);
            programListWindow.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void initialize(){
        createProgramListWindow();
    }
}