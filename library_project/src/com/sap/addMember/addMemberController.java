package com.sap.addMember;

import com.sap.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class addMemberController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField memberId;
    @FXML
    private TextField memberFName;
    @FXML
    private TextField memberLName;
    @FXML
    private TextField memberMobile;
    @FXML
    private TextField memberEmail;

    private DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMemberButtonAction(ActionEvent actionEvent) {
        String mId = memberId.getText();
        String mFName = memberFName.getText();
        String mLName = memberLName.getText();
        String mMobile = memberMobile.getText();
        String mEmail = memberEmail.getText();
        boolean flag = true;
        boolean flag2 = true;
        String regexMobile = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regexMobile);
        Matcher matcher = pattern.matcher(mMobile);
        if (!matcher.matches()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Mobile format error");
            alert.showAndWait();
            flag = false;
        }
        Pattern pattern1 = Pattern.compile(regexEmail);
        Matcher matcher1 = pattern1.matcher(mEmail);
        if (!matcher1.matches()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Email format error");
            alert.showAndWait();
            flag2 = false;
        }
       if (flag && flag2 ){
        String query = "INSERT INTO `library_dp`.`users` (id, first_name, last_name, mobile, email) VALUES ('" + mId + "', '" +
                mFName + "', '" + mLName + "', '" + mMobile + "', '" + mEmail + "');";

        if (mId.isEmpty() || mFName.isEmpty() || mLName.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter data in all fields");
            alert.showAndWait();
            return;
        }
        if (DatabaseHandler.executeAction(query)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully added");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to add member");
            alert.showAndWait();
        }

        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
