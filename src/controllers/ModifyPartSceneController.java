package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class ModifyPartSceneController implements Initializable {
    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton inHouseBtn;
//
//    @FXML
//    private ToggleGroup addPartTg;

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

    private final static String Machine_ID = "Machine ID";
    private final static String COMPANY_NAME = "Company Name";


    @FXML
    void backToMainScene(ActionEvent event) throws IOException {
        Parent main = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(main);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void modifyInHouseType(ActionEvent event) {
        MachineIdOrCompanyLabel.setText(Machine_ID);
    }

    @FXML
    void modifyOutsourcedType(ActionEvent event) {
        MachineIdOrCompanyLabel.setText(COMPANY_NAME);
    }

    @FXML
    void savePart(ActionEvent event) throws IOException {

        if (containMachineId()) {
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
            } else {
                int inv = Integer.parseInt(invField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                if (min > max) {
                    Validator.displayInvalidLogic("Min should not be greater than Max");
                }
                if (inv > max) {
                    Validator.displayInvalidLogic("Stock should not greater than Max");
                } else {
                    InHouse part = new InHouse(selectedRow.getId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText()));
                    int index = findIndex();
                    Inventory.updatePart(index, part);
                    returnBackToMainScene(event);
                }
            }
        }
        if (containCompanyName()) {
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
            } else {
                int inv = Integer.parseInt(invField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                if (min > max) {
                    Validator.displayInvalidLogic("Min should not be greater than Max");
                }
                if (inv > max) {
                    Validator.displayInvalidLogic("Stock should not be greater than Max");

                } else {
                    Outsourced part = new Outsourced(selectedRow.getId(), name, price, inv, max, min, dynamicField.getText());
                    int index = findIndex();
                    Inventory.updatePart(index, part);
                    returnBackToMainScene(event);
                }
            }
        }

    }

    private int findIndex() {
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (Inventory.getAllParts().get(i).getId() == selectedRow.getId()) {
                return i;
            }
        }
        return -1; //fix me for excepti
    }

    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean containMachineId() {
        return MachineIdOrCompanyLabel.getText().equalsIgnoreCase(Machine_ID);
    }

    private boolean containCompanyName() {
        return MachineIdOrCompanyLabel.getText().equalsIgnoreCase(COMPANY_NAME);
    }

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

    public boolean isInHouse() {
        return selectedRow instanceof InHouse;
    }

    public boolean isOutsourced() {
        return selectedRow instanceof Outsourced;
    }

    private void setPartFields() {
        selectedRow = MainSceneController.partSelectedRow;

        partIdField.setText(String.valueOf(selectedRow.getId()));
        nameField.setText(selectedRow.getName());
        invField.setText(String.valueOf(selectedRow.getStock()));
        priceField.setText(String.valueOf(selectedRow.getPrice()));
        maxField.setText(String.valueOf(selectedRow.getMax()));
        minField.setText(String.valueOf(selectedRow.getMin()));
    }

    private void setOutSourcedFields() {
        outsourcedBtn.setSelected(true);
        MachineIdOrCompanyLabel.setText(COMPANY_NAME);
        dynamicField.setText(((Outsourced) selectedRow).getCompanyName());
    }

    private void setInHouseFields() {
        inHouseBtn.setSelected(true);
        MachineIdOrCompanyLabel.setText(Machine_ID);
        dynamicField.setText(String.valueOf(((InHouse) selectedRow).getMachineId()));
    }
}
