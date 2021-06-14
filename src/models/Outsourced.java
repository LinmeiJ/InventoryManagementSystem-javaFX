package models;

 /**
  * Supplied class Outsourced.java
  * Please see the {@link Part} class for more super class info
  */

 /**
  *@author Linmei Mills
  */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Class constructor specifying the outsourced type part info
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the company name for the outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
