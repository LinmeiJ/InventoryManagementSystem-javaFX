package controllers;

import controllers.fxml.CommonAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Button exitId;

    @FXML
    private AnchorPane partPane;

    @FXML
    private TextField searchPartField;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private Button partAdd;

    @FXML
    private Button partModify;

    @FXML
    private Button partDelete;

    @FXML
    private AnchorPane partPane1;

    @FXML
    private TextField searchProductField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInv;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private Button prodAdd;

    @FXML
    private Button prodModify;

    @FXML
    private Button prodDelete;

    @FXML
    void addPartBtnClicked(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(new Main().getClass().getResource("fxml/addPartsScene.fxml"));
        var scene = new Scene(add);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void addProdBtnClicked(ActionEvent event) {

    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void partDeleteClicked(ActionEvent event) {

    }

    @FXML
    void partModifyBtnClicked(ActionEvent event) {

    }

    @FXML
    void prodBtnDeleteClicked(ActionEvent event) {

    }

    @FXML
    void prodModifyBtnClicked(ActionEvent event) {

    }

    @FXML
    void searchPartByIdOrName(KeyEvent event) {
        String text = searchPartField.getText();
        if(event.getCode().equals(KeyCode.ENTER) && isNumeric(text))
        {
            searchedPartById();
        }
        else if(event.getCode().equals(KeyCode.ENTER) && isString(text)){
            searchedPartByName();
        }
        else{
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(searchPartField.getText());
        if(result == null){
            CommonAlert.displayAlert(1);
        }
        else{
            partsTable.setItems(result);
        }

    }

    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(searchPartField.getText()));
        if(part == null) {
            CommonAlert.displayAlert(1);
        }
        else{
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            partsTable.setItems(result);
        }
    }

    @FXML
    void searchProductByIdOrName(KeyEvent event) {
        String text = searchProductField.getText();
        if(event.getCode().equals(KeyCode.ENTER) && isNumeric(text))
        {
            searchedProdById();
        }
        else if(event.getCode().equals(KeyCode.ENTER) && isString(text)){
            searchedProdByName();
        }
        else{
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    private void searchedProdByName() {
        ObservableList result = Inventory.lookupProduct(searchProductField.getText());
        if(result == null){
            CommonAlert.displayAlert(1);
        }
        else{
            productTable.setItems(result);
        }
    }

    private void searchedProdById() {
        var prod = Inventory.lookupProduct(Integer.parseInt(searchProductField.getText()));
        if(prod == null) {
            CommonAlert.displayAlert(1);
        }
        else{
            ObservableList<Product> result = FXCollections.observableArrayList();
            result.add(prod);
            productTable.setItems(result);
        }
    }

    private boolean isString(String text) {
        return text != null && text.matches("^[a-zA-Z]*$");
    }

    private boolean isNumeric(String text){
        return text != null && text.matches("^[0-9]*$");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        createPartsItems();
        partsTable.setItems(Inventory.getAllParts());

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        createProductItems();
        productTable.setItems(Inventory.getAllProducts());

    }

    private void createPartsItems() {
        Inventory.addPart(new InHouse(1, "Brakes", 15.00, 10, 1, 15, 111));
        Inventory.addPart(new InHouse(2, "Wheel", 11.00, 10, 1, 15, 112));
        Inventory.addPart(new Outsourced(3, "Beat", 15.00, 10, 1, 15, "Supper Bikes"));
    }

    private void createProductItems(){
       Inventory.addProduct(new Product(1, "Giant Bike", 5, 299.99, 1, 10));
       Inventory.addProduct(new Product(2, "Kids Bike", 10, 99.99, 1, 10));
       Inventory.addProduct(new Product(3, "kicks", 10, 99.99, 1, 10));
    }

}
