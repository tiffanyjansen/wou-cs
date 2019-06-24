
/**
 * CS 162 Lab 2
 * Subclass2
 * 
 * @author Tiffany Jansen and Stacia Fry 
 * @version 04.11.2017
 */
public class Tiger extends Animal
{
    private int numberOfStripes;
    private String nameOfZoo;

    /**
     * Constructor for objects of class Tiger
     * @param name1 The name of the tiger
     * @param numbStripes The number of stripes the tiger has
     * @param nameZ The name of the zoo
     */
    public Tiger(String name1, int numbStripes, String nameZ)
    {
        super(name1);
        numberOfStripes = numbStripes;
        nameOfZoo = nameZ;
    }

   /**
     * Accessor  method for number of stripes
     * @return Number of stripes
     */
    public int getStripes()
    {
        return numberOfStripes;
    }
    
    /** 
     * Accessor method for name of zoo
     * @return name of zoo
     */
    public String getZoo()
    {
        return nameOfZoo;
    }
    
    /** 
     * Mutator method for name of zoo
     * @param newZoo Name of new zoo
     */
    public void setOwner(String newZoo)
    {
        nameOfZoo = newZoo;
    }
    
    /**
     * Print the details of the Tiger
     */
    public void printDetails()
    {
        super.printDetails();
        System.out.println("The tiger has " + numberOfStripes + " stripes, and it lives at the " + nameOfZoo + "."); 
    }
}
