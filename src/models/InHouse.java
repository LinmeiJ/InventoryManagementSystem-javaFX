package models;

/**
 * Supplied class InHouse.java
 * Please see the {@link Part} class for more super class info
 */

 /**
 *@author Linmei Mills
 */
public class InHouse extends Part{
    private int machineId;

     /**
      * Class constructor specifying the InHouse type part info
      */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return machineID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId set a new machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
