package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    public static int partUId = 1;
    public static int prodUId = 1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        initialPartsItems();
        initialProductItems();
        launch(args);
    }

    public static int getUniquePartId(){
        return partUId++;
    }

    public static int getUniqueProdId(){
        return prodUId++;
    }

    private static void initialPartsItems() {
        Inventory.addPart(new InHouse(getUniquePartId(), "Brakes", 15.00, 10, 1, 15, 111));
        Inventory.addPart(new InHouse(getUniquePartId(), "Wheel wheel wheel", 11.00, 10, 1, 15, 112));
        Inventory.addPart(new Outsourced(getUniquePartId(), "Beat", 15.00, 10, 1, 15, "Supper Bikes"));
    }

    private static void initialProductItems(){
        Product bike = new Product(getUniqueProdId(), "Kids Bike", 10, 99.99, 1, 10);
        bike.addAssociatedPart(Inventory.getAllParts().get(1));
        Inventory.addProduct(bike);

        Product kicks = new Product(getUniqueProdId(), "kicks", 10, 99.99, 1, 10);
        kicks.addAssociatedPart(Inventory.getAllParts().get(0));
        Inventory.addProduct(kicks);

    }
}
