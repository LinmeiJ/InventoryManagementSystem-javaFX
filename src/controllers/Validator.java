package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Validator {
    public static Optional<ButtonType> confirmResult;
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private static int min;
    private static int max;

    public static void displayPartNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Part is not found");
        infoAlert.showAndWait();
    }

    public static void displayProdNotFound() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Product is not found");
        infoAlert.showAndWait();
    }

    public static void displayRowNotSelected() {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText("Please select a row");
        infoAlert.showAndWait();
    }

    public static void displayRemoveParts() {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Product has parts");
        errorAlert.setContentText("Please remove all parts before deleting a product.");
        errorAlert.showAndWait();
    }

//    public static void displayInvalidInput(String name, String inv, String price, String max, String min) {
//        if ((isNotEmpty(name) == false && isInteger(inv) == false && isDouble(price) == false && isInteger(max) == false && isInteger(min) == false)) {
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("Fields input are incorrect");
//            errorAlert.setContentText("Exception: No data found in the name field; Inventory(Inv) is not an integer; Price is not a double; Max is not an integer; Min is not an integer");
//            errorAlert.showAndWait();
//        }
//        if ((isNotEmpty(name) == true && isInteger(inv) == false && isDouble(price) == false && isInteger(max) == false && isInteger(min) == false)) {
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("Fields input are incorrect");
//            errorAlert.setContentText("Exception: Inventory(Inv) is not an integer; Price is not a double; Max is not an integer; Min is not an integer");
//            errorAlert.showAndWait();
//        }
//        if ((isNotEmpty(name) == true && isInteger(inv) == true && isDouble(price) == false && isInteger(max) == false && isInteger(min) == false)) {
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("Fields input are incorrect");
//            errorAlert.setContentText("Exception: Price is not a double; Max is not an integer; Min is not an integer");
//            errorAlert.showAndWait();
//        }
//        if ((isNotEmpty(name) == true && isInteger(inv) == true && isDouble(price) == true && isInteger(max) == false && isInteger(min) == false)) {
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("Fields input are incorrect");
//            errorAlert.setContentText("Exception: Max is not an integer; Min is not an integer");
//            errorAlert.showAndWait();
//        }
//        if ((isNotEmpty(name) == true && isInteger(inv) == true && isDouble(price) == true && isInteger(max) == true && isInteger(min) == false)) {
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("Input for Min is incorrect");
//            errorAlert.setContentText("Exception: Min is not an integer");
//            errorAlert.showAndWait();
//        }
//    }

    public static void displayDeleteConfirmation() {
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete this part?");
        confirmResult = confirmAlert.showAndWait();
    }

    public static boolean areValidateInputs(String name, String inv, String price, String max, String min) {
        if ((isEmpty(name) && isInteger(inv) && isDouble(price) && isInteger(max) && isInteger(min))) {
            return true;
        }
        return false;
    }

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
}
