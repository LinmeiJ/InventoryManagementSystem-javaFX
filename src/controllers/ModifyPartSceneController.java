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
        Parent add = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(add);
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
        int inv = Integer.parseInt(invField.getText());
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int max = Integer.parseInt(maxField.getText());
        int min = Integer.parseInt(minField.getText());
        if(containMachineId())
        {
            InHouse part = new InHouse(selectedRow.getId(), name, price, inv, max, min, Integer.parseInt(dynamicField.getText()));
            int index = findIndex();
            Inventory.updatePart(index, part);
        }
        if(containCompanyName()){
            Outsourced part = new Outsourced(selectedRow.getId(), name, price, inv, max, min, dynamicField.getText());
            System.out.println(part instanceof Outsourced);
            System.out.println(part.getName() + " "+ part.getCompanyName() + " "+ selectedRow.getId());
            int index = findIndex();
            Inventory.updatePart(index, part);
        }
        returnBackToMainScene(event);

    }

    private int findIndex() {
        for(int i = 0; i < Inventory.getAllParts().size(); i++){
            if(Inventory.getAllParts().get(i).getId() == selectedRow.getId()){
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
