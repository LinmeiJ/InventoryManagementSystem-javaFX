package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

public class AddProductController implements Initializable {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productionInvField;

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

    @FXML
    private Button addAssociatedPartBtn;

    @FXML
    private Button removePartFromProdBtn;

    @FXML
    private Button saveProdBtn;

    @FXML
    private Button cancelBtn;

    private Part selectedPartTableRow;
    private Part selectedAssociatedPart;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    void addPartToProdClicked(ActionEvent event) {
        selectedPartTableRow = partTable.getSelectionModel().getSelectedItem();
        if(selectedPartTableRow == null)
        {
            CommonAlert.displayRowNotSelected();
        }
        Part part = selectedPartTableRow; //check if we need this line

        associatedParts.add(part);
        associatedPartTable.setItems(associatedParts);
    }

    @FXML
    void cancelBtnClicked(ActionEvent event) throws IOException {
        returnBackToMainScene(event);
    }

    @FXML
    void removePartFromProdClicked(ActionEvent event) {
        selectedAssociatedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        int id = selectedAssociatedPart.getId();
        for(int i = 0; i < associatedParts.size(); i++)
        {
            if(associatedParts.get(i).getId() == id){
                associatedParts.remove(associatedParts.get(i));
            }
        }
    }

    @FXML
    void saveProdClicked(ActionEvent event) throws IOException {

        String name = productNameField.getText();
        int stock = Integer.parseInt(productionInvField.getText());
        double price = Double.parseDouble(productPriceField.getText());
        int min = Integer.parseInt(productMinField.getText());
        int max = Integer.parseInt(productMaxField.getText());

        Product prod = new Product(Main.getUniqueProdId(), name, stock, price, min, max);
        for(Part part : associatedParts){
            prod.addAssociatedPart(part);
        }
        Inventory.addProduct(prod);
        returnBackToMainScene(event);
    }
    public void returnBackToMainScene(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

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
            partTable.setItems(Inventory.getAllParts());
        }
    }

    private boolean isEntered(KeyEvent event){
        return event.getCode().equals(KeyCode.ENTER);
    }

    private void searchedPartByName() {
        ObservableList result = Inventory.lookupPart(partSearchField.getText());
        if(result.size() > 0){
            partTable.setItems(result);
        }
        else CommonAlert.displayAlert(1);
    }

    private void searchedPartById() {
        var part = Inventory.lookupPart(Integer.parseInt(partSearchField.getText()));
        if(part == null) {
            CommonAlert.displayAlert(1);
        }
        else{
            ObservableList<Part> result = FXCollections.observableArrayList();
            result.add(part);
            partTable.setItems(result);
        }
    }

    private boolean isPartString() {
        return partSearchField.getText() != null && partSearchField.getText().matches("^[a-zA-Z\\s]*$");
    }

    private boolean isPartNumeric(){
        return partSearchField != null && partSearchField.getText().matches("^[0-9]*$");
    }


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
}
