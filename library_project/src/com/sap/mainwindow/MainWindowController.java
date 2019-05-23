package com.sap.mainwindow;

import com.jfoenix.effects.JFXDepthManager;
import com.sap.database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController implements Initializable {
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox bookInfo;
    @FXML
    private HBox memberInfo;
    @FXML
    private TextField bookIDInput;
    @FXML
    private Text bookTitle;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookAvailability;
    @FXML
    private TextField userIDInput;
    @FXML
    private Text userName;
    @FXML
    private Text userMobile;
    @FXML
    private Text userEmail;
    @FXML
    private Text deleteTitle;
    @FXML
    private Text deleteAuthor;
    @FXML
    private TextField deleteBookId;
    @FXML
    private TextField returnId;
    @FXML
    private ListView<String> list;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(bookInfo, 3); // izostrqne na prozoreca
        JFXDepthManager.setDepth(memberInfo, 3);
        JFXDepthManager.setDepth(deleteTitle, 3);
        JFXDepthManager.setDepth(deleteAuthor, 3);
    }

    public void menuCloseHandleButton(ActionEvent actionEvent) {
        ((Stage)rootPane.getScene().getWindow()).close();
    }

    public void menuViewMembersAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/memberlist/memberList.fxml", "Members List");    }

    public void menuViewBookListAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/booklist/BookList.fxml", "Book List");    }


    public void addMemberButtonAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/addMember/addMember.fxml", "Add member");
    }


    public void addBookButtonAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/addBook/addBook.fxml", "Add book");
    }


    public void viewMembersButton(ActionEvent actionEvent) {
        loadWindow("/com/sap/memberlist/memberList.fxml", "Members List");
    }


    public void viewBookButtonAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/booklist/BookList.fxml", "Book List");
    }

    public void menuAddMemberAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/addMember/addMember.fxml", "Add member");
    }

    public void menuAddBookAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/addBook/addBook.fxml", "Add book");
    }

    public void loadBookIdAction (ActionEvent actionEvent) {
        clearBookCache();
        String id = bookIDInput.getText();
        String query = "SELECT * FROM books WHERE id = '" + id + "'";
        boolean flag = false;
        ResultSet resultSet = DatabaseHandler.executeQuery(query);
       try {
           assert resultSet != null;
           while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean isAvailable = resultSet.getBoolean("availability");
                bookTitle.setText(title);
                bookAuthor.setText(author);
                String available = (isAvailable)? "Available" : "Not Available";
                bookAvailability.setText(available);
                flag = true;
           }
           if (!flag) {
               bookTitle.setText("No such book available");
       }
        } catch (SQLException e) {
           Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
       }
    }

    public void settingsButtonAction(ActionEvent actionEvent) {
        loadWindow("/com/sap/settings/Settings.fxml", "Settings");
    }
    private void loadWindow(String location, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        } catch (IOException e) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadDeleteBookInfo(ActionEvent actionEvent) {
        clearDeleteBookCache();
        String id = deleteBookId.getText();
        String query = "SELECT * FROM books WHERE id = '" + id + "'";
        boolean flag = false;
        ResultSet resultSet = DatabaseHandler.executeQuery(query);
        try{
            assert resultSet != null;
            while (resultSet.next()){
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                deleteTitle.setText(title);
                deleteAuthor.setText(author);
                flag = true;
            }
            if (!flag) {
                deleteTitle.setText("No such book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void loadMemberInfoAction(ActionEvent actionEvent) {
        clearMemberCache();
        String id = userIDInput.getText();
        String query = "SELECT * FROM users WHERE id = '" + id + "'";
        boolean flag = false;
        ResultSet resultSet = DatabaseHandler.executeQuery(query);
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                String name = resultSet.getString("first_name");
                String mobile = resultSet.getString("mobile");
                String email = resultSet.getString("email");
                userName.setText(name);
                userMobile.setText(mobile);
                userEmail.setText(email);
                flag = true;
            }
            if (!flag) {
                userName.setText("No such member");
            }
        } catch (SQLException e) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void returnBookButtonAction(ActionEvent actionEvent) {
        String bookId = returnId.getText();
        String lentQuery = "DELETE FROM lented WHERE bookId = '" + bookId + "';";
        String changeQuery = "UPDATE books SET availability = true WHERE id = '" + bookId + "';";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirm operation");
        alert.setContentText("Are you sure you want to return this book?");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            if (DatabaseHandler.executeAction(lentQuery) && DatabaseHandler.executeAction(changeQuery)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Operation successfull");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setContentText("Operation failed");
                alert1.showAndWait();
            }
        }
    }

    public void loadReturnBook(ActionEvent actionEvent) {
        ObservableList<String> data = FXCollections.observableArrayList();
        String bookId = returnId.getText();
        String query = "SELECT * FROM lented WHERE bookId = '" + bookId + "';";
        ResultSet resultSet = DatabaseHandler.executeQuery(query) ;
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                String newBookId = resultSet.getString("bookId");
                String newMemberId = resultSet.getString("memberId");
                Timestamp lentedTime = resultSet.getTimestamp("lentedTime");
                data.add("Book was lented on: " + lentedTime.toGMTString());
                data.add("Book information: \n");
                String bookQuery = "SELECT * FROM books WHERE id = '" + newBookId + "';";
                ResultSet resultSet1 = DatabaseHandler.executeQuery(bookQuery);
                assert resultSet1 != null;
                while (resultSet1.next()){
                    data.add("\t Book id: " + resultSet1.getString("id"));
                    data.add("\t Book title: " + resultSet1.getString("title"));
                    data.add("\t Book author: " + resultSet1.getString("author"));
                    data.add("\t Book published date: : " + resultSet1.getString("publishedDate"));
                }
                data.add("Member information: \n");
                String userQuery = "SELECT * FROM users WHERE id = '" + newMemberId + "';";
                ResultSet resultSet2 = DatabaseHandler.executeQuery(userQuery);
                assert resultSet2 != null;
                while (resultSet2.next()) {
                    data.add("\t Member id: " + resultSet2.getString("id"));
                    data.add("\t Member First Name: " + resultSet2.getString("first_name"));
                    data.add("\t Member Last Name: " + resultSet2.getString("last_name"));
                    data.add("\t Member Mobile: " + resultSet2.getString("mobile"));
                    data.add("\t Member Email: " + resultSet2.getString("email"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.getItems().setAll(data);
        if (data.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The book you searched for is available");
            alert.showAndWait();
        }
    }

    public void deleteBookButtonAction(ActionEvent actionEvent) {
        String id = deleteBookId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirm operation");
        alert.setContentText("Are you sure you want to delete the book " + bookTitle.getText() + "?");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String query = "DELETE FROM books WHERE id = '" + id + "';";
            if (DatabaseHandler.executeAction(query)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Operation successfull");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setContentText("Operation failed");
                alert1.showAndWait();
            }
        }
    }

    public void borrowBookButtonAction(ActionEvent actionEvent) {
        String book_id = bookIDInput.getText();
        String user_id = userIDInput.getText();
        if (bookAvailability.getText().equals("Not Available")) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText(null);
            alert2.setContentText("That book is not available");
            alert2.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirm operation");
            alert.setContentText("Are you sure you wanna lent the book " + bookTitle.getText() + " to " + userName.getText());
            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                String query = "INSERT INTO lented (bookId, memberId) VALUES ('" + book_id + "', '" + user_id + "');";
                String availabilityQuery = "UPDATE books SET availability = false WHERE id = '" + book_id + "'";
                String changeQuery = "UPDATE books SET lentBy = '" + userName.getText() + "' WHERE id = '" + book_id + "'";
                if (DatabaseHandler.executeAction(query) && DatabaseHandler.executeAction(availabilityQuery) &&
                        DatabaseHandler.executeAction(changeQuery)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Operation is successfull");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Operation failed");
                    alert1.showAndWait();
                }
            }
        }
    }


    private  void clearBookCache() {
        bookAuthor.setText("");
        bookTitle.setText("");
        bookAvailability.setText("");
    }
    private void clearDeleteBookCache(){
        deleteAuthor.setText("");
        deleteTitle.setText("");
    }
    private void clearMemberCache() {
        userName.setText("");
        userMobile.setText("");
        userEmail.setText("");

    }



}
