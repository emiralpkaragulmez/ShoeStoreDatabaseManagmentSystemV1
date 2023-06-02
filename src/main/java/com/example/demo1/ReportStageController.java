package com.example.demo1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReportStageController {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ToggleButton runSelecetedQueryButton;

    @FXML
    private ToggleButton deneme;

    @FXML
    private ToggleButton goBack;

    @FXML
    void deneme(MouseEvent event) {
        // Create a JTextArea for user input
        JTextArea queryTextArea = new JTextArea(10, 40);
        queryTextArea.setLineWrap(true);

        // Create a JScrollPane for the JTextArea
        JScrollPane scrollPane = new JScrollPane(queryTextArea);

        // Create a JOptionPane with the JScrollPane for executing queries
        int result = JOptionPane.showConfirmDialog(null, scrollPane, "Enter SQL Query",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Get the query from the JTextArea
            String query = queryTextArea.getText().trim();

            // Execute the query and display the results in a dialog box
            try {
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
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("APStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Manager Panel");
        stage.setScene(scene);

    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        String selectedOption = comboBox.getValue();
        String pass = "Select Query";
        int firstNumber = 0;

        if (selectedOption.equals(pass)) {
            return;
        }
        int endIndex = 0;
        for (int i = 0; i < selectedOption.length(); i++) {
            if (Character.isDigit(selectedOption.charAt(i))) {
                endIndex = i + 1;
            } else {
                break;
            }
        }
        String firstIntegerStr = selectedOption.substring(0, endIndex);
        firstNumber = Integer.parseInt(firstIntegerStr);

        String query = null;
        if (firstNumber == 1){
            query = "SELECT sp.SalesPerson_id, sp.Name, sp.Surname, COUNT(s.Sale_id) AS TotalSales\n" +
                    "FROM SalesPersons sp\n" +
                    "INNER JOIN Sales s ON sp.SalesPerson_id = s.SalesPerson_id\n" +
                    "WHERE YEAR(s.Date) = 2022\n" +
                    "GROUP BY sp.SalesPerson_id, sp.Name, sp.Surname\n" +
                    "ORDER BY TotalSales DESC\n" +
                    "LIMIT 1\n";
        } else if (firstNumber == 2){
            query = "SELECT s.Shoe_id, s.Brand, s.Model, COUNT(*) AS TotalSales\n" +
                    "FROM Sales sa\n" +
                    "INNER JOIN Shoes s ON sa.Shoe_id = s.Shoe_id\n" +
                    "WHERE YEAR(sa.Date) = 2022\n" +
                    "GROUP BY s.Shoe_id, s.Brand, s.Model\n" +
                    "HAVING COUNT(*) = (\n" +
                    "    SELECT COUNT(*) AS SaleCount\n" +
                    "    FROM Sales sal\n" +
                    "    WHERE YEAR(sal.Date) = 2022\n" +
                    "    GROUP BY sal.Shoe_id\n" +
                    "    ORDER BY SaleCount DESC\n" +
                    "    LIMIT 1)\n";
        } else if (firstNumber == 3){
            query = "SELECT Brand, COUNT(*) AS TotalShoes\n" +
                    "FROM Shoes\n" +
                    "GROUP BY Brand\n" +
                    "ORDER BY TotalShoes DESC\n" +
                    "LIMIT 1;\n";
        } else if (firstNumber == 4){
            query = "SELECT c.Customer_id, c.Name, COUNT(s.Sale_id) AS TotalShoesBought\n" +
                    "FROM Customers c\n" +
                    "INNER JOIN Sales s ON c.Customer_id = s.Customer_id\n" +
                    "WHERE s.Date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)\n" +
                    "GROUP BY c.Customer_id, c.Name\n" +
                    "ORDER BY TotalShoesBought DESC\n" +
                    "LIMIT 10\n";
        }else if (firstNumber == 5){
            query = "SELECT c.Customer_id, c.Name, c.Surname, s.SalesPerson_id, sh.Model\n" +
                    "FROM Customers c\n" +
                    "JOIN Sales s ON c.Customer_id = s.Customer_id\n" +
                    "JOIN Shoes sh ON sh.Shoe_id = s.Shoe_id\n" +
                    "GROUP BY c.Customer_id, c.Name, c.Surname, s.SalesPerson_id, sh.Model\n" +
                    "HAVING COUNT(*) > 1;\n";
        }


        try {
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

}
