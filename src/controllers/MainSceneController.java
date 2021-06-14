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

/** This class contains all the logics to create the Main Scene UI.
 It contains two tables(Parts and Product); allows user to search, add, modify or delete a part or a product.
 *
 * @author Linmei Mills
 *
 * */
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

    /**
     * when user select a row from part table, the result will be assigned in here.
     */
    public static Part partSelectedRow;

    /**
     * when user select a row from product table, the result will be assigned in ere
     */
    public static Product productSelectedRow;
    /**
     * This method set add part UI where user can add the details of a part.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     * */
    @FXML
    public void addPartBtnClicked(ActionEvent event) throws IOException {
        setScene(event,"fxml/addPartsScene.fxml");
    }

    /**
     * This method closes the the program.
     It prompts a dialog window to ensure whether a user wants to exit.
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void exitBtnClicked(ActionEvent event) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        Validator.displayExitConfirmation();
        if(Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK){
            Inventory.deletePart(partSelectedRow);
            stage.close();
        }
    }

    /**
     * This method set a modify UI for user to modify her/his selected part item.
     It prompts a dialog window when no row is selected
     * @param event an event indicates a component-defined action occurred
     * @throws IOException catch the error when the modify fxml is not found.
     * */
    @FXML
    public void partModifyBtnClicked(ActionEvent event) throws IOException {
        partSelectedRow = partsTable.getSelectionModel().getSelectedItem();
        if(partSelectedRow == null)
            Validator.displayRowNotSelected();

        else
            setScene(event, "fxml/modifyPartsScene.fxml");
    }

    /**
     * This method delete a part item.
     It prompts a dialog window when no row is selected
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void partDeleteClicked(ActionEvent event) {
        partSelectedRow = partsTable.getSelectionModel().getSelectedItem();
        if(partSelectedRow == null)
            Validator.displayRowNotSelected();
        else Validator.displayDeleteConfirmation();
        if(Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK){
            Inventory.deletePart(partSelectedRow);
        }if(Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK){
            Inventory.deletePart(partSelectedRow);
        }
    }

    /**
     * This method perform a search by user enter a part ID, a part name, or a partial part name.
     It based on user data to determine whether a user entered a integer or string.
     If it is a int, search by ID. otherwise, name search is performed.
     Display the result.
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void searchPartByIdOrName(KeyEvent event) {
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

    /**
     * This method checks whether the key is entered with a enter key
     * */
    private boolean isEntered(KeyEvent event){
        return event.getCode().equals(KeyCode.ENTER);
    }

    /**
     * this method contains logic to lookup the part by name from Inventory.
     * If nothing find, display a dialog window with not found message
     * */
    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(searchPartField.getText());
        if(result.size() > 0){
            partsTable.setItems(result);
        }
        else Validator.displayPartNotFound();
    }

    /**
     * this method contains logic to lookup the part by ID from Inventory.
     * If nothing find, display a dialog window with not found message
     * */
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

    //This method checks whether the search text entered by user is a string for Part
    private boolean isPartString() {
        return searchPartField.getText() != null && searchPartField.getText().matches("^[a-zA-Z\\s]*$");
    }

    //This method checks whether the search text entered by user is a number for Part
    private boolean isPartNumeric(){
        return searchPartField != null && searchPartField.getText().matches("^[0-9]*$");
    }

    //This method checks whether the search text entered by user is a string for Product
    private boolean isProdString() {
        return searchProductField.getText() != null && searchProductField.getText().matches("^[a-zA-Z\\s]*$");
    }

    //This method checks whether the search text entered by user is a number for Product
    private boolean isProdNumeric(){
        return searchProductField != null && searchProductField.getText().matches("^[0-9]*$");
    }


    /**
     * This method perform a search by user enter a product ID, a product name, or a partial product name.
     It based on user data to determine whether a user entered a integer or string.
     If it is a int, search by ID. otherwise, name search is performed.
     Display the result
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void searchProductByIdOrName(KeyEvent event) {
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

    /**
     * This method performs the logic to search the product name based on user entry
     * displays the result if it is found.
     * otherwise, display a dialog window says product is not found
     * */
    private void searchedProdByName() {
        ObservableList result = Inventory.lookupProduct(searchProductField.getText());
        if(result.size() > 0){
            productTable.setItems(result);
        }
        else Validator.displayProdNotFound();
    }

    /**
     * This method performs the logic to search the product ID based on user entry
     * displays the result if it is found.
     * otherwise, display a dialog window says product is not found*/
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

    /**
     * This method sets add a product UI where a user can add the details of a product.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     * */
    @FXML
    public void addProdBtnClicked(ActionEvent event) throws IOException {
        setScene(event,"fxml/addProductScene.fxml");
    }

    /**
     * This method sets modify a product UI where a user can modify a selected product.
     If no product is selected, displays a dialog window to remind user to select one.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     * */
    @FXML
    public void prodModifyBtnClicked(ActionEvent event) throws IOException {
        productSelectedRow = productTable.getSelectionModel().getSelectedItem();
        if(productSelectedRow == null) {
            Validator.displayRowNotSelected();
        }
        setScene(event,"fxml/modifyProductScene.fxml");

    }

    /**
     * This method performs a deletion action based on a product is selected by user.
     * it prompts user to delete the product associated parts if this product contains parts
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     * */
    @FXML
    public void prodBtnDeleteClicked(ActionEvent event) throws IOException { // fix me, how to delete associated parts!!!!
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

    //check whether the product user selected contains parts
    private boolean hasParts(Product prod) {
       return prod.getAllAssociatedParts().size() > 0;
    }

    /**
     * This methods is called to initialize a controller after its root element has been completely processed.
     * @param url It is a location used to resolve relative paths for the root project, or null if the location is null
     * @param resourceBundle The resource used to localize the root project, or null if the root object was not located
     * */
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

    /**
     * set a scene based on this particular action and fxml path passed over to the params.
     * @param event an event indicates a component-defined action occurred
     * @param s the file path where the fxml is located at
     * @throws IOException it happens when the fxml file is not found
     */
    private void setScene(ActionEvent event, String s) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(s));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
