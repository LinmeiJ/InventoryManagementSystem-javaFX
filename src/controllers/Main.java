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

/**
 * This class create the main/first scene.
 * <p>
 * Future Enhancement: User is able to add multiple part/product without switch scenes they are done entering all the parts/products to save to the inventory.
 * <p>
 * Javadoc location: InventoryManagementSystem/src/javadoc
 *
 * @author Linmei Mills
 */
public class Main extends Application {
    public static int partUId = 1; //part ID starts from 1
    public static int prodUId = 1; //product ID starts from 1

    /**
     * This method is a main entry point for all JavaFX application.
     * it creates the main/first scene
     *
     * @param primaryStage it helps with set the application scene
     * @throws Exception the mainScene file is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This method is a main entry point for all Java program to run.
     * It initializes the values for parts table and products table and launch this application
     *
     * @param args It stores Java command line arguments and is an array of type java.lang.String class
     */
    public static void main(String[] args) {
        initialPartsItems();
        initialProductItems();
        launch(args);
    }

    /**
     * this method is for generating a unique ID for a Part.
     *
     * @return the id
     */
    public static int getUniquePartId() {
        return partUId++;
    }

    /**
     * this method is for generating a unique ID for a Product.
     *
     * @return the id
     */
    public static int getUniqueProdId() {
        return prodUId++;
    }

    /**
     * this method creates Part object and stores to Inventory.
     */
    private static void initialPartsItems() {
        Inventory.addPart(new InHouse(getUniquePartId(), "Brakes", 15.00, 10, 1, 15, 111));
        Inventory.addPart(new InHouse(getUniquePartId(), "Wheel wheel wheel", 11.00, 10, 1, 15, 112));
        Inventory.addPart(new Outsourced(getUniquePartId(), "Beat", 15.00, 10, 1, 15, "Supper Bikes"));
    }

    /**
     * this method creates Product object and stores to Inventory.
     * Create 2 products with adding associated parts.
     */
    private static void initialProductItems() {
        Product bike = new Product(getUniqueProdId(), "Kids Bike", 10, 100, 1, 10);
        bike.addAssociatedPart(Inventory.getAllParts().get(1));
        Inventory.addProduct(bike);

        Product kicks = new Product(getUniqueProdId(), "kicks", 9, 79.99, 1, 10);
        kicks.addAssociatedPart(Inventory.getAllParts().get(0));
        Inventory.addProduct(kicks);

        Product tickles = new Product(getUniqueProdId(), "tickles", 8, 12, 1, 10);
        tickles.addAssociatedPart(Inventory.getAllParts().get(1));
        tickles.addAssociatedPart(Inventory.getAllParts().get(2));
        Inventory.addProduct(tickles);

    }
}
