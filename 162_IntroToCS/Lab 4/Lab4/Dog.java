
/**
 * CS 162 Lab 4
 * Subclass 1
 * 
 * @author Tiffany Jansen
 * @version 04.22.2017
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
     * (Abstract Method 1)
     */
    public void printDetails()
    {
        System.out.println("The dog's name is " + getName() + ", and it is " + getAge() + " years old.");
        System.out.println("It is " + color + ", and it's owner is " + owner + ".");
    }
    
    /**
     *  toString method to override the one in Object.
     *  @return String representation of Dog.
     */
    public String toString()
    {
        return "The dog's name is " + getName() + ", it is " + getAge() + " years old, \n it is " + color + ", and it's owner is " + owner + ".";
    }
    
     /**
     * Prints out age "a" times.
     * (Abstract Method 2)
     * @param a Number of times to increment age.
     */
    public void printAge(int a)
    {
        if(a > -1){
            System.out.println(getName() + " is " + getAge() + " years old now.");
            for(int i = 1; i<a; i++){
                System.out.println("It will be " + incrementAge() + " years old.");
            }
        }
    }
    
    /**
     * Is the animal alive?
     * @return isAlive.
     */
    public boolean isAlive()
    {
        return super.isAlive();
    }
}
