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
        if(isEntered(event) && isPartNumeric())
        {
            searchedPartById();
        }
        else if(isEntered(event) && isPartString()){
            searchedPartByName();
        }
        else{
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    private boolean isEntered(KeyEvent event){
        return event.getCode().equals(KeyCode.ENTER);
    }
    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(searchPartField.getText());
        if(result.size() > 0){
            partsTable.setItems(result);
        }
        else CommonAlert.displayAlert(1);
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
        if(isEntered(event) && isProdNumeric())
        {
            searchedProdById();
        }
        else if(isEntered(event)&& isProdString()){
            searchedProdByName();
        }
        else{
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    private void searchedProdByName() {
        ObservableList result = Inventory.lookupProduct(searchProductField.getText());
        if(result.size() > 0){
            productTable.setItems(result);
        }
        else CommonAlert.displayAlert(2);
    }

    private void searchedProdById() {
        var prod = Inventory.lookupProduct(Integer.parseInt(searchProductField.getText()));
        if(prod == null) {
            CommonAlert.displayAlert(2);
        }
        else{
            ObservableList<Product> result = FXCollections.observableArrayList();
            result.add(prod);
            productTable.setItems(result);
        }
    }

    private boolean isPartString() {
        return searchPartField.getText() != null && searchPartField.getText().matches("^[a-zA-Z]*$");
    }

    private boolean isPartNumeric(){
        return searchPartField != null && searchPartField.getText().matches("^[0-9]*$");
    }

    private boolean isProdString() {
        return searchProductField.getText() != null && searchProductField.getText().matches("^[a-zA-Z]*$");
    }

    private boolean isProdNumeric(){
        return searchProductField != null && searchProductField.getText().matches("^[0-9]*$");
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
