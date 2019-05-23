package com.sap.booklist;

import com.sap.data.Book;
import com.sap.database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sap.database.DatabaseHandler.executeQuery;


public class BookListController implements Initializable {

    private ObservableList<Book> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initalizeColumn();
        showData();
    }
    DatabaseHandler databaseHandler;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publishedColumn;
    @FXML
    private TableColumn<Book, String> lentByColumn;
    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;

    private void initalizeColumn () {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishedColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        lentByColumn.setCellValueFactory(new PropertyValueFactory<>("lentBy"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
    }
    private void showData(){
        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String printingQuery = "SELECT * FROM BOOKS;";
        ResultSet resultSet = databaseHandler.executeQuery(printingQuery);
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publishedDate = resultSet.getString("publishedDate");
                String lentBy = resultSet.getString("lentBy");
                boolean isAvailible  = resultSet.getBoolean("availability");
                if (lentBy != null){
                    isAvailible = false;
                } else {
                    isAvailible = true;
                }
                data.add(new Book(id, title, author, publishedDate, lentBy, isAvailible));
            }
        }catch (SQLException e) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, "Error showing data", e);
            e.printStackTrace();
        }
        tableView.setItems(data);
    }
}
