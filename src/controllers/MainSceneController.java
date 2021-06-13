package controllers;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Inventory;
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
    private AnchorPane ProdPane;

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

    public static Part partSelectedRow;
    public static Product productSelectedRow;


    @FXML
    void addPartBtnClicked(ActionEvent event) throws IOException {
        setScene(event,"fxml/addPartsScene.fxml");
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void partModifyBtnClicked(ActionEvent event) throws IOException {
        partSelectedRow = partsTable.getSelectionModel().getSelectedItem();
        if(partSelectedRow == null)
            Validator.displayRowNotSelected();

        else
            setScene(event, "fxml/modifyPartsScene.fxml");
    }

    @FXML
    void partDeleteClicked(ActionEvent event) {
        partSelectedRow = partsTable.getSelectionModel().getSelectedItem();
        if(partSelectedRow == null)
            Validator.displayRowNotSelected();
        else Validator.displayDeleteConfirmation();
        if(Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK){
            Inventory.deletePart(partSelectedRow);
        }
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
        else Validator.displayPartNotFound();
    }

    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(searchPartField.getText()));
        if(part == null) {
            Validator.displayPartNotFound();
        }
        else{
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            partsTable.setItems(result);
        }
    }

    private boolean isPartString() {
        return searchPartField.getText() != null && searchPartField.getText().matches("^[a-zA-Z\\s]*$");
    }

    private boolean isPartNumeric(){
        return searchPartField != null && searchPartField.getText().matches("^[0-9]*$");
    }

    private boolean isProdString() {
        return searchProductField.getText() != null && searchProductField.getText().matches("^[a-zA-Z\\s]*$");
    }

    private boolean isProdNumeric(){
        return searchProductField != null && searchProductField.getText().matches("^[0-9]*$");
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
        else Validator.displayProdNotFound();
    }

    private void searchedProdById() {
        var prod = Inventory.lookupProduct(Integer.parseInt(searchProductField.getText()));
        if(prod == null) {
            Validator.displayProdNotFound();
        }
        else{
            ObservableList<Product> result = FXCollections.observableArrayList();
            result.add(prod);
            productTable.setItems(result);
        }
    }



    @FXML
    void addProdBtnClicked(ActionEvent event) throws IOException {
        setScene(event,"fxml/addProductScene.fxml");
    }

    @FXML
    void prodModifyBtnClicked(ActionEvent event) throws IOException {
        productSelectedRow = productTable.getSelectionModel().getSelectedItem();
        if(productSelectedRow == null) {
            Validator.displayRowNotSelected();
        }
        setScene(event,"fxml/modifyProductScene.fxml");

    }

    @FXML
    void prodBtnDeleteClicked(ActionEvent event) throws IOException { // fix me, how to delete associated parts!!!!
       Product productSelectedRow = productTable.getSelectionModel().getSelectedItem();
        Product prod = null;

        if (productSelectedRow.getAllAssociatedParts().size() > 0) {
            Validator.displayProdContainsParts();
        }
        else{
            if (productSelectedRow == null)
                Validator.displayRowNotSelected();
            else
                Validator.displayDeleteConfirmation();


            if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
                for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
                    if (Inventory.getAllProducts().get(i).getId() == productSelectedRow.getId()) {
                        prod = Inventory.getAllProducts().get(i);
                    }
                }
            }
            Inventory.deleteProduct(prod);
        }
    }

    private boolean hasParts(Product prod) {
       return prod.getAllAssociatedParts().size() > 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());
    }

    private void setScene(ActionEvent event, String s) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(s));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
