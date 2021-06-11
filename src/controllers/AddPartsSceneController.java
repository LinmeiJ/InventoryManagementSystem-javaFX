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
import models.Part;

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
        Parent add = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(add);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void savePart(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        int inv = Integer.parseInt(invField.getText());
        double price = Double.parseDouble(priceField.getText());//convert to double
        int max = Integer.parseInt(maxField.getText());
        int min = Integer.parseInt(minField.getText());
        if(MachineIdOrCompanylabel.getText().equalsIgnoreCase(MACHINE_ID))
        {
            Inventory.addPart(new InHouse(Main.getUniquePartId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText())));
        }
        if(MachineIdOrCompanylabel.getText().equalsIgnoreCase(COMPANY_NAME)){
            Inventory.addPart(new Outsourced(Main.getUniquePartId(), name, price, inv, min, max, dynamicField.getText()));
        }
        returnBackToMainScene(actionEvent);
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
