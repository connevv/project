package com.sap.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.sap.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private JFXPasswordField oldPassword;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField repeatPassword;

    public void savePasswordButtonHandle(ActionEvent actionEvent) {
        String new_password = newPassword.getText();
        String passwordQuery = "UPDATE credentials SET password = '" + new_password + "' WHERE (idcredentials = '1');";
        DatabaseHandler.executeAction(passwordQuery);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Password changed successfully");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void closeButtonHandle(ActionEvent actionEvent) {
        ((Stage) oldPassword.getScene().getWindow()).close();
    }

    public void oldPasswordHandleButton(ActionEvent actionEvent) {
        String old_password = oldPassword.getText();
        String query = "SELECT * FROM credentials;";
        ResultSet resultSet = DatabaseHandler.executeQuery(query);
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                String credentialPassword = resultSet.getString("password");
                if (!old_password.equals(credentialPassword)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void repeatPasswordButtonHandle(ActionEvent actionEvent) {
        String new_password = newPassword.getText();
        String repeat_password = repeatPassword.getText();
        if (!new_password.equals(repeat_password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wrong password");
            alert.showAndWait();
        }
    }
}

