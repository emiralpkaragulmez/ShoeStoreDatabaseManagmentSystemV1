package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;

public class ShoesStageController {
    static int shoesCameFrom = 0;

    @FXML
    private ToggleButton AddRecordButton;

    @FXML
    private TextField BrandIdTextField;

    @FXML
    private TextField ColourTextField;

    @FXML
    private ToggleButton DeleteButton;

    @FXML
    private ToggleButton LookRecordsButton;

    @FXML
    private TextField ModelTextField;

    @FXML
    private TextField OutputTextField;

    @FXML
    private TextField PriceTextField;

    @FXML
    private ToggleButton SearchButton;

    @FXML
    private TextField ShoeIdTextField;

    @FXML
    private TextField SizeTextField;

    @FXML
    private ToggleButton UpdateButton;

    @FXML
    private ToggleButton goBack;

    @FXML
    void Delete(MouseEvent event) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase", "root", "12345678");
                Integer id = Integer.valueOf(ShoeIdTextField.getText());

                PreparedStatement preparedStatement2 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                preparedStatement2.executeUpdate();

                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shoes WHERE Shoe_id = ?");
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
                OutputTextField.setText("Record Deleted");
                ShoeIdTextField.setText("");
                BrandIdTextField.setText("");
                ModelTextField.setText("");
                ColourTextField.setText("");
                SizeTextField.setText("");
                PriceTextField.setText("");
                ShoeIdTextField.requestFocus();

                PreparedStatement preparedStatement3 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
                preparedStatement3.executeUpdate();


            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
            }
    }

    @FXML
    void Update(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");


            int id = Integer.valueOf(ShoeIdTextField.getText());
            String brand = BrandIdTextField.getText();
            String model = ModelTextField.getText();
            String colour = ColourTextField.getText();
            int size = Integer.parseInt(SizeTextField.getText());
            double price = Double.parseDouble(PriceTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE shoes SET Shoe_id = ?,Brand = ?,Model = ?,Color = ?,Size = ?,Price = ? WHERE Shoe_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, brand);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, colour);
            preparedStatement.setString(5, String.valueOf(size));
            preparedStatement.setString(6, String.valueOf(price));
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Updated");
            ShoeIdTextField.setText("");
            BrandIdTextField.setText("");
            ModelTextField.setText("");
            ColourTextField.setText("");
            SizeTextField.setText("");
            PriceTextField.setText("");
            ShoeIdTextField.requestFocus();

            preparedStatement.close();
            connection.close();



        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    void addRecord(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");
            Integer id = Integer.valueOf(ShoeIdTextField.getText());
            String brand = BrandIdTextField.getText();
            String model = ModelTextField.getText();
            String colour = ColourTextField.getText();
            int size = Integer.parseInt(SizeTextField.getText());
            double price = Double.parseDouble(PriceTextField.getText());

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO shoes(Shoe_id,Brand,Model,Color,Size,Price)values(?,?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, brand);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, colour);
            preparedStatement.setString(5, String.valueOf(size));
            preparedStatement.setString(6, String.valueOf(price));
            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Added.");
            ShoeIdTextField.setText("");
            BrandIdTextField.setText("");
            ModelTextField.setText("");
            ColourTextField.setText("");
            SizeTextField.setText("");
            PriceTextField.setText("");

            preparedStatement.close();
            connection.close();



        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        if (shoesCameFrom == 1){
            Stage stage = (Stage) goBack.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("APStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Manager Panel");
            stage.setScene(scene);

        } else if (shoesCameFrom == 2) {
            Stage stage = (Stage) goBack.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SPPStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Employee Panel");
            stage.setScene(scene);
        }

    }

    @FXML
    void lookRecords(MouseEvent event) {
        try {
            String query = "SELECT * FROM shoes";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create a DefaultTableModel for the results
            DefaultTableModel resultTableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add column names to the result table model
            for (int i = 1; i <= columnCount; i++) {
                resultTableModel.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the result table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                resultTableModel.addRow(row);
            }

            // Create a JTable for the result table model
            JTable resultTable = new JTable(resultTableModel);

            // Add the result table to a JScrollPane for scrollability
            JScrollPane resultScrollPane = new JScrollPane(resultTable);

            // Show the result dialog box
            JOptionPane.showMessageDialog(null, resultScrollPane, "Query Results",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error executing query!");
        }

    }

    @FXML
    void searchId(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");

            Integer id = Integer.valueOf(ShoeIdTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Brand,Model,Color,Size,Price FROM shoes WHERE Shoe_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == true){
                String brand = resultSet.getString(1);
                String model = resultSet.getString(2);
                String colour = resultSet.getString(3);
                int size = resultSet.getInt(4);
                double price = resultSet.getDouble(5);

                BrandIdTextField.setText(brand);
                ModelTextField.setText(model);
                ColourTextField.setText(colour);
                SizeTextField.setText(String.valueOf(size));
                PriceTextField.setText(String.valueOf(price));
            }
            else {
                ShoeIdTextField.setText("");
                BrandIdTextField.setText("");
                ModelTextField.setText("");
                ColourTextField.setText("");
                SizeTextField.setText("");
                PriceTextField.setText("");
                OutputTextField.setText("Invalid ID");
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }


}

