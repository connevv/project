package com.sap.memberlist;

import com.sap.data.Book;
import com.sap.data.User;
import com.sap.database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.sap.database.DatabaseHandler.executeQuery;

public class MemberListController implements Initializable {
    ObservableList<User> data = FXCollections.observableArrayList();
    DatabaseHandler handler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeColumn();
        showMembers();
    }
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> mobileColumn;
    @FXML
    private TableColumn<User, String> emailColumn;


    private void initializeColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void showMembers() {
        try {
            handler = DatabaseHandler.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String printingQuery = "SELECT * FROM USERS;";
        ResultSet resultSet = handler.executeQuery(printingQuery);
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("first_Name");
                String lastName = resultSet.getString("last_Name");
                String mobile = resultSet.getString("mobile");
                String email = resultSet.getString("email");
                data.addAll(new User(id, firstName, lastName, mobile, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }
}