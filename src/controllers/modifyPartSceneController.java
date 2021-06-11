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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyPartSceneController implements Initializable {
    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label modifyPartLabel;

    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private ToggleGroup addPartTg;

    @FXML
    private RadioButton outsroucedBtn;

    @FXML
    private Label MachineIdOrCompanylabel;

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

    @FXML
    private Label maxLabel;

    private Part selectedRow;

    private final static String Machine_ID = "Machine ID";
    private final static String COMPANY_NAME = "Company Name";

    @FXML
    void backToMainScene(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(add);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void modifyInHouseType(ActionEvent event) {
        MachineIdOrCompanylabel.setText(Machine_ID);
    }

    @FXML
    void modifyOutsourcedType(ActionEvent event) {
        MachineIdOrCompanylabel.setText(COMPANY_NAME);
    }

    @FXML
    void savePart(ActionEvent event) throws IOException {
        String name = nameField.getText();
        int inv = Integer.parseInt(invField.getText());
        double price = ;//convert to double

        selectedRow.setName(name);
        selectedRow.setPrice(Double.parseDouble(priceField.getText()));
        selectedRow.setMax(Integer.parseInt(maxField.getText()));
        selectedRow.setMin(Integer.parseInt(minField.getText()));
        if(isStillInHouseType()){

            Inventory.updatePart(selectedRow.getId(), selectedRow);
        }
        if(isMachineId())
        {
//          addInHouseToInventory(Main.getUniquePartId(), name, inv, price, max, min);
        }
        if(isCompanyName()){
//            addOutsourcedToInventory(Main.getUniquePartId(), name, inv, price, max, min);
        }
        returnBackToMainScene(event);

    }

    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean isMachineId() {
        return MachineIdOrCompanylabel.getText().equalsIgnoreCase(Machine_ID);
    }

    private boolean isCompanyName() {
        return MachineIdOrCompanylabel.getText().equalsIgnoreCase(COMPANY_NAME);
    }

    private boolean isStillInHouseType(){
        return isInHouse() && isMachineId();
    }

    private boolean isStillOutsourceType(){
        return isOutsourced() && isCompanyName();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCommonFields();
        if(isInHouse())
        {
            setInHouseFields();
        }
        if(isOutsourced())
        {
            setOutSourcedFields();
        }
    }

    public boolean isInHouse(){
        return selectedRow instanceof InHouse;
    }

    public boolean isOutsourced(){
        return selectedRow instanceof Outsourced;
    }

    private void setCommonFields() {
        selectedRow = MainSceneController.partSelectedRow;
        partIdField.setText(String.valueOf(selectedRow.getId()));
        nameField.setText(selectedRow.getName());
        invField.setText(String.valueOf(selectedRow.getStock()));
        priceField.setText(String.valueOf(selectedRow.getPrice()));
        maxField.setText(String.valueOf(selectedRow.getMax()));
        minField.setText(String.valueOf(selectedRow.getMin()));
    }

    private void setOutSourcedFields() {
        outsroucedBtn.setSelected(true);
        MachineIdOrCompanylabel.setText(COMPANY_NAME);
        dynamicField.setText(((Outsourced) selectedRow).getCompanyName());
    }

    private void setInHouseFields() {
        inHouseBtn.setSelected(true);
        MachineIdOrCompanylabel.setText(Machine_ID);
        dynamicField.setText(String.valueOf(((InHouse) selectedRow).getMachineId()));
    }
}
