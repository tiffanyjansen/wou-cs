import java.util.ArrayList;
import java.util.Random;
/**
 * CS 162 Lab 2
 * Other Class
 * 
 * @author Tiffany Jansen and Stacia Fry
 * @version 04.11.2017
 */
public class AnimalList
{
    private ArrayList<Animal> animals;

    /**
     * Constructor for objects of class AnimalList
     */
    public AnimalList()
    {
        animals = new ArrayList<Animal>();
    }
    
    /**
     * MAIN METHOD
     */
    public static void main(String[] args)
    {
        AnimalList a = new AnimalList();
        
        //Creates dogs and adds them to list.
        a.addAnimal(new Dog("Spot", "Black and White".toLowerCase(), "Jim"));
        a.addAnimal(new Dog("Alistair", "Tan".toLowerCase(), "Stacia"));
        a.addAnimal(new Dog("Iggy", "Black and White".toLowerCase(), "Stacia"));
        a.addAnimal(new Dog("Jez", "Brown and White".toLowerCase(), "Becka"));
        a.addAnimal(new Dog("Jasper", "Black".toLowerCase(), "David"));
        
        //Creates tigers and adds them to list.
        a.addAnimal(new Tiger("Shere Kahn", 32, "Oregon Zoo"));
        a.addAnimal(new Tiger("Bob", 45, "Detroit Zoo"));
        a.addAnimal(new Tiger("Jerry", 56, "Brookfield Zoo"));
        a.addAnimal(new Tiger("Morty", 5, "National Zoological Park"));
        a.addAnimal(new Tiger("Rick", 25, "Blank Park Zoo"));
        
        //Prints all Details from our list
        a.printAll();
        a.printAges();
        a.allAlive();
    }

    /**
     * Adds an animal to our arraylist
     */
    public void addAnimal(Animal animal)
    {
        animals.add(animal);
    }
    
    /**
     * Prints all the animals in the list.
     */
    public void printAll()
    {
        for(Animal a: animals){
            System.out.println(a);
            System.out.println();
        }
    }
    
    public void printAges()
    {
        Random ran = new Random();
        for(Animal a: animals){
            int i = ran.nextInt(10);
            a.printAge(i);
            System.out.println("The animal is now as old as the last age stated here.");
            System.out.println();
        }
    }
    
    public void allAlive()
    {
        for(int i = 0; i<animals.size(); i++){
            Animal a = animals.get(i);
            if(a.isAlive()){
                System.out.println(a.getName() + " is still alive. :)");
            }
            else{
                System.out.println("Sadly, " + a.getName() + " is now dead. :(");
            }
        }
    }
}
