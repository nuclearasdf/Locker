package com.virus;


import com.virus.security.SearchDirectory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application{

    public static void main(String[] args) {
	// write your code here
        Thread t1 = new Thread(() -> {
            launch(args);
        });

        Thread t2 = new Thread(() -> {
            SearchDirectory searchDirectory= new SearchDirectory();
        });
        t1.start();
        t2.start();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Pikicast");

        stage.show();
    }
}
