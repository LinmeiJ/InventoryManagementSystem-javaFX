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
import javafx.scene.control.Button;
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

public class ModifyProductController implements Initializable, Cloneable {

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

    @FXML
    private Button addAssociatedPartBtn;

    @FXML
    private Button removePartFromProdBtn;

    @FXML
    private Button saveProdBtn;

    @FXML
    private Button cancelBtn;

    private ObservableList<Part> associatedParts  = FXCollections.observableArrayList();
    private Product originalRow = MainSceneController.productSelectedRow;


    @FXML
    void searchBtnEntered(KeyEvent event) {
        if(isEntered(event) && isPartNumeric())
        {
            searchedPartById();
        }
        else if(isEntered(event) && isPartString()){
            searchedPartByName();
        }
        else{
            modifyPartTable.setItems(Inventory.getAllParts());
        }
    }
    private boolean isEntered(KeyEvent event){
        return event.getCode().equals(KeyCode.ENTER);
    }

    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(partSearchField.getText());
        if(result.size() > 0){
            modifyPartTable.setItems(result);
        }
        else Validator.displayPartNotFound();
    }

    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(partSearchField.getText()));
        if(part == null) {
            Validator.displayPartNotFound();
        }
        else{
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            modifyPartTable.setItems(result);
        }
    }

    private boolean isPartString() {
        return partSearchField.getText() != null && partSearchField.getText().matches("^[a-zA-Z\\s]*$");
    }

    private boolean isPartNumeric(){
        return partSearchField != null && partSearchField.getText().matches("^[0-9]*$");
    }



    @FXML
    void addPartToProdClicked(ActionEvent event) {
        try{
           Part selectedPartRow = modifyPartTable.getSelectionModel().getSelectedItem();
           associatedParts.add(selectedPartRow);
           modifyAssociatedPartTable.setItems(associatedParts);
        }catch (NullPointerException e){
            Validator.displayRowNotSelected();
        }
    }

    @FXML
    void removePartFromProdClicked(ActionEvent event) {
        Part selectedAssocPart = modifyAssociatedPartTable.getSelectionModel().getSelectedItem();
        if(selectedAssocPart == null){
            Validator.displayRowNotSelected();
        }
        else {
            int id = selectedAssocPart.getId();
            for (int i = 0; i < associatedParts.size(); i++) {
                if (associatedParts.get(i).getId() == id) {
                    associatedParts.remove(associatedParts.get(i));
                }
            }
        }
    }

    @FXML
    void saveProdClicked(ActionEvent event) throws IOException {
        int id = originalRow.getId();
        String name = modifyProdNameField.getText();
        double price = Double.parseDouble(modifyProdPriceField.getText());
        int stock = Integer.parseInt(modifyProdInvField.getText());
        int min = Integer.parseInt(modifyProdMinField.getText());
        int max = Integer.parseInt(modifyProdMaxField.getText());
        Product prod = new Product(id, name, stock, price, min, max);
        int index = findIndex();

        for(Part part : associatedParts){
            prod.addAssociatedPart(part);
        }
        Inventory.updateProdcut(index, prod);
        returnBackToMainScene(event);
    }

    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private int findIndex() {
        for(int i = 0; i < Inventory.getAllProducts().size(); i++){
            if(Inventory.getAllProducts().get(i).getId() == originalRow.getId()){
                return i;
            }
        }
        return -1; // fix me
    }

    @FXML
    void cancelBtnClicked(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(new Main().getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(add);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProdFields();
        setPartTable();
        setAssociatedPartsTable();
    }

    private void setPartTable() {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyPartTable.setItems(Inventory.getAllParts());
    }

    private void setAssociatedPartsTable() {
        associatePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associateInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = MainSceneController.productSelectedRow.getAllAssociatedParts();
        modifyAssociatedPartTable.setItems(associatedParts);
    }

    private void setProdFields() {
        modifyProdID.setText(String.valueOf(originalRow.getId()));
        modifyProdNameField.setText(originalRow.getName());
        modifyProdInvField.setText(String.valueOf(originalRow.getStock()));
        modifyProdPriceField.setText(String.valueOf(originalRow.getPrice()));
        modifyProdMinField.setText(String.valueOf(originalRow.getMax()));
        modifyProdMaxField.setText(String.valueOf(originalRow.getMin()));
    }
}
