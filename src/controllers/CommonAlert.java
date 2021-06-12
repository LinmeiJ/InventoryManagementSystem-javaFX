package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CommonAlert {
    public static Optional<ButtonType> confirmResult;
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public static void displayPartNotFound(){
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Part is not found");
        infoAlert.showAndWait();
    }

    public static void displayProdNotFound(){
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Product is not found");
        infoAlert.showAndWait();
    }

    public static void displayRowNotSelected(){
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Row is not selected");
        infoAlert.showAndWait();
    }

    public static void displayRemoveParts(){ //fix me
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Parts Associated");
        errorAlert.setContentText("All parts must be removed from product before deletion.");
        errorAlert.showAndWait();
    }

    public static void displayPartIsAlreadyExist() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Part is already exist for this product");
        infoAlert.showAndWait();
    }

    public static void displayDeleteConfirmation(){
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete this part?");
        confirmResult = confirmAlert.showAndWait();
    }

    public static void displanySuccessful() {
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("result");
        confirmAlert.setContentText("Successful Deleted");
        confirmResult = confirmAlert.showAndWait();
    }


//  clear the switch statement out when it is done <-- fix me
    public static void displayAlert(int alertType) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        switch (alertType) {
            case 1:
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Part not found");
                infoAlert.showAndWait();
                break;
            case 2:
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Product not found");
                infoAlert.showAndWait();
                break;
            case 3:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Please Select a row");
                errorAlert.showAndWait();
                break;
            case 4:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Product not selected");
                errorAlert.showAndWait();
                break;
            case 5:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Parts Associated");
                errorAlert.setContentText("All parts must be removed from product before deletion.");
                errorAlert.showAndWait();
                break;
            case 6:
                confirmAlert.setTitle("Parts");
                confirmAlert.setHeaderText("Delete");
                confirmAlert.setContentText("Do you want to delete this part?");
                confirmResult = confirmAlert.showAndWait();
                break;
            default: //fix me
        }
    }



}
