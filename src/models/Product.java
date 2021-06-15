package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

 /**
  * Supplied class Product.java
  * Please see the {@link Part} class for more super class info
  */

 /**
  *  @author Linmei Mills
  */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

     /**
      * Class constructor specifying the product info
      *@param id the part id
      *@param name the part name
      *@param price the part price
      *@param stock the part stock
      *@param min the part min
      *@param max the part max
      */
    public Product(int id, String name, int stock, double price, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id for the product
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name for the product
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price the price for the product
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * @param stock the stock for the product
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @param min the min for the product
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @param max the max for the prodcut
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @param part a new part is created by user
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * This method removes a selected associated part from a product.
     * @param selectedAssociatedPart user selected part
     * @return whether the associated part is deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
       return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method gets all part from a product.
     * @return all associated parts in inventory
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
