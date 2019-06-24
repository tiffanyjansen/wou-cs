
/**
 * CS 162 Lab 2
 * Subclass1
 * 
 * @author Tiffany Jansen and Stacia Fry
 * @version 04.11.2017
 */
public class Dog extends Animal
{
    private String color;
    private String owner;
    
    /**
     * Constructor for objects of class Dog
     * @param String name1 The name of the dog
     * @param String color1 The color of the dog
     * @param String owner1 The owner of the dog
     */
    public Dog(String name1, String color1, String owner1)
    {
        super(name1);
        color = color1;
        owner = owner1;
    }

    /**
     * Accessor  method for color
     * @return color of dog
     */
    public String getColor()
    {
        return color;
    }
    
    /** 
     * Accessor method for owner
     * @return owner of dog
     */
    public String getOwner()
    {
        return owner;
    }
    
    /** 
     * Mutator method for owner
     * @param newOwner Name of owner
     */
    public void setOwner(String newOwner)
    {
        owner = newOwner;
    }
    
    /**
     * Prints the details of the Dog
     */
    public void printDetails()
    {
        super.printDetails();
        System.out.println("The color of the dog is " + color + ", and it's owner is " + owner + ".");
    }
}
