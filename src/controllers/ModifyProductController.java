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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the logics for the modify Product scene
 *
 * @author Linmei Mills
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField modifyProdID;

    @FXML
    private TextField modifyProdNameField;

    @FXML
    private TextField modifyProdInvField;

    @FXML
    private TextField modifyProdPriceField;

    @FXML
    private TextField modifyProdMaxField;

    @FXML
    private TextField modifyProdMinField;

    @FXML
    private TextField partSearchField;

    @FXML
    private TableView<Part> modifyPartTable;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView<Part> modifyAssociatedPartTable;

    @FXML
    private TableColumn<Part, Integer> associatePartId;

    @FXML
    private TableColumn<Part, String> associatePartName;

    @FXML
    private TableColumn<Part, Integer> associateInv;

    @FXML
    private TableColumn<Part, Double> associatePrice;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Product originalRow = MainSceneController.productSelectedRow;

    /**
     * This method search a product(s) baserd on user's input in the search text field.
     *
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    public void searchBtnEntered(KeyEvent event) {
        if (isEntered(event) && isPartNumeric()) {
            searchedPartById();
        } else if (isEntered(event) && isPartString()) {
            searchedPartByName();
        } else {
            modifyPartTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This methods adds a part to associated part table for this product.
     *
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    public void addPartToProdClicked(ActionEvent event) {
        try {
            associatedParts.add(modifyPartTable.getSelectionModel().getSelectedItem());
            modifyAssociatedPartTable.setItems(associatedParts);
        } catch (Exception e) {
            Validator.displayRowNotSelected();
        }
    }

    /**
     * This method removes a part from the associated part table for the product.
     * When no part is selected to be removed, a dialog window displays.
     *
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    public void removePartFromProdClicked(ActionEvent event) {
        Part selectedAssocPart = modifyAssociatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedAssocPart == null) {
            Validator.displayRowNotSelected();
        } else {
            int id = selectedAssocPart.getId();
            if (associatedParts == null) {
                Validator.displayError("no associated part is find");
            } else {
                for (int i = 0; i < associatedParts.size(); i++) {
                    if (associatedParts.get(i).getId() == id) {
                        associatedParts.remove(associatedParts.get(i));
                    }
                }
            }
        }
    }

    /**
     * This method saves the product that is modified by the end user.
     * It validates the user's inputs before save the product to inventory.
     * Once the product is saved, screen will be returned back to Main scene.
     *
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     */
    @FXML
    public void saveProdClicked(ActionEvent event) throws IOException {
        int id = originalRow.getId();

        if (!areValidInputs()) {
            Validator.displayInvalidInput("Exception: Name can not be empty\n Price needs to be double\n Inv, Max, and Min need to be integers");
        } else {
            String name = modifyProdNameField.getText();
            double price = Double.parseDouble(modifyProdPriceField.getText());
            int stock = Integer.parseInt(modifyProdInvField.getText());
            int min = Integer.parseInt(modifyProdMinField.getText());
            int max = Integer.parseInt(modifyProdMaxField.getText());
            if (!(stock <= max && min <= max && stock >= min)) {
                Validator.displayError("Note: Inv value has to be between min and Man / Min can not be greater than max");
            } else {
                Product prod = new Product(id, name, stock, price, min, max);
                int index = findIndex();

                for (Part part : associatedParts) {
                    prod.addAssociatedPart(part);
                }
                Inventory.updateProduct(index, prod);
                returnBackToMainScene(event);
            }
        }
    }

    /**
     * This method returns user to the Main scene.
     *
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     */
    @FXML
    public void cancelBtnClicked(ActionEvent event) throws IOException {
        returnBackToMainScene(event);
    }

    /**
     * This method displays all info on modify product scene for the item user has selected.
     * It display all the fields for the selected product, displays all parts from the inventory,
     * and displays the associated parts in which the product contains.
     *
     * @param url            It is a location used to resolve relative paths for the root project, or null if the location is null
     * @param resourceBundle The resource used to localize the root project, or null if the root object was not located
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProdFields();
        setPartTable();
        setAssociatedPartsTable();
    }

    //set the the parts that are available from Inventory
    private void setPartTable() {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyPartTable.setItems(Inventory.getAllParts());
    }

    //sets all associated parts to the table view
    private void setAssociatedPartsTable() {
        associatePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associateInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = MainSceneController.productSelectedRow.getAllAssociatedParts();
        modifyAssociatedPartTable.setItems(associatedParts);
    }

    //sets all the fields for this product
    private void setProdFields() {
        modifyProdID.setText(String.valueOf(originalRow.getId()));
        modifyProdNameField.setText(originalRow.getName());
        modifyProdInvField.setText(String.valueOf(originalRow.getStock()));
        modifyProdPriceField.setText(String.valueOf(originalRow.getPrice()));
        modifyProdMinField.setText(String.valueOf(originalRow.getMin()));
        modifyProdMaxField.setText(String.valueOf(originalRow.getMax()));
    }


    //this method set the current scene back to the Main scene
    private void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //find the index where this product is stored in the inventory
    private int findIndex() {
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == originalRow.getId()) {
                return i;
            }
        }
        return -1;
    }

    //check whether the enter key is pressed
    private boolean isEntered(KeyEvent event) {
        return event.getCode().equals(KeyCode.ENTER);
    }

    //searches the part name from the inventory. display a dialog window when the part is not found
    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(partSearchField.getText());
        if (result.size() > 0) {
            modifyPartTable.setItems(result);
        } else Validator.displayPartNotFound();
    }

    //searches the part ID from the inventory. Displays a dialog window when the part is not found;
    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(partSearchField.getText()));
        if (part == null) {
            Validator.displayPartNotFound();
        } else {
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            modifyPartTable.setItems(result);
        }
    }

    //checks whether the user's input is an string/name
    private boolean isPartString() {
        return partSearchField.getText() != null && partSearchField.getText().matches("^[a-zA-Z\\s]*$");
    }

    //checks whether the user's input is an id
    private boolean isPartNumeric() {
        return partSearchField != null && partSearchField.getText().matches("^[0-9]*$");
    }


    //validates user's inputs
    private boolean areValidInputs() {
        return Validator.isDouble(modifyProdPriceField.getText()) && Validator.isInteger(modifyProdMaxField.getText())
                && Validator.isInteger(modifyProdMinField.getText()) && Validator.isInteger(modifyProdInvField.getText())
                && !Validator.isEmpty(modifyProdNameField.getText());
    }

}
