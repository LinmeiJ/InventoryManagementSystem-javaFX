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
     * An optional button type window to confirm user's action.
     */
    public static Optional<ButtonType> confirmResult;

    /**
     * An alert object to display info to user to the user.
     */
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * An alert object to display a confirmation to a user.
     */
    private static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * An alert to display an error message.
     */
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * This method generates a part not found dialog window to the end user.
     */
    public static void displayPartNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Part is not found");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a product not found dialog window to the end user.
     */
    public static void displayProdNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Product is not found");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a row is not selected dialog window to the end user.
     */
    public static void displayRowNotSelected() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Please select a row");
        infoAlert.showAndWait();
    }

    /**
     * This method generates a reminder that a product cannot be deleted before it's parts still associated with.
     */
    public static void displayRemoveParts() {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Product has parts");
        errorAlert.setContentText("Please remove all parts before deleting a product.");
        errorAlert.showAndWait();
    }

    /**
     * This method generates a message to confirm whether the end user want to delete a selected item.
     */
    public static void displayDeleteConfirmation() {
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete it?");
        confirmResult = confirmAlert.showAndWait();
    }

    /**
     * This method checks whether an input is a double type.
     *
     * @param input user's input
     * @return boolean
     */
    public static boolean isDouble(String input) {
        boolean isValid = false;
        try {
            double d = Double.parseDouble(input);
            isValid = true;
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * This method checks whether an user's input is an integer.
     *
     * @param input input from the end user
     * @return whether is a integer
     */
    public static boolean isInteger(String input) {
        boolean isValid = false;
        try {
            Integer d = Integer.parseInt(input);
            isValid = true;
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * This method checks whether an user's input is empty.
     *
     * @param input input from the end user
     * @return whether is a an empty string
     */
    public static boolean isEmpty(String input) {
        boolean isValid = false;
        if (input.isEmpty() || input.isBlank() || input == null) {
            isValid = true;

        }
        return isValid;
    }

    /**
     * This method sets an error alert that displays to the end user.
     *
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Field input is incorrect");
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    /**
     * This method sets an error alert that reminds user to delete the associated parts first before delete the product.
     */
    public static void displayProdContainsParts() {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Product");
        errorAlert.setContentText("This product contains parts, please delete the parts by using the modify button");
        errorAlert.showAndWait();
    }

    /**
     * This method sets an error alert that display to the end user based on the message passed in.
     *
     * @param msg a message will be used for setting up the alert text content
     */
    public static void displayError(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Error Occurs");
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    /**
     * This method sets a confirmation window to ensure the end user wants to exit the program.
     */
    public static void displayExitConfirmation() {
        confirmAlert.setTitle("Exit");
        confirmAlert.setHeaderText("Close the program");
        confirmAlert.setContentText("Are you sure you want to close the program?");
        confirmResult = confirmAlert.showAndWait();
    }
}
