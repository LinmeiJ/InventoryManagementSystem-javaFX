package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CommonAlert {
    public static Optional<ButtonType> confirmResult;

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
