package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the logics for the modify Part scene
 *
 * @author  Linmei Mills
 *
 * */
public class ModifyPartSceneController implements Initializable {
    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private RadioButton outsourcedBtn;

    @FXML
    private Label MachineIdOrCompanyLabel;

    @FXML
    private TextField partIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField invField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField maxField;

    @FXML
    private TextField dynamicField;

    @FXML
    private TextField minField;

    private Part selectedRow;

    /**
     * This method sets a text field label to machine ID when it is a InHouse type part.
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    public void modifyInHouseType(ActionEvent event) {
        MachineIdOrCompanyLabel.setText("Machine ID");
    }

    /**
     * This method sets a text field label to company name when it is a outsourced type part.
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    public void modifyOutsourcedType(ActionEvent event) {
        MachineIdOrCompanyLabel.setText("Company Name");
    }

    /**
     * This method saves modified part to inventory.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     */
    @FXML
    public void savePart(ActionEvent event) throws IOException {

        if (containMachineId()) {
            if(!areValidInputs(Validator.isInteger(dynamicField.getText()))){
                Validator.displayInvalidInput("Exception: Name can not be empty\n Price needs to be double\n Inv, Max, Min and Machine ID need to be integers");
            } else {
                int inv = Integer.parseInt(invField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                if(!(inv <= max && min <= max && inv >= min)){
                    Validator.displayError("Note: Inv value has to be between min and Man / Min can not be greater than max");
                } else {
                    InHouse part = new InHouse(selectedRow.getId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText()));
                    int index = findIndex();
                    Inventory.updatePart(index, part);
                    backToMainScene(event);
                }
            }
        }
        if (containCompanyName()) {
            if(!areValidInputs(!Validator.isEmpty(dynamicField.getText()))){
            Validator.displayInvalidInput("Exception: Name and Company Name can not be empty\n Price needs to be double\n Inv, Max, and Min need to be integers");
            } else {
                int inv = Integer.parseInt(invField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());

                if(!(inv <= max && min <= max && inv >= min)){
                    Validator.displayError("Note: Inv value has to be between min and Man / Min can not be greater than max");
                } else {
                    Outsourced part = new Outsourced(selectedRow.getId(), name, price, inv, max, min, dynamicField.getText());
                    int index = findIndex();
                    Inventory.updatePart(index, part);
                    backToMainScene(event);
                }
            }
        }

    }

    /**
     * This method sets current scene to main scene.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException exception occur when the fxml file is not found
     */
    @FXML
    public void backToMainScene(ActionEvent event) throws IOException {
        Parent main = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(main);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    /**
     * This method displays all info on modify part scene for the item user has selected.
     * @param url It is a location used to resolve relative paths for the root project, or null if the location is null
     * @param resourceBundle The resource used to localize the root project, or null if the root object was not located
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPartFields();
        if (isInHouse()) {
            setInHouseFields();
        }
        if (isOutsourced()) {
            setOutSourcedFields();
        }
    }

    //checks if the object is an InHouse type object
    private boolean isInHouse() {
        return selectedRow instanceof InHouse;
    }

    //checks if the object in an Outsourced type object
    private boolean isOutsourced() {
        return selectedRow instanceof Outsourced;
    }

    //sets the value of the part item on table view
    private void setPartFields() {
        selectedRow = MainSceneController.partSelectedRow;

        partIdField.setText(String.valueOf(selectedRow.getId()));
        nameField.setText(selectedRow.getName());
        invField.setText(String.valueOf(selectedRow.getStock()));
        priceField.setText(String.valueOf(selectedRow.getPrice()));
        maxField.setText(String.valueOf(selectedRow.getMax()));
        minField.setText(String.valueOf(selectedRow.getMin()));
    }

    //sets the text field label to company name when it is an InHouse part type
    private void setOutSourcedFields() {
        outsourcedBtn.setSelected(true);
        MachineIdOrCompanyLabel.setText("Company Name");
        dynamicField.setText(((Outsourced) selectedRow).getCompanyName());
    }

    //sets the text field label to machine ID when it is an outsourced part type
    private void setInHouseFields() {
        inHouseBtn.setSelected(true);
        MachineIdOrCompanyLabel.setText("Machine ID");
        dynamicField.setText(String.valueOf(((InHouse) selectedRow).getMachineId()));
    }

    //checks whether this field is labeled as a machine id
    private boolean containMachineId() {
        return MachineIdOrCompanyLabel.getText().equalsIgnoreCase("Machine ID");
    }
    //checks whether this field is labeled as a company name
    private boolean containCompanyName() {
        return MachineIdOrCompanyLabel.getText().equalsIgnoreCase("Company Name");
    }

    //validates user's inputs
    private boolean areValidInputs(boolean dynamicField) {
        return Validator.isInteger(invField.getText()) && Validator.isDouble(priceField.getText())
                && Validator.isInteger(maxField.getText()) && Validator.isInteger(minField.getText())
                && !Validator.isEmpty(nameField.getText()) && dynamicField;
    }

    // find the index of a selected part from the inventory
    private int findIndex() {
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (Inventory.getAllParts().get(i).getId() == selectedRow.getId()) {
                return i;
            }
        }
        return -1;
    }
}