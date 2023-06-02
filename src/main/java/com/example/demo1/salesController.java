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

public class salesController {
    static int salesCameFrom = 0;

    @FXML
    private ToggleButton AddRecordButton;

    @FXML
    private TextField CustomerIdTextField;

    @FXML
    private TextField DateTextField;

    @FXML
    private ToggleButton DeleteButton;

    @FXML
    private ToggleButton LookRecordsButton;

    @FXML
    private TextField OutputTextField;

    @FXML
    private TextField SaleIdTextField;

    @FXML
    private TextField SalesPersonIdTextField;

    @FXML
    private ToggleButton SearchButton;

    @FXML
    private TextField ShoeIdTextField;

    @FXML
    private ToggleButton UpdateButton;

    @FXML
    private ToggleButton goBack;

    @FXML
    void Delete(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");
            Integer id = Integer.valueOf(SaleIdTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sales WHERE Sale_Id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            OutputTextField.setText("Record Deleted");
            SalesPersonIdTextField.setText("");
            CustomerIdTextField.setText("");
            ShoeIdTextField.setText("");
            DateTextField.setText("");
            SaleIdTextField.setText("");

            SaleIdTextField.requestFocus();



        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    void Update(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");


            int Sale_id = Integer.valueOf(SaleIdTextField.getText());
            int Customer_id = Integer.valueOf(CustomerIdTextField.getText());
            int Shoe_id = Integer.valueOf(ShoeIdTextField.getText());
            int SalesPerson_id = Integer.valueOf(SalesPersonIdTextField.getText());
            Date Date = java.sql.Date.valueOf(DateTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE sales SET Sale_id = ?,Customer_id = ?,Shoe_id = ?,SalesPerson_id = ?,Date = ? WHERE Sale_id = ?");
            preparedStatement.setInt(1, Sale_id);
            preparedStatement.setInt(2, Customer_id);
            preparedStatement.setInt(3, Shoe_id);
            preparedStatement.setInt(4, SalesPerson_id);
            preparedStatement.setDate(5, Date);
            preparedStatement.setInt(6, Sale_id);

            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Updated");
            SaleIdTextField.setText("");
            CustomerIdTextField.setText("");
            ShoeIdTextField.setText("");
            SalesPersonIdTextField.setText("");
            DateTextField.setText("");
            SaleIdTextField.requestFocus();

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
            int id = Integer.valueOf(SaleIdTextField.getText());
            int customerid = Integer.parseInt(CustomerIdTextField.getText());
            int shoeid = Integer.parseInt(ShoeIdTextField.getText());
            int salespersonid = Integer.parseInt(SalesPersonIdTextField.getText());
            Date date = Date.valueOf(DateTextField.getText());

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales(Sale_id,Customer_id,Shoe_id,SalesPerson_id,Date)values(?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, customerid);
            preparedStatement.setInt(3, shoeid);
            preparedStatement.setInt(4, salespersonid);
            preparedStatement.setDate(5, date);
            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Added.");
            SaleIdTextField.setText("");
            CustomerIdTextField.setText("");
            ShoeIdTextField.setText("");
            SalesPersonIdTextField.setText("");
            DateTextField.setText("");

            preparedStatement.close();
            connection.close();



        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        if (salesCameFrom == 1){
            Stage stage = (Stage) goBack.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("APStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Manager Panel");
            stage.setScene(scene);

        } else if (salesCameFrom == 2) {
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
            String query = "SELECT * FROM sales";
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

            Integer id = Integer.valueOf(SaleIdTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Customer_id,Shoe_id,SalesPerson_id,Date FROM sales WHERE Sale_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == true){
                int Customer_id = resultSet.getInt(1);
                int Shoe_id = resultSet.getInt(2);
                int SalesPerson_id = resultSet.getInt(3);
                Date Date = resultSet.getDate(4);

                CustomerIdTextField.setText(String.valueOf(Customer_id));
                ShoeIdTextField.setText(String.valueOf(Shoe_id));
                SalesPersonIdTextField.setText(String.valueOf(SalesPerson_id));
                DateTextField.setText(String.valueOf(Date));
            }
            else {
                CustomerIdTextField.setText("");
                ShoeIdTextField.setText("");
                SalesPersonIdTextField.setText("");
                DateTextField.setText("");
                OutputTextField.setText("Invalid ID");
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

}
