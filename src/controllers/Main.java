package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(new Main().getClass().getResource("fxml/addPartsOutsourced.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addPartsScene.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/mainScene.fxml"));
        BorderPane mainPane = fxmlLoader.load();
        mainPane.setCenter(null);
        primaryStage.setScene(new Scene(mainPane, 1300, 400));
//      primaryStage.setScene(new Scene(root, 1000, 400));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
