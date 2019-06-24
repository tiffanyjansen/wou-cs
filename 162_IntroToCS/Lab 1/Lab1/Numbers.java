import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
/**
 * Contains an ArrayList of integers and does stuff with them.
 * 
 * @author Tiffany Jansen 
 * @version CS162 Lab 1, 04/03/2017
 */
public class Numbers
{
    private ArrayList<Integer> numbers;
    public static final int SIZE = 10;
    public static final int RANGE = 100000;

    public Numbers()
    {
        numbers = new ArrayList<Integer>();
    }

    /**
     * Fills each element of the array with a random integer 
     * with a max value of the range constant.
     */
    public void fillArray()
    {
        Random ran = new Random();
        for(int i=0; i<SIZE; i++){
            numbers.add(ran.nextInt(RANGE));
        }
    }

    /**
     * Returns the largest integer in the ArrayList.
     * 
     * @return Largest integer.
     */
    public int getMax()
    {
        int max = numbers.get(0);
        int i = 0;
        while(i<SIZE){
            int num = numbers.get(i);
            if(num > max){
                max = num;
            }
            i++;
        }
        return max;
    }

    /**
     * Returns the smallest integer in the ArrayList.
     * 
     * @return Smallest integer.
     */
    public int getMin()
    {
        int min = numbers.get(0);
        int i = 0;
        while(i<SIZE){
            int num = numbers.get(i);
            if(num < min){
                min = num;
            }
            i++;
        }
        return min;
    }

    /**
     * Returns the sum of all integers in the ArrayList.
     * 
     * @return Sum.
     */
    public int getSum()
    {
        int sum = 0;
        for(int i : numbers){
            sum = sum + i;
        }
        return sum;
    }

    /**
     * Returns the average value of the integers in the ArrayList.
     * 
     * @return Average.
     */
    public int getAvg()
    {
        int avg = getSum()/SIZE;
        return avg;
    }

    /**
     * Prints each element value in the ArrayList from the item in index 0 
     * through the item in the max index location.
     */
    public void printContents()
    {
        Iterator<Integer> it = numbers.iterator();
        int i = 0;
        while(it.hasNext()){
            System.out.println("[" + i + "] " + it.next());
            i++;
        }
    }

    /**
     * Tests the methods to verify their correctness; numeric
     * values will change due to the generation of random
     * values, but the math is easy enough
     * to visually verify if things are working
     */
    public void testMethods()
    {
        fillArray();
        printContents();
        System.out.println("SIZE value: " + SIZE);
        System.out.println("Max value: " + getMax());
        System.out.println("Min value: " + getMin());
        System.out.println("Sum: " + getSum());
        System.out.println("Average: " + getAvg());
    }
    
    /**
     * Main method to allow us to run the program on the command
     * line instead of BlueJ.
     */
    public static void main(String args[])
    {
        Numbers number = new Numbers();
        number.testMethods();
    }
}