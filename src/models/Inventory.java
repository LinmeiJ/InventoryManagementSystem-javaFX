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
        for(Part part: allParts)
        {
            if(part.getId() == partId)
            {
                return part;
            }
        }
        return null ;
    }

    public static Product lookupProduct(int productId)
    {
        for(var prod: allProducts)
        {
            if(prod.getId() == productId)
            {
                return prod;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName)
    {
        String s = partName.toLowerCase();
        ObservableList result = FXCollections.observableArrayList();
        for(var part: allProducts){
            if(part.getName().equals(partName) || part.getName().toLowerCase().startsWith(s)){
                result.add(part);
            }
        }
        return result;
    }


    public static ObservableList<Product> lookupProduct(String productName)
    {
        String s = productName.toLowerCase();
        ObservableList result = FXCollections.observableArrayList();
        for(var prod: allProducts){
            if(prod.getName().equals(productName) || prod.getName().toLowerCase().startsWith(s)){
                result.add(prod);
            }
        }
        return result;
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
