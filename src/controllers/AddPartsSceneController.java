package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;

import java.io.IOException;
/**
 * This class provides the logics for the add Part scene
 *
 * @author  Linmei Mills
 *
 * */
public class AddPartsSceneController {
    @FXML
    private Label MachineIdOrCompanyLabel;

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

//    private static final String COMPANY_NAME = "Company Name";
//    private static final String MACHINE_ID = "Machine ID";

    /**This method saves a new part when user hits the save button.
     User's inputs are evaluated before save it to the inventory.
     Once the saved it to Inventory, this method also sets the scene to the Main scene
     * @param actionEvent an event indicates a component-defined action occurred
     * @throws IOException catch the exception when fxml file is not find when returning back to the Main scene
     * */
    @FXML
    public void savePart(ActionEvent actionEvent) throws IOException {
        String name = "";
        int inv = 0;
        int max = 0;
        int min = 0;
        double price = 0;
        if (MachineIdOrCompanyLabel.getText().equalsIgnoreCase("Machine ID")) {
            if(!areValidInputs(Validator.isInteger(dynamicField.getText()))){
                Validator.displayInvalidInput("Exception: Name can not be empty\n Price needs to be double\n Inv, Max, Min and Machine ID need to be integers");
            } else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min = Integer.parseInt(minField.getText());

                if(!(inv <= max && min <= max && inv >= min)){
                    Validator.displayError("Note: Inv value has to be between min and Man / Min can not be greater than max");
                }else {
                    Inventory.addPart(new InHouse(Main.getUniquePartId(), name, price, inv, min, max, Integer.parseInt(dynamicField.getText())));
                    backToMainScene(actionEvent);
                }
            }
        }
        if (MachineIdOrCompanyLabel.getText().equalsIgnoreCase("Company Name")) {
            if(!areValidInputs(!Validator.isEmpty(dynamicField.getText()))){
                    Validator.displayInvalidInput("Exception: Name and Company Name can not be empty\n Price needs to be double\n Inv, Max, and Min need to be integers");
            } else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min= Integer.parseInt(minField.getText());
                if(!(inv <= max && min <= max && inv >= min)){
                    Validator.displayError("Note: Inv value has to be between min and Man / Min can not be greater than max");
                }else {
                    Inventory.addPart(new Outsourced(Main.getUniquePartId(), name, price, inv, min, max, dynamicField.getText()));
                    backToMainScene(actionEvent);
                }
            }
        }

    }

    /**This method sets the scene to Main scene.
     * @param event an event indicates a component-defined action occurred
     * @throws IOException catch the exception when the fxml file is not found
     * */
    @FXML
    public void backToMainScene(ActionEvent event) throws IOException {
        Parent main = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(main);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * This method sets the label to machine id when a user selects inHouse type part.
     * @param actionEvent an event indicates a component-defined action occurred
     * */
    public void addInHouseType(ActionEvent actionEvent) {
        MachineIdOrCompanyLabel.setText("Machine ID");
    }

    /**
     * This method sets the label to company name when a user selects outsourced type part.
     * @param actionEvent an event indicates a component-defined action occurred
     * */
    public void addOutsourcedType(ActionEvent actionEvent) {
        MachineIdOrCompanyLabel.setText("Company Name");
    }

    //validates user's inputs
    private boolean areValidInputs(boolean dynamicField) {
        return Validator.isInteger(invField.getText()) && Validator.isDouble(priceField.getText()) && Validator.isInteger(maxField.getText())
                && Validator.isInteger(minField.getText()) && !Validator.isEmpty(nameField.getText()) && dynamicField;
    }
}
