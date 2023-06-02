package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class APStageController {

    @FXML
    private ToggleButton changeCustomersInformationButton;

    @FXML
    private ToggleButton changeSalesPersonsButton;

    @FXML
    private ToggleButton changeStockButton;

    @FXML
    private ToggleButton goBack;

    @FXML
    private ToggleButton getReportButton;

    @FXML
    private ToggleButton makeSellButton;

    @FXML
    void changeSceneToCustomersInformation(MouseEvent event) throws IOException {
        Stage stage = (Stage) changeCustomersInformationButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customersStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        CustomersStageController.CustomerCameFrom = 1;
        stage.setTitle("Customers");
        stage.setScene(scene);
    }

    @FXML
    void changeSceneToGetReport(MouseEvent event) throws IOException {
        Stage stage = (Stage) getReportButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reportStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Reports");
        stage.setScene(scene);
    }

    @FXML
    void changeStageToChangeSalesPersons(MouseEvent event) throws IOException {
        Stage stage = (Stage) changeSalesPersonsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("salesPersonsStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("SalesPersons");
        stage.setScene(scene);

    }

    @FXML
    void changeStageToMakeSell(MouseEvent event) throws IOException {
        Stage stage = (Stage) makeSellButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("salesStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        salesController.salesCameFrom = 1;
        stage.setTitle("Sales");
        stage.setScene(scene);
    }

    @FXML
    void changeStageToShoes(MouseEvent event) throws IOException {
        Stage stage = (Stage) changeStockButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shoesStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        ShoesStageController.shoesCameFrom = 1;
        stage.setTitle("Shoes");
        stage.setScene(scene);
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello Page");
        stage.setScene(scene);
    }


}
