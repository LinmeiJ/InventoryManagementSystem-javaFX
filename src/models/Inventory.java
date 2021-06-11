package models;

import controllers.Main;
import controllers.MainSceneController;
import controllers.ModifyPartSceneController;
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
        for(var part: allParts){
            if(part.getName().equalsIgnoreCase(partName) || part.getName().toLowerCase().startsWith(s)){
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
            if(prod.getName().equalsIgnoreCase(productName) || prod.getName().toLowerCase().startsWith(s)){
                result.add(prod);
            }
        }
        return result;
    }


    public static void updatePart(int index, Part selectedPart)
    {
            allParts.get(index).setName(selectedPart.getName());
            allParts.get(index).setStock(selectedPart.getStock());
            allParts.get(index).setPrice(selectedPart.getPrice());
            allParts.get(index).setMax(selectedPart.getMax());
            allParts.get(index).setMin(selectedPart.getMin());
            if(ModifyPartSceneController.isInHouse){
                ((InHouse) allParts.get(index)).setMachineId(((InHouse) selectedPart).getMachineId());
            }
            if(ModifyPartSceneController.isOutsourced){
                allParts.set(index, createANewPartType(index, selectedPart));
            }
    }

    private static Part createANewPartType(int index, Part selectedPart) {
        return new Outsourced(selectedPart.getId(), selectedPart.getName(),  selectedPart.getPrice(), selectedPart.getStock(),  selectedPart.getMin(), selectedPart.getMax(), ((Outsourced)selectedPart).getCompanyName());
    }

    public static void updateProdcut(int index, Product newProduct)
    {
    }

    public static boolean deletePart(Part selectedPart)
    {
        boolean isRemoved = false;
        if(allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            isRemoved = true;
        }
        return isRemoved;
    }

    public static boolean deleteProduct(Product selectedProduct)
    {
        boolean isRemoved = false;
        if(allParts.contains(selectedProduct))
        {
            allParts.remove(selectedProduct);
            isRemoved = true;
        }
        return isRemoved;
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
