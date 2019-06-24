import java.util.Random;
/**
 * CS 162 Lab 2 
 * SuperClass
 * 
 * @author Tiffany Jansen and Stacia Fry
 * @version 04.11.2017
 */
public class Animal
{
    private String name;
    private int age;
    
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
    public void printDetails()
    {
        System.out.println("The name of the animal is " + name + ", and it's age is " + age + ".");
    }
}
