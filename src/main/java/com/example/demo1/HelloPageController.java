package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class HelloPageController {

    @FXML
    private ToggleButton APanel;

    @FXML
    private ToggleButton SPPanel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField outputTextField;



    @FXML
    void goAPStage(MouseEvent event) throws IOException {
        String preAdminUsername = "Admin";
        String preAdminPassword = "12345678";
        if (passwordTextField.getText().equals(preAdminPassword) && usernameTextField.getText().equals(preAdminUsername)){
            Stage stage = (Stage) APanel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("APStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("ManagerPanel");
            stage.setScene(scene);
        }else {
            outputTextField.setText("Make sure information are correct.");
        }



    }

    @FXML
    void goSPPStage(MouseEvent event) throws IOException {
        String salesPersonUsername = "Sales Person";
        String salesPersonPassword = "12345678";
        if (passwordTextField.getText().equals(salesPersonPassword) && usernameTextField.getText().equals(salesPersonUsername)){
            Stage stage = (Stage) SPPanel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SPPStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Employee Panel");
            stage.setScene(scene);

        }else {
            outputTextField.setText("Make sure information are correct.");
        }
    }
}
