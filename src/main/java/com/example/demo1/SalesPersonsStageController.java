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

public class SalesPersonsStageController {


    @FXML
    private ToggleButton AddRecordButton;

    @FXML
    private TextField AddressTextField;

    @FXML
    private ToggleButton DeleteButton;

    @FXML
    private TextField EmailAddressTextField;

    @FXML
    private ToggleButton LookRecordsButton;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField OutputTextField;

    @FXML
    private TextField PhoneNumberTextField;

    @FXML
    private TextField SalesPersonIdTextField;

    @FXML
    private ToggleButton SearchButton;

    @FXML
    private TextField SurnameTextField;

    @FXML
    private ToggleButton UpdateButton;

    @FXML
    private ToggleButton goBack;

    @FXML
    void Delete(MouseEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestoredatabase","root","12345678");
            Integer id = Integer.valueOf(SalesPersonIdTextField.getText());
            PreparedStatement preparedStatement2 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            preparedStatement2.executeUpdate();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sales WHERE SalesPerson_Id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            OutputTextField.setText("Record Deleted");
            SalesPersonIdTextField.setText("");
            NameTextField.setText("");
            SurnameTextField.setText("");
            PhoneNumberTextField.setText("");
            EmailAddressTextField.setText("");
            AddressTextField.setText("");
            SalesPersonIdTextField.requestFocus();

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


            Integer id = Integer.valueOf(SalesPersonIdTextField.getText());
            String name = NameTextField.getText();
            String surname = SurnameTextField.getText();
            String pnumber = PhoneNumberTextField.getText();
            String email = EmailAddressTextField.getText();
            String address = AddressTextField.getText();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SalesPersons SET SalesPerson_id = ?,Name = ?,Surname = ?,PhoneNumber = ?,EmailAddress = ?,Address = ? WHERE SalesPerson_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, pnumber);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Updated");
            SalesPersonIdTextField.setText("");
            NameTextField.setText("");
            SurnameTextField.setText("");
            PhoneNumberTextField.setText("");
            EmailAddressTextField.setText("");
            AddressTextField.setText("");
            SalesPersonIdTextField.requestFocus();

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
            Integer id = Integer.valueOf(SalesPersonIdTextField.getText());
            String name = NameTextField.getText();
            String surname = SurnameTextField.getText();
            String pnumber = PhoneNumberTextField.getText();
            String email = EmailAddressTextField.getText();
            String address = AddressTextField.getText();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SalesPersons(SalesPerson_id,Name,Surname,PhoneNumber,EmailAddress,Address)values(?,?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, pnumber);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);
            preparedStatement.executeUpdate();

            OutputTextField.setText("Record Added.");
            SalesPersonIdTextField.setText("");
            NameTextField.setText("");
            SurnameTextField.setText("");
            PhoneNumberTextField.setText("");
            EmailAddressTextField.setText("");
            AddressTextField.setText("");

            preparedStatement.close();
            connection.close();



        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("APStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Admin Panel");
        stage.setScene(scene);

    }

    @FXML
    void lookRecords(MouseEvent event) {

        try {
            String query = "SELECT * FROM SalesPersons";
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

            Integer id = Integer.valueOf(SalesPersonIdTextField.getText());
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Name,Surname,PhoneNumber,EmailAddress,Address FROM SalesPersons WHERE SalesPerson_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == true){
                String name = resultSet.getString(1);
                String surname = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String email = resultSet.getString(4);
                String address = resultSet.getString(5);

                NameTextField.setText(name);
                SurnameTextField.setText(surname);
                PhoneNumberTextField.setText(phone);
                EmailAddressTextField.setText(email);
                AddressTextField.setText(address);
            }
            else {
                SalesPersonIdTextField.setText("");
                NameTextField.setText("");
                SurnameTextField.setText("");
                PhoneNumberTextField.setText("");
                EmailAddressTextField.setText("");
                AddressTextField.setText("");
                OutputTextField.setText("Invalid ID");
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

}
