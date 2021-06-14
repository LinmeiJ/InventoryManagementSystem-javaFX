package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * This class contains the logics for validating user's inputs and message alerts when the required inputs are invalid.
 *
 * @author Linmei Mills
 */
public class Validator {

    /**
     * An optional button type window to confirm user's action
     */
    public static Optional<ButtonType> confirmResult;

    /**
     * An alert object to display info to user to the user
     */
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * An alert object to display a confirmation to a user
     */
    private static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * An alert to display an error message
     */
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * This method generates a part not found dialog window to the end user
     */
    public static void displayPartNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Part is not found");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a product not found dialog window to the end user
     */
    public static void displayProdNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Product is not found");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a row is not selected dialog window to the end user
     */
    public static void displayRowNotSelected() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Please select a row");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a reminder that a product cannot be deleted before it's parts still associated with
     */
    public static void displayRemoveParts() {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Product has parts");
        errorAlert.setContentText("Please remove all parts before deleting a product.");
        errorAlert.showAndWait();
    }

    /**
     * This method generates a message to confirm whether the end user want to delete a selected item
     */
    public static void displayDeleteConfirmation() {
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete it?");
        confirmResult = confirmAlert.showAndWait();
    }

    /**
     * This method checks whether an input is a double type
     * @param input user's input
     * @return boolean
     */
    public static boolean isDouble(String input) {
        boolean isValid = false;
        try {
            double d = Double.parseDouble(input);
            isValid = true;
        } catch (NumberFormatException ex) {
            isValid = false;
        }
        return isValid;
    }


    public static boolean isInteger(String input) {
        boolean isValid = false;
        try {
            Integer d = Integer.parseInt(input);
            isValid = true;
        } catch (NumberFormatException ex) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isEmpty(String input) {
        boolean isValid = false;
        if (input.isEmpty() || input.isBlank() || input == null) {
            isValid = true;

        }
        return isValid;
    }

    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Field input is incorrect");
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    public static void displayProdContainsParts() {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Product");
        errorAlert.setContentText("This product contains parts, please delete the parts by using the modify button");
        errorAlert.showAndWait();
    }

    public static void displayInvalidLogic(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Logic Error Occurs");
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    public static void displayExitConfirmation() {
        confirmAlert.setTitle("Exit");
        confirmAlert.setHeaderText("Close the program");
        confirmAlert.setContentText("Are you sure you want to close the program?");
        confirmResult = confirmAlert.showAndWait();
    }
}
