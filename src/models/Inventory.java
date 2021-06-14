package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

 /**
  * Supplied class Inventory.java.
  */

 /**
  *  @author Linmei Mills
  */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to Inventory.
     * @param newPart the new part user created
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the inventory.
     * @param newProduct the new product user created
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * This method looks up for a part that a user searches for by a id.
     * @param partId a part ID that the user entered
     * @return returns the part that is found, otherwise returns a null
     */
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

    /**
     * This method looks up for a product that a user searches for by a id.
     * @param productId a product ID that the user entered
     * @return Returns the product that is found, otherwise return null.
     */
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

    /**
     * This method looks up for a part that a user searches for by name.
     * @param partName a part name or a partial character(s) of the name user entered
     * @return the part that is found
     */
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

    /**
     * This method looks up for a product that a user searches for by name.
     * @param productName A product name or a partial character(s) of the name user entered
     * @return The product that is found
     */
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

   /**
    * This method updates a part based user selection.
    *
    * @param index the element position in the inventory
    * @param selectedPart a part that a user wishes to update
    */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates a product based user selection.
     *
     * @param index the element position in the inventory
     * @param newProduct a product that a user wishes to update
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * This method deletes a part based user selection.
     *
     * @param selectedPart the part that a user wishes to delete
     * @return whether the part is deleted
     */
    public static boolean deletePart(Part selectedPart)
    {
        boolean isDeleted = false;
        if(allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * This method deletes a product based user selection.
     *
     * @param selectedProduct the product that a user wishes to delete
     * @return whether the product is deleted
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        boolean isDeleted = false;
        if(allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * @return all the parts in the inventory
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * @return all the products in the inventory
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
