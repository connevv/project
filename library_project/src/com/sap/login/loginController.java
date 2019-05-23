package com.sap.login;


import com.jfoenix.controls.JFXPasswordField;
import com.sap.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginController implements Initializable {
 @FXML
 private JFXTextField username;
 @FXML
 private JFXPasswordField password;
 @FXML
 private Label label;

 @Override
 public void initialize(URL location, ResourceBundle resources) {

 }


 public void handleCancelButtonAction(ActionEvent actionEvent) {
  System.exit(0);
 }

 void loadMainWindow() {
  try {
   Parent parent = FXMLLoader.load(getClass().getResource("/com/sap/mainwindow/mainWindow.fxml"));
   Stage stage = new Stage(StageStyle.DECORATED);
   stage.setTitle("User form");
   stage.setScene(new Scene(parent));
   stage.show();

  } catch (IOException ex) {
   Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
  }
 }

 private void closeStage() {
  ((Stage) username.getScene().getWindow()).close();
 }

 public void handleLoginButtonAction(ActionEvent actionEvent) {
  String mUsername = username.getText();
  String mPassword = password.getText();
  String query = "SELECT * FROM credentials;";
  ResultSet resultSet = DatabaseHandler.executeQuery(query);
  try {
   assert resultSet != null;
   while (resultSet.next()) {
    String credentialUsername = resultSet.getString("username");
    String credentialPassword = resultSet.getString("password");
    if ((mUsername.equals(credentialUsername)) && (mPassword.equals(credentialPassword))) {
     closeStage();
     loadMainWindow();
    } else {
     label.setText("Invalid credentials");
    }
   }

  } catch (SQLException e1) {
   e1.printStackTrace();
  }
 }
}


