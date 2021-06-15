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
 * This class provides the logics for the add product scene.
 *
 * @author  Linmei Mills
 */
/**add enhancement here!!!!!(fix me) such as if the user wants to add multiple product without going back to main scene, this is where the logs should be sit in*/
public class AddProductController implements Initializable {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TextField partSearchField;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableColumn<Part, Integer> associatePartId;

    @FXML
    private TableColumn<Part, String> associatePartName;

    @FXML
    private TableColumn<Part, Integer> associateInv;

    @FXML
    private TableColumn<Part, Double> associatePrice;

    // stores all associated parts for products
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**This method saves a new product when user hits the save button.
     User's inputs are evaluated before save it to the inventory.
     Once the saved it to Inventory, this method also sets the scene to the Main scene
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void addPartToProdClicked(ActionEvent event) {
        Part selectedPartTableRow = partTable.getSelectionModel().getSelectedItem();
        if (selectedPartTableRow == null) {
            Validator.displayRowNotSelected();
        } else {
            associatedParts.add(selectedPartTableRow);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**
     * This method sets the Main scene when a user hits the cancel button.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException catch the exception when the fxml file for main scene is not found
     * */
    @FXML
    public void cancelBtnClicked(ActionEvent event) throws IOException {
        returnBackToMainScene(event);
    }

    /**
     * This method removes a associated part from the associated table view.
     When a selected row is not found, display a dialog window to ask user to select a row.
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void removePartFromProdClicked(ActionEvent event) {
        Part selectedAssocPart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedAssocPart == null) {
            Validator.displayRowNotSelected();
        } else {
            int id = selectedAssocPart.getId();
            for (int i = 0; i < associatedParts.size(); i++) {
                if (associatedParts.get(i).getId() == id) {
                    associatedParts.remove(associatedParts.get(i));
                }
            }
        }
    }

    /**
     * This method saves a new product to Inventory.
     It checks the user's inputs before saving to the inventory. Once the new product is saved,
     return the screen back to Main scene.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException catch the exception when the main scene fxml file is not find
     * */
    @FXML
    public void saveProdClicked(ActionEvent event) throws IOException {
        if(!areValidInputs()){
            Validator.displayInvalidInput("Exception: Name can not be empty\n Price needs to be double\n Inv, Max, and Min need to be integers");
        } else {
            String name = productNameField.getText();
            int stock = Integer.parseInt(productInvField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if(!(stock <= max && min <= max)){
                Validator.displayInvalidLogic("Note: Stock field or Min field can not be greater than max");
            } else {
                Product prod = new Product(Main.getUniqueProdId(), name, stock, price, min, max);
                for (Part part : associatedParts) {
                    prod.addAssociatedPart(part);
                }
                Inventory.addProduct(prod);
                returnBackToMainScene(event);
            }
        }
    }

    /**This method sets the scene to Main scene.
     * @param actionEvent an event indicates a component-defined action occurred
     * @throws IOException catch the exception when the fxml file is not found
     * */
    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method perform a search by user enter a part ID, a part name, or a partial part name.
     It based on user data to determine whether a user entered an integer or a string.
     If it is a int, search by ID. otherwise, name search is performed.
     Display the result in the tableview.
     * @param event an event indicates a component-defined action occurred
     * */
    @FXML
    public void searchBtnEntered(KeyEvent event) {
        if (isEntered(event) && isPartNumeric()) {
            searchedPartById();
        } else if (isEntered(event) && isPartString()) {
            searchedPartByName();
        } else {
            partTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method gets all parts from inventory and displays it on the scene.
     * @param url It is a location used to resolve relative paths for the root project, or null if the location is null
     * @param resourceBundle The resource used to localize the root project, or null if the root object was not located
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(Inventory.getAllParts());

        associatePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associateInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    //check the key event whether the user hits the enter key
    private boolean isEntered(KeyEvent event) {
        return event.getCode().equals(KeyCode.ENTER);
    }

    //search the part by name from Inventory. when part is not found, display a not found dialog window
    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(partSearchField.getText());
        if (result.size() > 0) {
            partTable.setItems(result);
        } else Validator.displayPartNotFound();
    }

    //search the part by ID. if not found, displays a not fund dialog window to user, otherwise display the result
    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(partSearchField.getText()));
        if (part == null) {
            Validator.displayPartNotFound();
        } else {
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            partTable.setItems(result);
        }
    }

    //check the search entry whether is a string
    private boolean isPartString() {
        return partSearchField.getText() != null && partSearchField.getText().matches("^[a-zA-Z\\s]*$");
    }

    //check the search entry whether is a digit
    private boolean isPartNumeric() {
        return partSearchField != null && partSearchField.getText().matches("^[0-9]*$");
    }

    //validates user's inputs
    private boolean areValidInputs() {
        return Validator.isInteger(productInvField.getText()) && Validator.isDouble(productPriceField.getText())
                && Validator.isInteger(productMaxField.getText()) && Validator.isInteger(productMinField.getText())
                && !Validator.isEmpty(productNameField.getText());
    }
}
