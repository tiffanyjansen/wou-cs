import java.util.Random;
/**
 * CS 162 Lab 4 
 * SuperClass
 * 
 * @author Tiffany Jansen
 * @version 04.22.2017
 */
abstract public class Animal implements LivingCreature
{
    private String name;
    private int age;
    private boolean isAlive;
    
    private static final int MAX_AGE = 15;
       
    /**
     * Constructor for objects of class Animal
     * 
     * @param name of animal
     * @param color of animal
     */
    public Animal(String name1)
    {
        Random ran = new Random();
        name = name1;
        age = (ran.nextInt(10)+1);
        isAlive = true;
    }
    
    /**
     * Accessor method for name of animal.
     * 
     * @return name of animal
     */
    public String getName()
    {
        return name;
    }
    
     /**
     * Accessor method for age of animal.
     * 
     * @return age of animal
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * Mutator method for name of animal.
     * 
     * @return name of animal
     */
    public void setName(String name2)
    {
        name = name2;
    }
    
    /**
     * Mutator method for age of animal.
     * 
     * @return age of animal
     */
    public void setAge(int age1)
    {
        age = age1;
    }
    
    /**
     * Prints the details of the Animal
     */
    public abstract void printDetails();
    
    /**
     * toString method to override the one in Object.
     * @return String representation of Animal.
     */
    public abstract String toString();
    
    /**
     * Prints out age "a" times
     * @param a Number of times to increment age.
     */
    public abstract void printAge(int a);
    
    /**
     * Age that animal dies.
     */
    public void isDead()
    {
        if(age > MAX_AGE){
            isAlive = false;
        }
    }
    
    /**
     * Is the animal alive?
     * @return isAlive.
     */
    public boolean isAlive()
    {
        isDead();
        return isAlive;
    }
    
    /**
     * Adds a year to the age of the animal.
     */
    public int incrementAge()
    {
        age = age + 1;
        return age;
    }
        
}
