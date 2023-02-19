package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EndangeredFormController implements Initializable {


    @FXML
    //All Components in EndangeredForm
    private TableView<EndangeredOrNot> endangeredTable;

    public ObservableList<EndangeredOrNot> data;

    @FXML
    public TableColumn<EndangeredOrNot, String> clmNotEndangered;
    public TableColumn<EndangeredOrNot, String> clmEndangered;


    //Get MySql database URL and login data
    final String DB_URL = "jdbc:mysql://localhost:3306/solutech?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "1234";

    Connection conn = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        data = FXCollections.observableArrayList();

        try {

            //Connect to the database
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            //Select columns of what we want to get data - create statement
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT id,name FROM addanimal WHERE endangered='Yes'");

            // Extract data from result set
            while (result.next()) {

                //Get values from database-table
                String id = result.getString(1);
                String name = result.getString(2);

                    String Endangered = id + "  :  " + name;

                    //Set values EndangeredOrNot class
                    data.add(new EndangeredOrNot(Endangered,null));

                //sets the cell value factory for a table columns
                clmEndangered.setCellValueFactory(new PropertyValueFactory<>("clmEndangered"));
                clmNotEndangered.setCellValueFactory(new PropertyValueFactory<>("clmNotEndangered"));

                //Add data to TableView
                endangeredTable.setItems(data);

            }

            //Select columns of what we want to get data - create statement
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT id,name FROM addanimal WHERE endangered='No'");

            int i = 0;

            // Extract data from result set
            while (resultSet.next()) {

                //Get values from database-table
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);

                String notEndangered = id + "  :  " + name;

                //Set values EndangeredOrNot class
                data.get(i).setClmNotEndangered(notEndangered);
                i++;
            }

        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
