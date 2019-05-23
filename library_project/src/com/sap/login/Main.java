package com.sap.login;

import com.sap.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Parent parent;
        {
            try {
                parent = FXMLLoader.load(getClass().getResource("login.fxml"));
                stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Book Library Log in");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}