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

    public static final String COMPANY_NAME = "Company Name";
    public static final String MACHINE_ID = "Machine ID";

    @FXML
    void backToMainScene(ActionEvent event) throws IOException {
        Parent main = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(main);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void savePart(ActionEvent actionEvent) throws IOException {
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
            if (Validator.isEmpty(dynamicField.getText())) {
                Validator.displayInvalidInput("Machine ID can not be empty");
            }
            if (Validator.isEmpty(nameField.getText())) {
                Validator.displayInvalidInput("Name field can not be empty");
            }
            else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min = Integer.parseInt(minField.getText());
                if(max < min){
                    Validator.displayInvalidInput("Min can not be greater than Max");
                }else {
                    Inventory.addPart(new InHouse(Main.getUniquePartId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText())));
                    returnBackToMainScene(actionEvent);
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
                Validator.displayInvalidInput("Machine ID can not be empty");
            }
            if (Validator.isEmpty(dynamicField.getText())) {
                Validator.displayInvalidInput("Company name cannot be empty");
            }
            if (Validator.isEmpty(nameField.getText())) {
                Validator.displayInvalidInput("Name field can not be empty");
            }
            else {
                name = nameField.getText();
                inv = Integer.parseInt(invField.getText());
                price = Double.parseDouble(priceField.getText());
                max = Integer.parseInt(maxField.getText());
                min = Integer.parseInt(minField.getText());
                if(max < min){
                    Validator.displayInvalidInput("Min can not be greater than Max");
                }else {
                    Inventory.addPart(new Outsourced(Main.getUniquePartId(), name, price, inv, min, max, dynamicField.getText()));
                    returnBackToMainScene(actionEvent);
                }
            }
        }

    }

    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void addInHouseType(ActionEvent actionEvent) {
        MachineIdOrCompanylabel.setText(MACHINE_ID);
    }

    public void addOutsourcedType(ActionEvent actionEvent) {
        MachineIdOrCompanylabel.setText(COMPANY_NAME);
    }
}
