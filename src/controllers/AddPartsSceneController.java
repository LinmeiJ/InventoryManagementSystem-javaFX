package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label addPartLabel;

    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private ToggleGroup addPartTg;

    @FXML
    private RadioButton outsroucedBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label MaxLabel;

    @FXML
    private Label minLabel;

    @FXML
    private Label MachineIdOrCompanylabel;

    @FXML
    private TextField idFiled;

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

    @FXML
    private Label idLabel;

    @FXML
    private Label invLabel;

    private static final String COMPANY_NAME = "Company Name";
    private static final String MACHINE_ID = "Machine ID";

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
        if (MachineIdOrCompanylabel.getText().equalsIgnoreCase(MACHINE_ID)) {
            if (!Validator.isInteger(invField.getText())) {
                Validator.displayInvalidInput("Inventory(Inv) field is not an integer");
            }
            if (!Validator.isDouble(priceField.getText())) {
                Validator.displayInvalidInput("Price needs to be a double");
            }
            if (!Validator.isInteger(maxField.getText())) {
                Validator.displayInvalidInput("Max needs to be an integer");
            }
            if (!Validator.isInteger(minField.getText())) {
                Validator.displayInvalidInput("Min needs to be an integer");
            }
            if (!Validator.isInteger(dynamicField.getText())) {
                Validator.displayInvalidInput("Machine ID needs to be an integer");
            }
            if (Validator.isEmpty(nameField.getText())) {
                Validator.displayInvalidInput("Name field can not be empty");
            } else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min = Integer.parseInt(minField.getText());
                if (min > max) {
                    Validator.displayInvalidLogic("Min should not be greater than Max");
                }
                if (inv > max) {
                    Validator.displayInvalidLogic("Stock should not be greater than Max");
                } else {
                    Inventory.addPart(new InHouse(Main.getUniquePartId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText())));
                    backToMainScene(actionEvent);
                }
            }
        }
        if (MachineIdOrCompanylabel.getText().equalsIgnoreCase(COMPANY_NAME)) {
            if (!Validator.isInteger(invField.getText())) {
                Validator.displayInvalidInput("Inventory(Inv) field is not an integer");
            }
            if (!Validator.isDouble(priceField.getText())) {
                Validator.displayInvalidInput("Price needs to be a double");
            }
            if (!Validator.isInteger(maxField.getText())) {
                Validator.displayInvalidInput("Max needs to be an integer");
            }
            if (!Validator.isInteger(minField.getText())) {
                Validator.displayInvalidInput("Min needs to be an integer");
            }
            if (Validator.isEmpty(dynamicField.getText())) {
                Validator.displayInvalidInput("Company name cannot be empty");
            }
            if (Validator.isEmpty(nameField.getText())) {
                Validator.displayInvalidInput("Name field can not be empty");
            } else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min = Integer.parseInt(minField.getText());
                if (max < min) {
                    Validator.displayInvalidInput("Min can not be greater than Max");
                } else {
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
        MachineIdOrCompanylabel.setText(MACHINE_ID);
    }

    /**
     * This method sets the label to company name when a user selects outsourced type part.
     * @param actionEvent an event indicates a component-defined action occurred
     * */
    public void addOutsourcedType(ActionEvent actionEvent) {
        MachineIdOrCompanylabel.setText(COMPANY_NAME);
    }
}
