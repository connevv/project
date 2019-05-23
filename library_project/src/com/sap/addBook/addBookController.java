package com.sap.addBook;

import com.sap.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addBookController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField btitle;
    @FXML
    private TextField bid;
    @FXML
    private TextField bauthor;
    @FXML
    private TextField bpublishedDate;

    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBookButtonAction(ActionEvent actionEvent) {
        String id = bid.getText();
        String title = btitle.getText();
        String author = bauthor.getText();
        String date = bpublishedDate.getText();
        boolean flag = true;
        String regex = "^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Date format error");
            alert.showAndWait();
            flag = false;
        }
        if (flag){
            String query = "INSERT INTO `library_dp`.`books` (id, title, author, publishedDate) VALUES ('" + id + "', '" +
                    title + "', '" + author + "', '" + date + "');";
            if (id.isEmpty() || title.isEmpty() || author.isEmpty() || date.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please enter data in all fields");
                alert.showAndWait();
                return;
            }
            if (DatabaseHandler.executeAction(query)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed to add book");
                alert.showAndWait();
            }
        }
        }

    public void cancelButtonAction(ActionEvent actionEvent) { {
            ((Stage) rootPane.getScene().getWindow()).close();
        }
    }
}
