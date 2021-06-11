package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class AddProductController {

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
    private TableColumn<?, ?> partId;

    @FXML
    private TableColumn<?, ?> partName;

    @FXML
    private TableColumn<?, ?> partInv;

    @FXML
    private TableColumn<?, ?> partPrice;

    @FXML
    private TableColumn<?, ?> associatePartId;

    @FXML
    private TableColumn<?, ?> associatePartName;

    @FXML
    private TableColumn<?, ?> associateInv;

    @FXML
    private TableColumn<?, ?> associatePrice;

    @FXML
    private Button addAssociatedPartBtn;

    @FXML
    private Button removePartFromProdBtn;

    @FXML
    private Button saveProdBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void addPartToProdClicked(ActionEvent event) {

    }

    @FXML
    void cancelBtnClicked(ActionEvent event) {

    }

    @FXML
    void removePartFromProdClicked(ActionEvent event) {

    }

    @FXML
    void saveProdClicked(ActionEvent event) {

    }

    @FXML
    void searchBtnEntered(ActionEvent event) {

    }

}
