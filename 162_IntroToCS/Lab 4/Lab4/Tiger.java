
/**
 * CS 162 Lab 4
 * Subclass 2
 * 
 * @author Tiffany Jansen 
 * @version 04.22.2017
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
     * (Abstract Method 1)
     */
    public void printDetails()
    {
        System.out.println("The tiger's name is " + getName() + ", and it is " + getAge() + " years old.");
        System.out.println("It has " + numberOfStripes + " stripes, and it lives at the " + nameOfZoo + "."); 
    }

    /**
     * toString method to override the one in Object.
     * @return String representation of Tiger.
     */
    public String toString()
    {
        return "The tiger's name is " + getName() + ", it is " + getAge() + " years old, \n it has " + numberOfStripes + " strips, and it lives at the " + nameOfZoo + ".";
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
