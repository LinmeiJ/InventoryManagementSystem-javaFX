package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId)
    {
        return null;//fix me
    }

    public static Product lookupProduct(int productId)
    {
        return null;//fix me
    }

    public static ObservableList<Part> lookupPart(String partName)
    {
        return null;//fix me
    }


    public static ObservableList<Product> lookupProduct(String productName)
    {
        return null;//fix me
    }


    public static void updatePart(int index, Part selectedPart)
    {
    }

    public static void updateProdcut(int index, Product newProduct)
    {
    }

    public static boolean deletePart(Part selectedPart)
    {
        return false;//fix me
    }

    public static boolean deleteProduct(Product selectedProduct)
    {
     return false;//fix me
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
