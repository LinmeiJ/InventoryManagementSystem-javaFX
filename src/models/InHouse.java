package models;

/**
 * Supplied class InHouse.java
 * Please see the {@link Part} class for more super class info
 */

/**
 * @author Linmei Mills
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Class constructor specifying the InHouse type part info
     *
     * @param id        the part id
     * @param name      the part name
     * @param price     the part price
     * @param stock     the part stock
     * @param min       the part min
     * @param max       the part max
     * @param machineId the machine ID of this part
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
